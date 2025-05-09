package com.accenture.franquicias_api.infrastructure.repository;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.accenture.franquicias_api.domain.entities.Branch;
import com.accenture.franquicias_api.domain.exception.EntityNotFoundException;
import com.accenture.franquicias_api.domain.interfaces.repository.BranchRepository;
import com.accenture.franquicias_api.infrastructure.entities.BranchEntity;
import com.accenture.franquicias_api.infrastructure.mapper.BranchMapper;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MongoBranchAdapter implements BranchRepository{

    private final MongoFranchiseRepository franchiseRepository;
    private final BranchMapper branchMapper;

    @Override
    public Mono<Branch> addBranchToFranchise(String franchiseId, Branch branch) {
        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Franchise not found with ID: " + franchiseId)))
                .flatMap(franchise -> {
                    if (franchise.getBranches() == null) {
                        franchise.setBranches(new ArrayList<>());
                    }

                    BranchEntity branchEntity = branchMapper.toEntity(branch);
                    if (branchEntity.getId() == null) {
                        branchEntity.setId(UUID.randomUUID().toString());
                    }

                    franchise.getBranches().add(branchEntity);
                    return franchiseRepository.save(franchise);
                })
                .map(savedFranchise -> {
                    return branchMapper.toDomain(savedFranchise.getBranches()
                            .get(savedFranchise.getBranches().size() - 1));
                });
    }

    @Override
    public Mono<Branch> findById(String id) {
        return franchiseRepository.findAll()
                .flatMapIterable(franchise -> franchise.getBranches() != null ? 
                        franchise.getBranches() : new ArrayList<>())
                .filter(branch -> branch.getId().equals(id))
                .next()
                .map(branchMapper::toDomain)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Branch not found with ID: " + id)));
    }

    @Override
    public Mono<Branch> updateBranchName(String branchId, String newName) {
        return franchiseRepository.findAll()
                .flatMapIterable(franchise -> franchise.getBranches() != null ? 
                        franchise.getBranches() : new ArrayList<>())
                .filter(branch -> branch.getId().equals(branchId))
                .next()
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Branch not found with ID: " + branchId)))
                .flatMap(branchEntity -> {
                    branchEntity.setName(newName);
                    
                    return franchiseRepository.findAll()
                            .flatMap(franchise -> {
                                franchise.getBranches().stream()
                                        .filter(b -> b.getId().equals(branchId))
                                        .findFirst()
                                        .ifPresent(b -> b.setName(newName));
                                return franchiseRepository.save(franchise);
                            })
                            .next()
                            .map(savedFranchise -> {
                                BranchEntity savedBranch = savedFranchise.getBranches().stream()
                                        .filter(b -> b.getId().equals(branchId))
                                        .findFirst()
                                        .orElseThrow(() -> new EntityNotFoundException("Branch not found after save operation"));
                                
                                return branchMapper.toDomain(savedBranch);
                            });
                });
    }
}
