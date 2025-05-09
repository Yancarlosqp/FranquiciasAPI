package com.accenture.franquicias_api.infrastructure.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/test-api")
@Tag(name = "Test", description = "Test endpoint")
public class TestController {
    @Operation(summary = "Test endpoint", description = "Simple test endpoint to verify API is running")
    @GetMapping
    public Mono<String> test() {
        return Mono.just("API is working!");
    }
}
