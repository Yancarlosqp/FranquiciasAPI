package com.accenture.franquicias_api.domain.interfaces.useCase;

import com.accenture.franquicias_api.domain.entities.Branch;
import reactor.core.publisher.Mono;

public interface BranchUseCase {
    Mono<Branch> addBranchToFranchise(String franchiseId, Branch branch);
    Mono<Branch> updateBranchName(String branchId, String newName);
}
