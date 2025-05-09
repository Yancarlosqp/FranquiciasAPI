package com.accenture.franquicias_api.infrastructure.entities;
import org.springframework.data.annotation.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductEntity {
    
    @Id
    private String id;
    private String name;
    private int stock;
}
