package com.accenture.franquicias_api.infrastructure.entities;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "franchises")
public class FranchiseEntity {
    @Id
    private String id;
    private String name;
    private List<BranchEntity> branches;
}
