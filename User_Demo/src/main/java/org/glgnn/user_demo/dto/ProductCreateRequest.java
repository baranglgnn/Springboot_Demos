package org.glgnn.user_demo.dto;

public record ProductCreateRequest(
        String productName,
        String serialNumber,
        Double price
) {
}