package com.accenture.franquicias_api.infrastructure.repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Component;
import com.accenture.franquicias_api.domain.entities.Product;
import com.accenture.franquicias_api.domain.exception.EntityNotFoundException;
import com.accenture.franquicias_api.domain.interfaces.repository.ProductRepository;
import com.accenture.franquicias_api.infrastructure.entities.BranchEntity;
import com.accenture.franquicias_api.infrastructure.entities.ProductEntity;
import com.accenture.franquicias_api.infrastructure.mapper.ProductMapper;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MongoProductAdapter implements ProductRepository {
    private final MongoFranchiseRepository franchiseRepository;
    private final ProductMapper productMapper;

    @Override
    public Mono<Product> addProductToBranch(String branchId, Product product) {
        return franchiseRepository.findAll()
                .flatMapIterable(franchise -> franchise.getBranches() != null ? franchise.getBranches() : new ArrayList<>())
                .filter(branch -> branch.getId().equals(branchId))
                .next()
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Branch not found with ID: " + branchId)))
                .flatMap(branch -> {
                    if (branch.getProducts() == null) {
                        branch.setProducts(new ArrayList<>());
                    }

                    ProductEntity productEntity = productMapper.toEntity(product);
                    if (productEntity.getId() == null) {
                        productEntity.setId(UUID.randomUUID().toString());
                    }

                    branch.getProducts().add(productEntity);

                    return franchiseRepository.findAll()
                            .flatMap(franchise -> {
                                franchise.getBranches().stream()
                                        .filter(b -> b.getId().equals(branchId))
                                        .findFirst()
                                        .ifPresent(b -> b.setProducts(branch.getProducts()));
                                return franchiseRepository.save(franchise);
                            })
                            .next();
                })
                .map(savedFranchise -> {
                    BranchEntity branch = savedFranchise.getBranches().stream()
                            .filter(b -> b.getId().equals(branchId))
                            .findFirst()
                            .orElseThrow(() -> new EntityNotFoundException("Branch not found after save operation"));

                    ProductEntity savedProduct = branch.getProducts().get(branch.getProducts().size() - 1);
                    return productMapper.toDomain(savedProduct);
                });
    }

    @Override
    public Mono<Void> removeProductFromBranch(String branchId, String productId) {
        return franchiseRepository.findAll()
                .flatMapIterable(franchise -> franchise.getBranches() != null ? franchise.getBranches() : new ArrayList<>())
                .filter(branch -> branch.getId().equals(branchId))
                .next()
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Branch not found with ID: " + branchId)))
                .flatMap(branch -> {
                    if (branch.getProducts() == null) {
                        return Mono.error(new EntityNotFoundException("No products found in branch with ID: " + branchId));
                    }

                    boolean removed = branch.getProducts().removeIf(product -> product.getId().equals(productId));
                    if (!removed) {
                        return Mono.error(new EntityNotFoundException("Product not found with ID: " + productId));
                    }

                    return franchiseRepository.findAll()
                            .flatMap(franchise -> {
                                franchise.getBranches().stream()
                                        .filter(b -> b.getId().equals(branchId))
                                        .findFirst()
                                        .ifPresent(b -> b.setProducts(branch.getProducts()));
                                return franchiseRepository.save(franchise);
                            })
                            .then();
                });
    }

    @Override
    public Mono<Product> updateProductStock(String branchId, String productId, int newStock) {
        return franchiseRepository.findAll()
                .flatMapIterable(franchise -> franchise.getBranches() != null ? franchise.getBranches() : new ArrayList<>())
                .filter(branch -> branch.getId().equals(branchId))
                .next()
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Branch not found with ID: " + branchId)))
                .flatMap(branch -> {
                    if (branch.getProducts() == null) {
                        return Mono.error(new EntityNotFoundException("No products found in branch with ID: " + branchId));
                    }

                    ProductEntity product = branch.getProducts().stream()
                            .filter(p -> p.getId().equals(productId))
                            .findFirst()
                            .orElse(null);

                    if (product == null) {
                        return Mono.error(new EntityNotFoundException("Product not found with ID: " + productId));
                    }

                    product.setStock(newStock);

                    return franchiseRepository.findAll()
                            .flatMap(franchise -> {
                                franchise.getBranches().stream()
                                        .filter(b -> b.getId().equals(branchId))
                                        .findFirst()
                                        .ifPresent(b -> b.setProducts(branch.getProducts()));
                                return franchiseRepository.save(franchise);
                            })
                            .next()
                            .map(savedFranchise -> {
                                ProductEntity savedProduct = savedFranchise.getBranches().stream()
                                        .filter(b -> b.getId().equals(branchId))
                                        .findFirst()
                                        .orElseThrow(() -> new EntityNotFoundException("Branch not found after save operation"))
                                        .getProducts().stream()
                                        .filter(p -> p.getId().equals(productId))
                                        .findFirst()
                                        .orElseThrow(() -> new EntityNotFoundException("Product not found after save operation"));
                                        
                                return productMapper.toDomain(savedProduct);
                            });
                });
    }

    @Override
    public Flux<Product> findProductsWithMaxStockByFranchise(String franchiseId) {
        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Franchise not found with ID: " + franchiseId)))
                .flatMapMany(franchise -> {
                    if (franchise.getBranches() == null || franchise.getBranches().isEmpty()) {
                        return Flux.empty();
                    }

                    Map<String, Product> maxStockProducts = new HashMap<>();
                    
                    for (BranchEntity branch : franchise.getBranches()) {
                        if (branch.getProducts() != null && !branch.getProducts().isEmpty()) {
                            ProductEntity maxStockProduct = branch.getProducts().stream()
                                    .max(Comparator.comparing(ProductEntity::getStock))
                                    .orElse(null);
                            
                            if (maxStockProduct != null) {
                                Product product = productMapper.toDomain(maxStockProduct);
                                maxStockProducts.put(branch.getId(), product);
                            }
                        }
                    }
                    
                    return Flux.fromIterable(maxStockProducts.values());
                });  
    }

    @Override
    public Mono<Product> updateProductName(String branchId, String productId, String newName) {
        return franchiseRepository.findAll()
                .flatMapIterable(franchise -> franchise.getBranches() != null ? franchise.getBranches() : new ArrayList<>())
                .filter(branch -> branch.getId().equals(branchId))
                .next()
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Branch not found with ID: " + branchId)))
                .flatMap(branch -> {
                    if (branch.getProducts() == null) {
                        return Mono.error(new EntityNotFoundException("No products found in branch with ID: " + branchId));
                    }

                    ProductEntity product = branch.getProducts().stream()
                            .filter(p -> p.getId().equals(productId))
                            .findFirst()
                            .orElse(null);

                    if (product == null) {
                        return Mono.error(new EntityNotFoundException("Product not found with ID: " + productId));
                    }

                    product.setName(newName);

                    return franchiseRepository.findAll()
                            .flatMap(franchise -> {
                                franchise.getBranches().stream()
                                        .filter(b -> b.getId().equals(branchId))
                                        .findFirst()
                                        .ifPresent(b -> b.setProducts(branch.getProducts()));
                                return franchiseRepository.save(franchise);
                            })
                            .next()
                            .map(savedFranchise -> {
                                ProductEntity savedProduct = savedFranchise.getBranches().stream()
                                        .filter(b -> b.getId().equals(branchId))
                                        .findFirst()
                                        .orElseThrow(() -> new EntityNotFoundException("Branch not found after save operation"))
                                        .getProducts().stream()
                                        .filter(p -> p.getId().equals(productId))
                                        .findFirst()
                                        .orElseThrow(() -> new EntityNotFoundException("Product not found after save operation"));
                                        
                                return productMapper.toDomain(savedProduct);
                            });
                });
    }
}
