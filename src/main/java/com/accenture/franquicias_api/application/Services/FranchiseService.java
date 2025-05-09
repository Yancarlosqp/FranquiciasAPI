package com.accenture.franquicias_api.application.Services;

import org.springframework.stereotype.Service;
import com.accenture.franquicias_api.domain.entities.Franchise;
import com.accenture.franquicias_api.domain.exception.EntityNotFoundException;
import com.accenture.franquicias_api.domain.interfaces.repository.FranchiseRepository;
import com.accenture.franquicias_api.domain.interfaces.useCase.FranchiseUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FranchiseService implements FranchiseUseCase {

    private final FranchiseRepository franchiseRepository;
    
    @Override
    public Mono<Franchise> createFranchise(Franchise franchise) {
        return franchiseRepository.save(franchise);
    }
    
    @Override
    public Mono<Franchise> getFranchise(String id) {
        return franchiseRepository.findById(id);
    }
    
    @Override
    public Flux<Franchise> listFranchises() {
        return franchiseRepository.findAll();
    }

    @Override
    public Mono<Franchise> updateFranchiseName(String franchiseId, String newName) {
        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Franchise not found with ID: " + franchiseId)))
                .flatMap(franchise -> {
                    franchise.setName(newName);
                    return franchiseRepository.save(franchise);
                });
    }
    
}
