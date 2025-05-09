package com.accenture.franquicias_api.application.Services;

import org.springframework.stereotype.Service;
import com.accenture.franquicias_api.domain.entities.Branch;
import com.accenture.franquicias_api.domain.interfaces.repository.BranchRepository;
import com.accenture.franquicias_api.domain.interfaces.useCase.BranchUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BranchService implements BranchUseCase{

    private final BranchRepository branchRepository;
    
    @Override
    public Mono<Branch> addBranchToFranchise(String franchiseId, Branch branch) {
        return branchRepository.addBranchToFranchise(franchiseId, branch);
    }

    @Override
    public Mono<Branch> updateBranchName(String branchId, String newName) {
        return branchRepository.updateBranchName(branchId, newName);
    }
}
