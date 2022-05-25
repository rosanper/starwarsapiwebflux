package com.letscode.starwarsapiwebflux.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum EquipmentsEnum {
    WEAPON("Weapon",4),
    AMMUNITION("Ammunition", 3),
    WATER("Water", 2),
    FOOD("Food", 1);

    private String equipmentName;
    private Integer equipmentPoints;

    public static String getName(String name){
        return EquipmentsEnum.valueOf(name.toUpperCase()).getEquipmentName();
    }

    public static int getPoints(String name){
        return EquipmentsEnum.valueOf(name.toUpperCase()).getEquipmentPoints();
    }
}
