package com.letscode.starwarsapiwebflux.repositories;

import com.letscode.starwarsapiwebflux.models.Rebel;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface RebelRepository extends ReactiveCrudRepository<Rebel,String> {
}
