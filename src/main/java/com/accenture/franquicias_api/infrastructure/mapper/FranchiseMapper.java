package com.accenture.franquicias_api.infrastructure.mapper;

import java.util.Collections;
import org.springframework.stereotype.Component;
import com.accenture.franquicias_api.domain.entities.Franchise;
import com.accenture.franquicias_api.infrastructure.entities.FranchiseEntity;

@Component
public class FranchiseMapper {
    private final BranchMapper branchMapper;
    
    public FranchiseMapper(BranchMapper branchMapper) {
        this.branchMapper = branchMapper;
    }
    
    public Franchise toDomain(FranchiseEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return Franchise.builder()
            .id(entity.getId())
            .name(entity.getName())
            .branches(entity.getBranches() != null 
                ? entity.getBranches().stream().map(branchMapper::toDomain).toList() 
                : Collections.emptyList())
            .build();
    }
    
    public FranchiseEntity toEntity(Franchise domain) {
        if (domain == null) {
            return null;
        }
        
        return FranchiseEntity.builder()
            .id(domain.getId())
            .name(domain.getName())
            .branches(domain.getBranches() != null 
                ? domain.getBranches().stream().map(branchMapper::toEntity).toList() 
                : Collections.emptyList())
            .build();
    }
}
