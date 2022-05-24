package com.letscode.starwarsapiwebflux.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LocationRequest {
    private Integer latitude;
    private Integer longitude;
    private String name;
}
