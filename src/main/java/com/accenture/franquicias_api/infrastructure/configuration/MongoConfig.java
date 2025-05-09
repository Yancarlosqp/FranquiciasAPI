package com.accenture.franquicias_api.infrastructure.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
@Configuration
@EnableReactiveMongoRepositories(basePackages = "com.accenture.franquicias_api.infrastructure.repository")
public class MongoConfig {
    
}
