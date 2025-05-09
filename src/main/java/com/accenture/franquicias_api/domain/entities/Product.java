package com.accenture.franquicias_api.domain.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {
    private String id;
    private String name;
    private int stock;
}
