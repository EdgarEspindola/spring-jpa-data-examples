package com.examples.spring_jpa.examples.product;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;

    public ProductController(ProductService productService, ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PutMapping("/{id}")
    public void updateProduct(@PathVariable(value = "id", required = true) UUID id,
            UpdateProductRequest updateRequest) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound(
                        "product with id [" + id + "] not found"));

        if (updateRequest.name() != null && !updateRequest.name().equals(product.getName())) {
            product.setName(updateRequest.name());
        }
        if (updateRequest.description() != null && !updateRequest.description().equals(product.getDescription())) {
            product.setDescription(updateRequest.description());
        }
        if (updateRequest.price() != null && !updateRequest.price().equals(product.getPrice())) {
            product.setPrice(updateRequest.price());
        }
        if (updateRequest.imageUrl() != null && !updateRequest.imageUrl().equals(product.getImageUrl())) {
            product.setImageUrl(updateRequest.imageUrl());
        }
        if (updateRequest.stockLevel() != null && !updateRequest.stockLevel().equals(product.getStockLevel())) {
            product.setStockLevel(updateRequest.stockLevel());
        }
        if (updateRequest.isPublished() != null && !updateRequest.isPublished().equals(product.getPublished())) {
            product.setPublished(updateRequest.isPublished());
        }

        productRepository.save(product);
    }
}