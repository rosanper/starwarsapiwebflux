package com.letscode.starwarsapiwebflux.models;

import com.letscode.starwarsapiwebflux.dtos.EquipmentRequest;
import com.letscode.starwarsapiwebflux.dtos.LocationRequest;
import com.letscode.starwarsapiwebflux.dtos.RebelRequest;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Document
@Data
@NoArgsConstructor
public class Rebel {

    @Id
    private String id;
    private String name;
    private Integer age;
    private String gender;

    private Integer qntAccusation;
    private Boolean isTraitor;

    private EquipmentRequest equipments;

    private LocationRequest locations;

    public Rebel(RebelRequest rebelRequest) {
        this.id = UUID.randomUUID().toString();
        this.name = rebelRequest.getName();
        this.age = rebelRequest.getAge();
        this.gender = rebelRequest.getGender();
        this.qntAccusation = 0;
        this.isTraitor = false;
        this.equipments = rebelRequest.getEquipmentsRequest();
        this.locations = rebelRequest.getLocationRequest();
    }
}
