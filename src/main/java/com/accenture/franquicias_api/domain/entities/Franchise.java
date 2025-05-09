package com.accenture.franquicias_api.domain.entities;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Franchise {
    private String id;
    private String name;
    private List<Branch> branches;
}
