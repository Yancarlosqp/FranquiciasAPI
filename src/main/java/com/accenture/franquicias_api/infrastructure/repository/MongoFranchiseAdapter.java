package com.accenture.franquicias_api.infrastructure.repository;

import org.springframework.stereotype.Component;
import com.accenture.franquicias_api.domain.entities.Franchise;
import com.accenture.franquicias_api.domain.interfaces.repository.FranchiseRepository;
import com.accenture.franquicias_api.infrastructure.mapper.FranchiseMapper;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MongoFranchiseAdapter implements FranchiseRepository {
    
    private final MongoFranchiseRepository mongoRepository;
    private final FranchiseMapper franchiseMapper;
    
    @Override
    public Mono<Franchise> save(Franchise franchise) {
        return Mono.just(franchise)
                .map(franchiseMapper::toEntity)
                .flatMap(mongoRepository::save)
                .map(franchiseMapper::toDomain);
    }

    @Override
    public Mono<Franchise> findById(String id) {
        return mongoRepository.findById(id)
                .map(franchiseMapper::toDomain);
    }

    @Override
    public Flux<Franchise> findAll() {
        return mongoRepository.findAll()
                .map(franchiseMapper::toDomain);
    }
}
