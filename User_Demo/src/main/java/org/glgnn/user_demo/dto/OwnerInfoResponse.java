package org.glgnn.user_demo.dto;

public record OwnerInfoResponse(
        Long id,
        String name,
        String email
) {
}