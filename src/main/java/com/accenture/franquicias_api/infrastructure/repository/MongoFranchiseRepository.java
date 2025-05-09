package com.accenture.franquicias_api.infrastructure.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import com.accenture.franquicias_api.infrastructure.entities.FranchiseEntity;

@Repository
public interface MongoFranchiseRepository extends ReactiveMongoRepository<FranchiseEntity, String> {

}
