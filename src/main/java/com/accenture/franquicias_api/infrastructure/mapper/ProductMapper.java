package com.accenture.franquicias_api.infrastructure.mapper;
import org.springframework.stereotype.Component;
import com.accenture.franquicias_api.domain.entities.Product;
import com.accenture.franquicias_api.infrastructure.entities.ProductEntity;

@Component
public class ProductMapper {
    public Product toDomain(ProductEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return Product.builder()
            .id(entity.getId())
            .name(entity.getName())
            .stock(entity.getStock())
            .build();
    }
    
    public ProductEntity toEntity(Product domain) {
        if (domain == null) {
            return null;
        }
        
        return ProductEntity.builder()
            .id(domain.getId())
            .name(domain.getName())
            .stock(domain.getStock())
            .build();
    }
}
