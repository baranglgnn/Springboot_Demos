package org.glgnn.user_demo.dto;

public record ProductResponse(
        Long id,
        String productName,
        String serialNumber,
        Double price,
        Boolean status,
        OwnerInfoResponse owner
) {
}
