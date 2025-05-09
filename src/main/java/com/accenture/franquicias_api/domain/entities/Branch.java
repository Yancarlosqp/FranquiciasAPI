package com.accenture.franquicias_api.domain.entities;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Branch {
    private String id;
    private String name;
    private List<Product> products;
}
