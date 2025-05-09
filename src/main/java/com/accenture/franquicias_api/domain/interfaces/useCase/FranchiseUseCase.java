package com.accenture.franquicias_api.domain.interfaces.useCase;

import com.accenture.franquicias_api.domain.entities.Franchise;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FranchiseUseCase {
    Mono<Franchise> createFranchise(Franchise franchise);
    Mono<Franchise> getFranchise(String id);
    Flux<Franchise> listFranchises();
    Mono<Franchise> updateFranchiseName(String franchiseId, String newName);
    
}
