package com.letscode.starwarsapiwebflux.controllers;

import com.letscode.starwarsapiwebflux.dtos.TradeRequest;
import com.letscode.starwarsapiwebflux.models.Rebel;
import com.letscode.starwarsapiwebflux.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/rebels")
@RequiredArgsConstructor
public class RebelControler {

    private final RebelService rebelsService;

    private final AccuseService accuseService;

    private final LocationService locationService;

    private final ReportService reportService;

    private final TradeService tradeService;

    @GetMapping
    public Flux<Rebel> getAll(){
        return rebelsService.getAllRebels();
    }

    @GetMapping("/{id}")
    public Mono<Rebel> getRebelById(@PathVariable String id){
        return rebelsService.findRebelById(id);
    }

    @PutMapping("/trade")
    public Flux<Rebel> tradeEquipments(@RequestBody TradeRequest tradeRequest){
        return tradeService.tradeEquipments(tradeRequest);
    }


}
