package com.accenture.franquicias_api.infrastructure.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.accenture.franquicias_api.domain.entities.Franchise;
import com.accenture.franquicias_api.domain.interfaces.useCase.FranchiseUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/franchises")
@RequiredArgsConstructor
@Tag(name = "Franchise", description = "Franchise management APIs")
public class FranchiseController {

    private final FranchiseUseCase franchiseUseCase;
    
    @Operation(summary = "Create a new franchise", description = "Creates a new franchise and returns the created entity")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Franchise created successfully",
                     content = @Content(schema = @Schema(implementation = Franchise.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Franchise> createFranchise(@RequestBody Franchise franchise) {
        return franchiseUseCase.createFranchise(franchise);
    }
    
    @Operation(summary = "Get a franchise by ID", description = "Returns a single franchise by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Franchise found",
                     content = @Content(schema = @Schema(implementation = Franchise.class))),
        @ApiResponse(responseCode = "404", description = "Franchise not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public Mono<Franchise> getFranchise(@PathVariable String id) {
        return franchiseUseCase.getFranchise(id);
    }
    
    @Operation(summary = "List all franchises", description = "Returns all franchises")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the list",
                     content = @Content(schema = @Schema(implementation = Franchise.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public Flux<Franchise> listFranchises() {
        return franchiseUseCase.listFranchises();
    }

    @Operation(summary = "Update franchise name", description = "Updates the name of a franchise")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Name updated successfully",
                     content = @Content(schema = @Schema(implementation = Franchise.class))),
        @ApiResponse(responseCode = "404", description = "Franchise not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{franchiseId}/name")
    public Mono<Franchise> updateFranchiseName(
            @PathVariable String franchiseId, 
            @RequestBody NameUpdateRequest request) {
        return franchiseUseCase.updateFranchiseName(franchiseId, request.name());
    }

    record NameUpdateRequest(String name) {}
    
}
