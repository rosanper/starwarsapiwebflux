package com.letscode.starwarsapiwebflux.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EquipmentRequest {
    private Integer quantityOfWeapon;
    private Integer quantityOfAmmunition;
    private Integer quantityOfWater;
    private Integer quantityOfFood;
}
