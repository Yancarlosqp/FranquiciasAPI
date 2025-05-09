package com.accenture.franquicias_api.domain.interfaces.useCase;

import com.accenture.franquicias_api.domain.entities.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductUseCase {
    Mono<Product> addProductToBranch(String branchId, Product product);
    Mono<Void> removeProductFromBranch(String branchId, String productId);
    Mono<Product> updateProductStock(String branchId, String productId, int newStock);
    Flux<Product> getProductsWithMaxStockByFranchise(String franchiseId);
    Mono<Product> updateProductName(String branchId, String productId, String newName);
}
