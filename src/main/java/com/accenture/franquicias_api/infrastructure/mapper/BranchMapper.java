package com.accenture.franquicias_api.infrastructure.mapper;

import java.util.Collections;
import org.springframework.stereotype.Component;
import com.accenture.franquicias_api.domain.entities.Branch;
import com.accenture.franquicias_api.infrastructure.entities.BranchEntity;

@Component
public class BranchMapper {
    private final ProductMapper productMapper;
    
    public BranchMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }
    
    public Branch toDomain(BranchEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return Branch.builder()
            .id(entity.getId())
            .name(entity.getName())
            .products(entity.getProducts() != null 
                ? entity.getProducts().stream().map(productMapper::toDomain).toList() 
                : Collections.emptyList())
            .build();
    }
    
    public BranchEntity toEntity(Branch domain) {
        if (domain == null) {
            return null;
        }
        
        return BranchEntity.builder()
            .id(domain.getId())
            .name(domain.getName())
            .products(domain.getProducts() != null 
                ? domain.getProducts().stream().map(productMapper::toEntity).toList() 
                : Collections.emptyList())
            .build();
    }
}
