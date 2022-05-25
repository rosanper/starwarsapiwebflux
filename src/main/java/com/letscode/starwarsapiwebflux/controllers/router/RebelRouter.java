package com.letscode.starwarsapiwebflux.controllers.router;

import com.letscode.starwarsapiwebflux.controllers.handler.RebelHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class RebelRouter {

    @Bean
    public RouterFunction<ServerResponse> router (RebelHandler rebelHandler){
        return RouterFunctions
                .route(GET("/rebels"),rebelHandler::getAllRebels)
                .andRoute(GET("/rebels/{id}"),rebelHandler::findRebelById)
                .andRoute(POST("/rebels"),rebelHandler::createRebel)
                .andRoute(DELETE("/rebels/{id}"),rebelHandler::deleteRebel)
                .andRoute(PUT("/rebels/accuse/{id}"),rebelHandler::accuseRebel)
                .andRoute(PUT("/rebels/update-location"),rebelHandler::updateLocation)
                .andRoute(GET("/report"),rebelHandler::getReport)
                ;
    }
}
