package org.glgnn.user_demo.service;

import java.util.List;

import org.glgnn.user_demo.dto.ProductResponse;
import org.glgnn.user_demo.dto.UserCreateRequest;
import org.glgnn.user_demo.dto.UserResponse;
import org.glgnn.user_demo.dto.UserUpdateNameRequest;
import org.glgnn.user_demo.entity.User;

public interface UserService {


    UserResponse createUser(UserCreateRequest request);

    UserResponse getActiveUserById(Long userId);

    List<UserResponse> getAllActiveUsers();

    UserResponse updateUserName(Long userId, UserUpdateNameRequest request);

    void softDeleteUser(Long userId);

    void hardDeleteUser(Long userId);

    User getActiveUserEntity(Long userId);
}
