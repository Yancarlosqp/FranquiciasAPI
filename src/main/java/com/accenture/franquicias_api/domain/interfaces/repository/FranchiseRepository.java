package com.accenture.franquicias_api.domain.interfaces.repository;

import com.accenture.franquicias_api.domain.entities.Franchise;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FranchiseRepository {
    Mono<Franchise> save(Franchise franchise);
    Mono<Franchise> findById(String id);
    Flux<Franchise> findAll();
}
