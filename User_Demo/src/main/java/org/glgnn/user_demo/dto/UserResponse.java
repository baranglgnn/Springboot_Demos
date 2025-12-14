package org.glgnn.user_demo.dto;

public record UserResponse(
        Long id,
        String name,
        String email,
        Boolean status
) {
}
