package org.glgnn.user_demo.dto;

public record PurchaseCreateRequest(
        Long userId,
        Long storeId,
        Long productId
) {}