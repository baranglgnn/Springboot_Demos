package org.glgnn.user_demo.dto;

public record StoreProductResponse(
        Long productId,
        String productName,
        String serialNumber,
        Boolean sold
) {}