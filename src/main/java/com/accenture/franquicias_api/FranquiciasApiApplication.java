package com.accenture.franquicias_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
@ComponentScan(basePackages = {
    "com.accenture.franquicias_api.domain",
    "com.accenture.franquicias_api.application",
    "com.accenture.franquicias_api.infrastructure",
})
public class FranquiciasApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FranquiciasApiApplication.class, args);
	}

}
