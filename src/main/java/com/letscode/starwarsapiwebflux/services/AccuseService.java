package com.letscode.starwarsapiwebflux.services;

import com.letscode.starwarsapiwebflux.models.Rebel;
import com.letscode.starwarsapiwebflux.repositories.RebelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class AccuseService {

    private final RebelService rebelService;

    private final RebelRepository rebelRepository;

    public Mono<String> accuseTraitor(String id){
        Mono<Rebel> rebel = rebelService.findRebelById(id);
        return rebel.flatMap(rebel1 -> updateQntAccusation(rebel1)).map(rebel1 -> message(rebel1));
    }

    private Mono<Rebel> updateQntAccusation(Rebel rebel){
        int newQntAccusation = rebel.getQntAccusation() + 1;
        if (newQntAccusation >= 3) rebel.setIsTraitor(true);
        rebel.setQntAccusation(newQntAccusation);
        return rebelService.saveRebel(rebel);

    }

    private String message(Rebel rebel){
        String messageIsTraitor;
        if(rebel.getIsTraitor() == true){
            messageIsTraitor = " é um traidor!!!";
        }else{
            messageIsTraitor = " não é um traidor!";
        }
        String mensage = "O rebelde " + rebel.getName() + " recebeu uma acusação de ser traidor, agora ele possui "
                + rebel.getQntAccusation() +" acusações. O rebelde " + rebel.getName() + messageIsTraitor;
        return mensage;
    }

}
