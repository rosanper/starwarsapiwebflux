package com.letscode.starwarsapiwebflux.controllers.handler;

import com.letscode.starwarsapiwebflux.dtos.RebelRequest;
import com.letscode.starwarsapiwebflux.dtos.ReportResponse;
import com.letscode.starwarsapiwebflux.dtos.UpdateLocation;
import com.letscode.starwarsapiwebflux.models.Rebel;
import com.letscode.starwarsapiwebflux.services.AccuseService;
import com.letscode.starwarsapiwebflux.services.LocationService;
import com.letscode.starwarsapiwebflux.services.RebelService;
import com.letscode.starwarsapiwebflux.services.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RebelHandler {
    private final RebelService rebelsService;

    private final AccuseService accuseService;

    private final LocationService locationService;

    private final ReportService reportService;

    public Mono<ServerResponse> getAllRebels(ServerRequest request){
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(rebelsService.getAllRebels(), Rebel.class);
    }

    public Mono<ServerResponse> findRebelById(ServerRequest request){
        String id = request.pathVariable("id");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(rebelsService.findRebelById(id),Rebel.class);
    }

    public Mono<ServerResponse> createRebel(ServerRequest request){
        Mono<RebelRequest> rebelRequestMono = request.bodyToMono(RebelRequest.class);
        return ServerResponse
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(
                        rebelRequestMono.flatMap(rebelsService::createRebel),Rebel.class));
    }

    public Mono<ServerResponse> deleteRebel(ServerRequest request){
        String id = request.pathVariable("id");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(rebelsService.deleteRebel(id),Void.class);
    }

    public Mono<ServerResponse> accuseRebel(ServerRequest request){
        String id = request.pathVariable("id");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(accuseService.accuseTraitor(id),String.class);
    }

    public Mono<ServerResponse> updateLocation(ServerRequest request){
        Mono<UpdateLocation> updateLocationMono = request.bodyToMono(UpdateLocation.class);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(
                        updateLocationMono.flatMap(locationService::updateLocation),Rebel.class));
    }

//    public Mono<ServerResponse> getReport(ServerRequest request){
//        return ServerResponse.ok()
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(reportService.getReport(), ReportResponse.class);
//    }
}
