package com.letscode.starwarsapiwebflux.services;


import com.letscode.starwarsapiwebflux.dtos.ReportResponse;
import com.letscode.starwarsapiwebflux.dtos.ResourceAverageResponse;
import com.letscode.starwarsapiwebflux.models.Rebel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ReportService {

    private final RebelService rebelService;

    private final CalculatorPointsService calculator;

    public Mono<ReportResponse> getReport(){
        Mono<ReportResponse> report = rebelService.getAllRebels().buffer()
                .map(rebels -> createReport(rebels))
                .single();
        return report;
    }

    private ResourceAverageResponse getAverageEquipment(List<Rebel> rebels, double totalRebels){
        int quantityWeapon = 0;
        int quantityAmmunation = 0;
        int quantityWater = 0;
        int quantityFood = 0;

        for (Rebel rebel : rebels) {
            quantityWeapon += rebel.getEquipments().getQuantityOfWeapon();
            quantityAmmunation += rebel.getEquipments().getQuantityOfAmmunition();
            quantityWater += rebel.getEquipments().getQuantityOfWater();
            quantityFood += rebel.getEquipments().getQuantityOfFood();
        }

        double averageWeapon = quantityWeapon/totalRebels;
        double averageAmmo = quantityAmmunation/totalRebels;
        double averageWater = quantityWater/totalRebels;
        double averageFood = quantityFood/totalRebels;

        return ResourceAverageResponse.builder()
                .averageAmmo(averageAmmo)
                .averageFood(averageFood)
                .averageWater(averageWater)
                .averageWeapon(averageWeapon)
                .totalRebels(totalRebels)
                .build();
    }

    private ReportResponse createReport(List<Rebel> rebelsList){
        List<Rebel> traitors = new ArrayList<>();
        List<Rebel> rebels = new ArrayList<>();
        List<Rebel> allRebels = rebelsList;

        // separar traidores e rebeldes
        for (Rebel rebel : allRebels) {
            if(rebel.getIsTraitor()==false){
                rebels.add(rebel);
            }else{
                traitors.add(rebel);
            }
        }

        double total = allRebels.size();
        double totalRebels = rebels.size();
        double totalTraitors = traitors.size();

        //Percentual de rebeldes e traidores
        double percentRebels = (totalRebels / total) * 100;
        double percentTraitors = (totalTraitors / total) * 100;

        //Pontos perdidos
        int totalPointsTraitor = traitors.stream()
                .mapToInt(rebel -> calculator.calculatorEquipmentsPoints(rebel.getEquipments()))
                .sum();

        //Media de equipamentos
        ResourceAverageResponse resourceAverageResponseDTO = getAverageEquipment(rebels, totalRebels);

        ReportResponse reportResponse = ReportResponse.builder()
                .rebelPercentage(percentRebels)
                .traitorsPercentage(percentTraitors)
                .resourceAverage(resourceAverageResponseDTO)
                .lostPoints(totalPointsTraitor)
                .build();

        return reportResponse;
    }



}
