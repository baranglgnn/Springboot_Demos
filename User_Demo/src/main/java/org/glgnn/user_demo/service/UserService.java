package org.glgnn.user_demo.service;

import java.util.List;

import org.glgnn.user_demo.dto.UserCreateRequest;
import org.glgnn.user_demo.dto.UserResponse;
import org.glgnn.user_demo.dto.UserUpdateNameRequest;

public interface UserService {

    UserResponse createUser(UserCreateRequest request);

    UserResponse getUserById(Long userId);

    List<UserResponse> getAllUsers();

    UserResponse updateUserName(Long userId, UserUpdateNameRequest request);

    void softDeleteUser(Long userId);

    void hardDeleteUser(Long userId);
}