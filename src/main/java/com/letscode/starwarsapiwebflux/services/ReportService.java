package com.letscode.starwarsapiwebflux.services;

import com.letscode.starwarsapiwebflux.dtos.EquipmentRequest;
import com.letscode.starwarsapiwebflux.dtos.ReportResponse;
import com.letscode.starwarsapiwebflux.dtos.ResourceAverageResponse;
import com.letscode.starwarsapiwebflux.models.Rebel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public ResourceAverageResponse getAverageEquipment(List<Rebel> rebels, double totalRebels){
        int quantityWater = 0;
        int quantityFood = 0;
        int quantityWeapon = 0;
        int quantityAmmo = 0;

        //TODO - Esta com problema, refazer
        for (Rebel rebel : rebels) {
            for (EquipmentRequest equipment : rebel.getEquipments()) {
                if(equipment.getName()=="arma"){
                    quantityWeapon += equipment.getQuantity();
                }
                if(equipment.getName()=="municao"){
                    quantityAmmo += equipment.getQuantity();
                }
                if(equipment.getName()=="agua"){
                    quantityWater += equipment.getQuantity();
                }
                if(equipment.getName()=="comida"){
                    quantityFood += equipment.getQuantity();
                }
            }
        }

        double averageWeapon = quantityWeapon/totalRebels;
        double averageAmmo = quantityAmmo/totalRebels;
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

    public ReportResponse createReport(List<Rebel> rebelsList){
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
        int totalPointsTraitor = traitors.stream().mapToInt(rebel -> calculator.calculatorRebelPoints(rebel)).sum();

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
