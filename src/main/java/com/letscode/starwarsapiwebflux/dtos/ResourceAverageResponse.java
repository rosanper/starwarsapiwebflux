package com.letscode.starwarsapiwebflux.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResourceAverageResponse {
    private Double averageWeapon;
    private Double averageAmmo;
    private Double averageWater;
    private Double averageFood;
    private Double totalRebels;
}
