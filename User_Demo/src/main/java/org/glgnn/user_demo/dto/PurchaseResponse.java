package org.glgnn.user_demo.dto;

import java.time.LocalDateTime;

public record PurchaseResponse(
        Long id,
        String buyerEmail,
        String productSerialNumber,
        LocalDateTime purchaseDate,
        LocalDateTime returnDate,
        Boolean active
) {}
