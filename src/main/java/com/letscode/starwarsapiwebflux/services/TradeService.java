package com.letscode.starwarsapiwebflux.services;

import com.letscode.starwarsapiwebflux.dtos.EquipmentRequest;
import com.letscode.starwarsapiwebflux.dtos.TradeRequest;
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

    private final CalculatorPointsService calculatorPointsService;

    public Flux<Rebel> tradeEquipments(TradeRequest tradeRequest){
        Flux<List<Rebel>> rebels = rebelService.getAllRebels().buffer();
        return rebels.map(rebelList -> changeEquipments(rebelList,tradeRequest))
                .flatMap(rebelList -> rebelService.saveRebelList(rebelList));

    }

//    flatMap(rebelList -> {
//        Iterator<Rebel> iterator = rebelList.stream().iterator();
//        while (iterator.hasNext()){
//            rebelService.saveRebel(iterator.next());
//        }
//        return null;
//    });



    public List<Rebel> changeEquipments(List<Rebel> rebels, TradeRequest tradeRequest){
        EquipmentRequest equipmentToTradeRebel1 = tradeRequest.getRebel1().getEquipmentRequests();
        EquipmentRequest equipmentToTradeRebel2 = tradeRequest.getRebel2().getEquipmentRequests();

        String idRebel1 = tradeRequest.getRebel1().getRebelId();
        String idRebel2 = tradeRequest.getRebel2().getRebelId();

        Rebel rebel1 = rebels.stream().filter(rebel -> rebel.getId().equalsIgnoreCase(idRebel1))
                .findFirst()
                .orElseThrow(() -> new TradeException("Não foi possível localizar o rebelde com id" + idRebel1));

        Rebel rebel2 = rebels.stream().filter(rebel -> rebel.getId().equalsIgnoreCase(idRebel2))
                .findFirst()
                .orElseThrow(() -> new TradeException("Não foi possível localizar o rebelde com id" + idRebel2));
//        Rebel rebel1 = new Rebel();
//        Rebel rebel2 = new Rebel();
//
//
//        for (Rebel rebel : rebels) {
//            if (rebel.getId().contains(idRebel1)) rebel1 = rebel;
//            if (rebel.getId().contains(idRebel2)) rebel2 = rebel;
//        }

        //Verificar se é traidor
        verifyTraitor(rebel1,rebel2);


        //verificar pontuação
        if (calculatorPointsService.calculatorEquipmentsPoints(equipmentToTradeRebel1) !=
                calculatorPointsService.calculatorEquipmentsPoints(equipmentToTradeRebel2)) throw new TradeException("A troca não respeita a igualdade de pontuação.");

        //Troca
        int newQuantityOfWeaponRebel1 = rebel1.getEquipments().getQuantityOfWeapon() - equipmentToTradeRebel1.getQuantityOfWeapon();
        int newQuantityOfAmmunationRebel1 = rebel1.getEquipments().getQuantityOfAmmunition() - equipmentToTradeRebel1.getQuantityOfAmmunition();
        int newQuantityOfWaterRebel1 = rebel1.getEquipments().getQuantityOfWater() - equipmentToTradeRebel1.getQuantityOfWater();
        int newQuantityOfFoodRebel1 = rebel1.getEquipments().getQuantityOfFood() - equipmentToTradeRebel1.getQuantityOfFood();

        if (newQuantityOfWeaponRebel1 < 0 || newQuantityOfAmmunationRebel1 < 0 ||
                newQuantityOfWaterRebel1 < 0 || newQuantityOfFoodRebel1 < 0) throw new TradeException("Não existe quantidade suficiente do item selecionado para a troca");

        int newQuantityOfWeaponRebel2 = rebel2.getEquipments().getQuantityOfWeapon() - equipmentToTradeRebel2.getQuantityOfWeapon();
        int newQuantityOfAmmunationRebel2 = rebel2.getEquipments().getQuantityOfAmmunition() - equipmentToTradeRebel2.getQuantityOfAmmunition();
        int newQuantityOfWaterRebel2 = rebel2.getEquipments().getQuantityOfWater() - equipmentToTradeRebel2.getQuantityOfWater();
        int newQuantityOfFoodRebel2 = rebel2.getEquipments().getQuantityOfFood() - equipmentToTradeRebel2.getQuantityOfFood();

        if (newQuantityOfWeaponRebel2 < 0 || newQuantityOfAmmunationRebel2 < 0 ||
                newQuantityOfWaterRebel2 < 0 || newQuantityOfFoodRebel2 < 0) throw new TradeException("Não existe quantidade suficiente do item selecionado para a troca");

        newQuantityOfWeaponRebel1 += equipmentToTradeRebel2.getQuantityOfWeapon();
        newQuantityOfAmmunationRebel1 += equipmentToTradeRebel2.getQuantityOfAmmunition();
        newQuantityOfWaterRebel1 += equipmentToTradeRebel2.getQuantityOfWater();
        newQuantityOfFoodRebel1 += equipmentToTradeRebel2.getQuantityOfFood();

        newQuantityOfWeaponRebel2 += equipmentToTradeRebel1.getQuantityOfWeapon();
        newQuantityOfAmmunationRebel2 += equipmentToTradeRebel1.getQuantityOfAmmunition();
        newQuantityOfWaterRebel2 += equipmentToTradeRebel1.getQuantityOfWater();
        newQuantityOfFoodRebel2 += equipmentToTradeRebel1.getQuantityOfFood();

        EquipmentRequest newEquipmentRebel1 = new EquipmentRequest(
                newQuantityOfWeaponRebel1,
                newQuantityOfAmmunationRebel1,
                newQuantityOfWaterRebel1,
                newQuantityOfFoodRebel1);

        EquipmentRequest newEquipmentRebel2 = new EquipmentRequest(
                newQuantityOfWeaponRebel2,
                newQuantityOfAmmunationRebel2,
                newQuantityOfWaterRebel2,
                newQuantityOfFoodRebel2);

        rebel1.setEquipments(newEquipmentRebel1);
        rebel2.setEquipments(newEquipmentRebel2);

        List<Rebel> result = new ArrayList<>();
        result.add(rebel1);
        result.add(rebel2);

        return result;

    }

    public void verifyTraitor(Rebel rebel1, Rebel rebel2){
        if(rebel1.getIsTraitor()== true || rebel2.getIsTraitor()== true){
            throw new TradeException("pelo menos um dos rebeldes é um traidor");
        }
    }



// TODO - refazer lógica

    //    @Autowired
//    private EquipmentRepository equipmentRepository;
//
//    @Autowired
//    private EquipmentsService equipmentsService;
//
//    @Autowired
//    private RebelsService rebelsService;
//
//    public void verifyTraitor(Rebel rebel1, Rebel rebel2){
//        if(rebel1.getIsTraitor()== true || rebel2.getIsTraitor()== true){
//            throw new TradeException("pelo menos um dos rebeldes é um traidor");
//        }
//    }
//
//    public void verifyNameAndQuantity(List<EquipmentToTradeDTO> equipmentToTradeList, List<Equipment> equipmentListRebelToGive){
//        for (EquipmentToTradeDTO equipmentToTrade : equipmentToTradeList) {
//            Equipment equipment = equipmentsService.findEquipment(equipmentListRebelToGive, equipmentToTrade.getName());
//
//            if(equipment == null){
//                throw new TradeException("O equipamento selecionado para a troca não existe");
//            }else{
//                int newQuantity = equipment.getQuantity() - equipmentToTrade.getQuantity();
//                if (newQuantity < 0) throw new TradeException("Não existe quantidade suficiente do item selecionado para a troca");
//            }
//        }
//    }
//
//    public void verifyPoints(List<EquipmentToTradeDTO> equipmentToTradeList1, List<EquipmentToTradeDTO> equipmentToTradeList2){
//        int pointsRebel1 = equipmentToTradeList1.stream()
//                .mapToInt(equipmentToTrade -> equipmentToTrade.getPoints()).sum();
//
//        int pointsRebel2 = equipmentToTradeList2.stream()
//                .mapToInt(equipmentToTrade -> equipmentToTrade.getPoints()).sum();
//
//        int difference = pointsRebel1 - pointsRebel2;
//        if(difference != 0){
//            throw new TradeException("A troca não respeita a igualdade de pontuação. A diferença de pontos é: " + difference);
//        }
//    }
//
//
//    public void verifyConditions(List<Equipment> equipmentsRebel1, List<Equipment> equipmentsRebel2,
//                                   List<EquipmentToTradeDTO> equipmentRequestToChangeRebel1,
//                                   List<EquipmentToTradeDTO> equipmentRequestToChangeRebel2 ){
//
//        // se tem o equipamento e a quantiddade
//        verifyNameAndQuantity(equipmentRequestToChangeRebel1, equipmentsRebel1);
//        verifyNameAndQuantity(equipmentRequestToChangeRebel2, equipmentsRebel2);
//
//        // pontuacao
//        verifyPoints(equipmentRequestToChangeRebel1,equipmentRequestToChangeRebel2);
//
//    }
//
//    public void tradeEquipment(Rebel rebelToReceive, List<EquipmentToTradeDTO> equipmentToTradeList,
//                               List<Equipment> equipmentListRebelToGive, List<Equipment> equipmentListRebelToReceive){
//
//        for (EquipmentToTradeDTO equipmentToTrade : equipmentToTradeList) {
//            Equipment equipment = equipmentsService.findEquipment(equipmentListRebelToGive, equipmentToTrade.getName());
//
//            int newQuantity = equipment.getQuantity() - equipmentToTrade.getQuantity();
//            int newPoints = newQuantity * EquipmentsEnum.getPoints(equipment.getName());
//
//            equipment.setQuantity(newQuantity);
//            equipment.setPoints(newPoints);
//            equipmentRepository.save(equipment);
//
//            Equipment newEquipmentRebel2 = equipmentsService.findEquipment(equipmentListRebelToReceive, equipmentToTrade.getName());
//            if(newEquipmentRebel2==null){
//                newEquipmentRebel2 = new Equipment(equipmentToTrade,rebelToReceive);
//            }else{
//                int newQuantityEquipment = newEquipmentRebel2.getQuantity()+ equipmentToTrade.getQuantity();
//                int newPointsEquipment = newQuantityEquipment * EquipmentsEnum.getPoints(equipment.getName());
//                newEquipmentRebel2.setQuantity(newQuantityEquipment);
//                newEquipmentRebel2.setPoints(newPointsEquipment);
//            }
//
//            equipmentRepository.save(newEquipmentRebel2);
//        }
//    }
//
//    public String changeEquipments(TradeEquipmentsDTO tradeEquipmentsDTO){
//
//        List<EquipmentToTradeDTO> equipmentRequestToChangeRebel1 = tradeEquipmentsDTO.getRebel1().getEquipmentRequestDTOList().stream().map(equipmentRequest -> equipmentRequest.toTrade()).collect(Collectors.toList());
//        List<EquipmentToTradeDTO> equipmentRequestToChangeRebel2 = tradeEquipmentsDTO.getRebel2().getEquipmentRequestDTOList().stream().map(equipmentRequest -> equipmentRequest.toTrade()).collect(Collectors.toList());
//
//        Long idRebel1 = tradeEquipmentsDTO.getRebel1().getId();
//        Long idRebel2 = tradeEquipmentsDTO.getRebel2().getId();
//
//        Rebel rebel1 = rebelsService.findRebelById(idRebel1);
//        Rebel rebel2 = rebelsService.findRebelById(idRebel2);
//
//        // tem traidor
//        verifyTraitor(rebel1, rebel2);
//
//        List<Equipment> equipmentsRebel1 = rebel1.getEquipments();
//        List<Equipment> equipmentsRebel2 = rebel2.getEquipments();
//
//        //verificar condições
//        verifyConditions(equipmentsRebel1,equipmentsRebel2,equipmentRequestToChangeRebel1,equipmentRequestToChangeRebel2);
//
//        // Fazer a troca
//        tradeEquipment(rebel2,equipmentRequestToChangeRebel1,equipmentsRebel1,equipmentsRebel2);
//        tradeEquipment(rebel1,equipmentRequestToChangeRebel2,equipmentsRebel2, equipmentsRebel1);
//
//        return "A troca foi realizada com sucesso!!!";
//    }

}
