package com.letscode.starwarsapiwebflux.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EquipmentRequest {
    private String name;
    private Integer quantity;
}
