package com.accenture.franquicias_api.application.Services;

import org.springframework.stereotype.Service;
import com.accenture.franquicias_api.domain.entities.Product;
import com.accenture.franquicias_api.domain.interfaces.repository.ProductRepository;
import com.accenture.franquicias_api.domain.interfaces.useCase.ProductUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductUseCase {

    private final ProductRepository productRepository;
    
    @Override
    public Mono<Product> addProductToBranch(String branchId, Product product) {
        return productRepository.addProductToBranch(branchId, product);
    }
    
    @Override
    public Mono<Void> removeProductFromBranch(String branchId, String productId) {
        return productRepository.removeProductFromBranch(branchId, productId);
    }
    
    @Override
    public Mono<Product> updateProductStock(String branchId, String productId, int newStock) {
        return productRepository.updateProductStock(branchId, productId, newStock);
    }
    
    @Override
    public Flux<Product> getProductsWithMaxStockByFranchise(String franchiseId) {
        return productRepository.findProductsWithMaxStockByFranchise(franchiseId);
    }

    @Override
    public Mono<Product> updateProductName(String branchId, String productId, String newName) {
        return productRepository.updateProductName(branchId, productId, newName);
    }
}
