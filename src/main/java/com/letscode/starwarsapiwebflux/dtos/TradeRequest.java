package com.letscode.starwarsapiwebflux.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TradeRequest {

    private TradeRebel rebel1;
    private TradeRebel rebel2;
}
