package org.glgnn.user_demo.controller;

import org.glgnn.user_demo.dto.*;
import org.glgnn.user_demo.service.StoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping
    public ResponseEntity<StoreResponse> create(@RequestBody StoreCreateRequest request) {
        return new ResponseEntity<>(storeService.createStore(request), HttpStatus.CREATED);
    }

    @PostMapping("/{storeId}/product/{productId}")
    public ResponseEntity<Void> addProduct(
            @PathVariable Long storeId,
            @PathVariable Long productId) {
        storeService.addProductToStore(storeId, productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{storeId}/products")
    public ResponseEntity<List<StoreProductResponse>> getProducts(
            @PathVariable Long storeId) {
        return ResponseEntity.ok(storeService.getStoreProducts(storeId));
    }
}
