package com.accenture.franquicias_api.domain.interfaces.repository;

import com.accenture.franquicias_api.domain.entities.Branch;
import reactor.core.publisher.Mono;

public interface BranchRepository {
    Mono<Branch> addBranchToFranchise(String franchiseId, Branch branch);
    Mono<Branch> findById(String id);
    Mono<Branch> updateBranchName(String branchId, String newName);
}
