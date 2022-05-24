package com.letscode.starwarsapiwebflux.services;

import com.letscode.starwarsapiwebflux.dtos.LocationRequest;
import com.letscode.starwarsapiwebflux.dtos.UpdateLocation;
import com.letscode.starwarsapiwebflux.models.Rebel;
import com.letscode.starwarsapiwebflux.repositories.RebelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final RebelService rebelService;

    public Mono<Rebel> updateLocation(UpdateLocation updateLocation){
        Mono<Rebel> rebel = rebelService.findRebelById(updateLocation.getRebelId());
        return rebel.map(rebel1 -> changeLocation(rebel1, updateLocation.getLocationRequest()))
                .flatMap(rebel1 -> rebelService.saveRebel(rebel1));
    }

    public Rebel changeLocation(Rebel rebel, LocationRequest locationRequest){
        rebel.setLocations(locationRequest);
        return rebel;
    }



}
