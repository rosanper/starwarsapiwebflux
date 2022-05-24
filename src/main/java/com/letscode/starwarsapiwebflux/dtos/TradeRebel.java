package com.letscode.starwarsapiwebflux.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TradeRebel {

    private String rebelId;

    private List<EquipmentRequest> equipmentRequests;
}
