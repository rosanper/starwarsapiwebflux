package com.letscode.starwarsapiwebflux.services;

import com.letscode.starwarsapiwebflux.dtos.EquipmentRequest;
import com.letscode.starwarsapiwebflux.dtos.TradeRequest;
import com.letscode.starwarsapiwebflux.exception.NotFoundRebelException;
import com.letscode.starwarsapiwebflux.exception.TradeException;
import com.letscode.starwarsapiwebflux.models.Rebel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TradeService {

    private final RebelService rebelService;

    private final CalculatorPointsService calculator;

    public Flux<Rebel> tradeEquipments(TradeRequest tradeRequest){
        Flux<List<Rebel>> rebels = rebelService.getAllRebels().buffer();
        return rebels.map(rebelList -> changeEquipments(rebelList,tradeRequest))
                .flatMap(rebelList -> rebelService.saveRebelList(rebelList));

    }

    private Rebel getRebel(List<Rebel> rebels, String id){
        return rebels.stream().filter(rebel -> rebel.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundRebelException("Não foi possível localizar o rebelde com id" + id));
    }

    public void verifyTraitor(Rebel rebel1, Rebel rebel2){
        if(rebel1.getIsTraitor()== true || rebel2.getIsTraitor()== true){
            throw new TradeException("pelo menos um dos rebeldes é um traidor");
        }
    }

    private EquipmentRequest reduceEquipmentQuantity(Rebel rebel, EquipmentRequest equimentoToGive){
        int newQuantityOfWeaponRebel = rebel.getEquipments().getQuantityOfWeapon() - equimentoToGive.getQuantityOfWeapon();
        int newQuantityOfAmmunationRebel = rebel.getEquipments().getQuantityOfAmmunition() - equimentoToGive.getQuantityOfAmmunition();
        int newQuantityOfWaterRebel = rebel.getEquipments().getQuantityOfWater() - equimentoToGive.getQuantityOfWater();
        int newQuantityOfFoodRebel = rebel.getEquipments().getQuantityOfFood() - equimentoToGive.getQuantityOfFood();

        if (newQuantityOfWeaponRebel < 0 || newQuantityOfAmmunationRebel < 0 ||
                newQuantityOfWaterRebel < 0 || newQuantityOfFoodRebel < 0) throw new TradeException("Não existe quantidade suficiente do item selecionado para a troca");

        return new EquipmentRequest(
                newQuantityOfWeaponRebel,
                newQuantityOfAmmunationRebel,
                newQuantityOfWaterRebel,
                newQuantityOfFoodRebel);
    }

    private EquipmentRequest increaseEquipmentQuantity(EquipmentRequest equipmentRebel, EquipmentRequest equipmentToReceive){
        int newQuantityOfWeaponRebel = equipmentRebel.getQuantityOfWeapon() + equipmentToReceive.getQuantityOfWeapon();
        int newQuantityOfAmmunationRebel= equipmentRebel.getQuantityOfAmmunition() + equipmentToReceive.getQuantityOfAmmunition();
        int newQuantityOfWaterRebel= equipmentRebel.getQuantityOfWater() + equipmentToReceive.getQuantityOfWater();
        int newQuantityOfFoodRebel=  equipmentRebel.getQuantityOfFood() + equipmentToReceive.getQuantityOfFood();

        return new EquipmentRequest(
                newQuantityOfWeaponRebel,
                newQuantityOfAmmunationRebel,
                newQuantityOfWaterRebel,
                newQuantityOfFoodRebel);
    }

    private List<Rebel> changeEquipments(List<Rebel> rebels, TradeRequest tradeRequest){
        EquipmentRequest equipmentToTradeRebel1 = tradeRequest.getRebel1().getEquipmentRequests();
        EquipmentRequest equipmentToTradeRebel2 = tradeRequest.getRebel2().getEquipmentRequests();

        String idRebel1 = tradeRequest.getRebel1().getRebelId();
        String idRebel2 = tradeRequest.getRebel2().getRebelId();

        Rebel rebel1 = getRebel(rebels,idRebel1);
        Rebel rebel2 = getRebel(rebels, idRebel2);

        //Verificar se é traidor
        verifyTraitor(rebel1,rebel2);


        //verificar pontuação
        if (calculator.calculatorEquipmentsPoints(equipmentToTradeRebel1) !=
                calculator.calculatorEquipmentsPoints(equipmentToTradeRebel2)) throw new TradeException("A troca não respeita a igualdade de pontuação.");

        //Troca
        EquipmentRequest newQuantityOfEquipmentRebel1 = reduceEquipmentQuantity(rebel1, equipmentToTradeRebel1);
        EquipmentRequest newQuantityOfEquipmentRebel2 = reduceEquipmentQuantity(rebel2,equipmentToTradeRebel2);

        newQuantityOfEquipmentRebel1 = increaseEquipmentQuantity(newQuantityOfEquipmentRebel1,equipmentToTradeRebel2);
        newQuantityOfEquipmentRebel2 = increaseEquipmentQuantity(newQuantityOfEquipmentRebel2,equipmentToTradeRebel1);

        rebel1.setEquipments(newQuantityOfEquipmentRebel1);
        rebel2.setEquipments(newQuantityOfEquipmentRebel2);

        List<Rebel> result = new ArrayList<>();
        result.add(rebel1);
        result.add(rebel2);

        return result;

    }


}
