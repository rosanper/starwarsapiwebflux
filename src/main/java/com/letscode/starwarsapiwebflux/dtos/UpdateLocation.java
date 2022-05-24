package com.letscode.starwarsapiwebflux.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateLocation {
    private String rebelId;
    private LocationRequest locationRequest;
}
