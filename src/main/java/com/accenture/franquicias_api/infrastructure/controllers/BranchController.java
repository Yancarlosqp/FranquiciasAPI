package com.accenture.franquicias_api.infrastructure.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.accenture.franquicias_api.domain.entities.Branch;
import com.accenture.franquicias_api.domain.interfaces.useCase.BranchUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/franchises/{franchiseId}/branches")
@RequiredArgsConstructor
@Tag(name = "Branch", description = "Branch management APIs")
public class BranchController {
    private final BranchUseCase branchUseCase;
    
    @Operation(summary = "Create a new branch", description = "Creates a new branch and returns the created entity")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Branch created successfully",
                     content = @Content(schema = @Schema(implementation = Branch.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "Franchise not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Branch> addBranch(@PathVariable String franchiseId, @RequestBody Branch branch) {
        return branchUseCase.addBranchToFranchise(franchiseId, branch);
    }

    @Operation(summary = "Update branch name", description = "Updates the name of a specific branch")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Name updated successfully",
                     content = @Content(schema = @Schema(implementation = Branch.class))),
        @ApiResponse(responseCode = "404", description = "Branch not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/branches/{branchId}/name")
    public Mono<Branch> updateBranchName(
            @PathVariable String branchId, 
            @RequestBody NameUpdateRequest request) {
        return branchUseCase.updateBranchName(branchId, request.name());
    }
    
    record NameUpdateRequest(String name) {}
}
