package org.glgnn.user_demo.controller;

import org.glgnn.user_demo.dto.PurchaseCreateRequest;
import org.glgnn.user_demo.dto.PurchaseResponse;
import org.glgnn.user_demo.service.PurchaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping
    public ResponseEntity<PurchaseResponse> purchaseProduct(
            @RequestBody PurchaseCreateRequest request
    ) {
        return new ResponseEntity<>(
                purchaseService.purchaseProduct(request),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/return/{id}")
    public ResponseEntity<Void> returnProduct(@PathVariable Long id) {
        purchaseService.returnProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PurchaseResponse>> getUserPurchases(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(
                purchaseService.getPurchasesByUser(userId)
        );
    }
}
