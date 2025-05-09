package com.accenture.franquicias_api.infrastructure.entities;

import java.util.List;
import org.springframework.data.annotation.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BranchEntity {
    @Id
    private String id;
    private String name;
    private List<ProductEntity> products;
}
