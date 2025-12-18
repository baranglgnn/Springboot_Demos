package org.glgnn.user_demo.controller;

import org.glgnn.user_demo.dto.*;
import org.glgnn.user_demo.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/add")
    public ResponseEntity<ProductResponse> create(@RequestBody ProductCreateRequest request) {
        return new ResponseEntity<>(productService.createProduct(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAll() {
        return ResponseEntity.ok(productService.getAllActiveProducts());
    }

    @PutMapping("/{id}/price")
    public ResponseEntity<ProductResponse> updatePrice(
            @PathVariable Long id,
            @RequestBody ProductUpdatePriceRequest request) {
        return ResponseEntity.ok(productService.updateProductPrice(id, request));
    }

    @PutMapping("/{id}/assign")
    public ResponseEntity<ProductResponse> assign(
            @PathVariable Long id,
            @RequestBody ProductAssignRequest request) {
        return ResponseEntity.ok(productService.assignProductToUser(id, request));
    }

    @PutMapping("/{id}/unassign")
    public ResponseEntity<Void> unassign(@PathVariable Long id) {
        productService.unassignProduct(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/soft")
    public ResponseEntity<Void> softDelete(@PathVariable Long id) {
        productService.softDeleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> hardDelete(@PathVariable Long id) {
        productService.hardDeleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}

