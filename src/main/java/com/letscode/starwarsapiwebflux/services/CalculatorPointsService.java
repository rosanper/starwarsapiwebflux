package com.letscode.starwarsapiwebflux.services;

import com.letscode.starwarsapiwebflux.dtos.EquipmentRequest;
import com.letscode.starwarsapiwebflux.enums.EquipmentsEnum;
import com.letscode.starwarsapiwebflux.models.Rebel;
import org.springframework.stereotype.Service;

@Service
public class CalculatorPointsService {

//    public int calculatorEquipmentPoints(EquipmentRequest equipment){
//        int points = equipment.getQuantity() * EquipmentsEnum.getPoints(equipment.getName());
//        return points;
//    }
//
//    public int calculatorRebelPoints(Rebel rebel){
//        int points = 0;
//        for (EquipmentRequest equipment : rebel.getEquipments()) {
//            points += calculatorEquipmentPoints(equipment);
//        }
//        return points;
//
//    }

}
