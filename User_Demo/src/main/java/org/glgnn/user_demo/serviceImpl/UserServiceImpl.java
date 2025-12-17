package org.glgnn.user_demo.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import org.glgnn.user_demo.dto.UserCreateRequest;
import org.glgnn.user_demo.dto.UserResponse;
import org.glgnn.user_demo.dto.UserUpdateNameRequest;
import org.glgnn.user_demo.entity.User;
import org.glgnn.user_demo.repository.UserRepository;
import org.glgnn.user_demo.service.UserService;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /* =========================
       CREATE
       ========================= */

    @Override
    public UserResponse createUser(UserCreateRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already exists");
        }

        if (userRepository.existsByTc(request.tc())) {
            throw new RuntimeException("TC already exists");
        }

        User user = new User(
                request.name(),
                request.email(),
                request.tc()
        );

        User savedUser = userRepository.save(user);
        return mapToUserResponse(savedUser);
    }

    /* =========================
       READ
       ========================= */

    @Override
    public UserResponse getActiveUserById(Long userId) {

        User user = userRepository.findByIdAndStatusTrue(userId)
                .orElseThrow(() -> new RuntimeException("Active user not found"));

        return mapToUserResponse(user);
    }

    @Override
    public List<UserResponse> getAllActiveUsers() {

        return userRepository.findAllByStatusTrue()
                .stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    /* =========================
       UPDATE
       ========================= */

    @Override
    public UserResponse updateUserName(Long userId, UserUpdateNameRequest request) {

        User user = userRepository.findByIdAndStatusTrue(userId)
                .orElseThrow(() -> new RuntimeException("Active user not found"));

        user.changeName(request.name());

        return mapToUserResponse(user);
    }

    @Override
    public void softDeleteUser(Long userId) {

        User user = userRepository.findByIdAndStatusTrue(userId)
                .orElseThrow(() -> new RuntimeException("Active user not found"));

        user.deactivate();
    }

    @Override
    public void hardDeleteUser(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }

        userRepository.deleteById(userId);
    }

    @Override
    public User getActiveUserEntity(Long userId) {

        return userRepository.findByIdAndStatusTrue(userId)
                .orElseThrow(() -> new RuntimeException("Active user not found"));
    }

    private UserResponse mapToUserResponse(User user) {

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getStatus()
        );
    }

}
