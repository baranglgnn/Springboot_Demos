package org.glgnn.user_demo.service;

import org.glgnn.user_demo.dto.UserCreateRequest;
import org.glgnn.user_demo.dto.UserResponse;
import org.glgnn.user_demo.dto.UserUpdateNameRequest;
import org.glgnn.user_demo.entity.User;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserCreateRequest request);

    UserResponse getActiveUserById(Long userId);

    List<UserResponse> getAllActiveUsers();

    UserResponse updateUserName(Long userId, UserUpdateNameRequest request);

    void softDeleteUser(Long userId);

    void hardDeleteUser(Long userId);

    User getActiveUserEntity(Long userId);
}
