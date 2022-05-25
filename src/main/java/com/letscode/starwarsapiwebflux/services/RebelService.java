package com.letscode.starwarsapiwebflux.services;

import com.letscode.starwarsapiwebflux.dtos.RebelRequest;
import com.letscode.starwarsapiwebflux.models.Rebel;
import com.letscode.starwarsapiwebflux.repositories.RebelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RebelService {

    private final RebelRepository rebelsRepository;

    public Flux<Rebel> getAllRebels(){
        return rebelsRepository.findAll();
    }

    public Mono<Rebel> createRebel(RebelRequest rebelRequest){
        Rebel rebel = new Rebel(rebelRequest);
        return rebelsRepository.save(rebel);
    }

    public Mono<Rebel> findRebelById(String id){
        return rebelsRepository.findById(id);
    }

    public Mono<Void> deleteRebel(String id){
        return rebelsRepository.deleteById(id);
    }

    public Mono<Rebel>  saveRebel(Rebel rebel){
        return rebelsRepository.save(rebel);
    }

    public Flux<Rebel> saveRebelList(List<Rebel> rebels){
        Flux<Rebel> rebelFlux = rebelsRepository.saveAll(rebels);
        return rebelFlux;
    }
}
