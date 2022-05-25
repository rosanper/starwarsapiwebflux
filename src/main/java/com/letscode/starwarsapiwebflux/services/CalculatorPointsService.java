package com.letscode.starwarsapiwebflux.services;


import com.letscode.starwarsapiwebflux.dtos.EquipmentRequest;
import com.letscode.starwarsapiwebflux.enums.EquipmentsEnum;
import com.letscode.starwarsapiwebflux.models.Rebel;
import org.springframework.stereotype.Service;

@Service
public class CalculatorPointsService {

    public int calculatorRebelPoints(Rebel rebel){
        int weaponPoints = rebel.getEquipments().getQuantityOfWeapon() * EquipmentsEnum.getPoints("Weapon");
        int ammunitionPoints = rebel.getEquipments().getQuantityOfAmmunition()*EquipmentsEnum.getPoints("Ammunition");
        int waterPoints = rebel.getEquipments().getQuantityOfWater() * EquipmentsEnum.getPoints("Water");
        int foodPoints = rebel.getEquipments().getQuantityOfFood() * EquipmentsEnum.getPoints("Food");

        int points = weaponPoints + ammunitionPoints + waterPoints + foodPoints;

        return points;

    }

    public int calculatorEquipmentsPoints(EquipmentRequest equipment){
        int weaponPoints = equipment.getQuantityOfWeapon() * EquipmentsEnum.getPoints("Weapon");
        int ammunitionPoints = equipment.getQuantityOfAmmunition()*EquipmentsEnum.getPoints("Ammunition");
        int waterPoints = equipment.getQuantityOfWater() * EquipmentsEnum.getPoints("Water");
        int foodPoints = equipment.getQuantityOfFood() * EquipmentsEnum.getPoints("Food");

        int points = weaponPoints + ammunitionPoints + waterPoints + foodPoints;

        return points;

    }

}
