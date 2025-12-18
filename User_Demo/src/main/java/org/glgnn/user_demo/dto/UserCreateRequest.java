package org.glgnn.user_demo.dto;

public record UserCreateRequest(
        String name,
        String email,
        String tc
) {
}
