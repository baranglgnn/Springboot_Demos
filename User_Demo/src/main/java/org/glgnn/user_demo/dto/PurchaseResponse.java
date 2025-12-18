package org.glgnn.user_demo.dto;

import java.time.LocalDateTime;

public record PurchaseResponse(
        Long id,
        String productName,
        String serialNumber,
        String buyerName,
        String buyerEmail,
        LocalDateTime purchaseDate,
        Boolean active
) {}
