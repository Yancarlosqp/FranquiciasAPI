package com.accenture.franquicias_api.infrastructure.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.accenture.franquicias_api.domain.entities.Product;
import com.accenture.franquicias_api.domain.interfaces.useCase.ProductUseCase;
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
@RequiredArgsConstructor
@Tag(name = "Product", description = "Product management APIs")
public class ProductController {

    private final ProductUseCase productUseCase;
    
    @Operation(summary = "Create a new product", description = "Creates a new product and returns the created entity")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Product created successfully",
                     content = @Content(schema = @Schema(implementation = Product.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "Branch not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/branches/{branchId}/products")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Product> addProduct(@PathVariable String branchId, @RequestBody Product product) {
        return productUseCase.addProductToBranch(branchId, product);
    }
    
    @Operation(summary = "Remove a product", description = "Removes a product from a branch")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Product removed successfully"),
        @ApiResponse(responseCode = "404", description = "Branch or product not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/branches/{branchId}/products/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> removeProduct(@PathVariable String branchId, @PathVariable String productId) {
        return productUseCase.removeProductFromBranch(branchId, productId);
    }
    
    @Operation(summary = "Update product stock", description = "Updates the stock quantity of a specific product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Stock updated successfully",
                     content = @Content(schema = @Schema(implementation = Product.class))),
        @ApiResponse(responseCode = "400", description = "Invalid stock value"),
        @ApiResponse(responseCode = "404", description = "Branch or product not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/branches/{branchId}/products/{productId}/stock")
    public Mono<Product> updateStock(
        @PathVariable String branchId, 
        @PathVariable String productId, 
        @RequestBody StockUpdateRequest request) {
        return productUseCase.updateProductStock(branchId, productId, request.stock());
    }
    
    @Operation(summary = "Get products with maximum stock", 
              description = "Returns products with the highest stock level for each branch in a franchise")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the list",
                     content = @Content(schema = @Schema(implementation = Product.class))),
        @ApiResponse(responseCode = "404", description = "Franchise not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/franchises/{franchiseId}/products/max-stock")
    public Flux<Product> getProductsWithMaxStock(@PathVariable String franchiseId) {
        return productUseCase.getProductsWithMaxStockByFranchise(franchiseId);
    }
    
    record StockUpdateRequest(int stock) {}

    @Operation(summary = "Update product name", description = "Updates the name of a specific product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Name updated successfully",
                     content = @Content(schema = @Schema(implementation = Product.class))),
        @ApiResponse(responseCode = "404", description = "Branch or product not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/branches/{branchId}/products/{productId}/name")
    public Mono<Product> updateProductName(
            @PathVariable String branchId,
            @PathVariable String productId,
            @RequestBody NameUpdateRequest request) {
        return productUseCase.updateProductName(branchId, productId, request.name());
    }
    
    record NameUpdateRequest(String name) {}
    
}
