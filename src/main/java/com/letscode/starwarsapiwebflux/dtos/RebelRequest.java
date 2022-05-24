package com.letscode.starwarsapiwebflux.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RebelRequest {
    private String name;
    private Integer age;
    private String gender;

    private List<EquipmentRequest> equipmentsRequest = new ArrayList<>();

    private LocationRequest locationRequest;
}
