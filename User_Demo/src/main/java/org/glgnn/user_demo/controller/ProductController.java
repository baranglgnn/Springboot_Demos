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

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @RequestBody ProductCreateRequest request
    ) {
        return new ResponseEntity<>(
                productService.createProduct(request),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllActiveProducts());
    }

    @PutMapping("/price/{id}")
    public ResponseEntity<ProductResponse> updatePrice(
            @PathVariable Long id,
            @RequestBody ProductUpdatePriceRequest request
    ) {
        return ResponseEntity.ok(productService.updateProductPrice(id, request));
    }

    @PutMapping("/assign/{id}")
    public ResponseEntity<ProductResponse> assignProduct(
            @PathVariable Long id,
            @RequestBody ProductAssignRequest request
    ) {
        return ResponseEntity.ok(productService.assignProductToUser(id, request));
    }

    @PutMapping("/unassign/{id}")
    public ResponseEntity<Void> unassignProduct(@PathVariable Long id) {
        productService.unassignProduct(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<Void> softDelete(@PathVariable Long id) {
        productService.softDeleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> hardDelete(@PathVariable Long id) {
        productService.hardDeleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
