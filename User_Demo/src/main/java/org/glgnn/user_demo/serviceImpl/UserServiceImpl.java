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
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserResponse createUser(UserCreateRequest request) {

        User user = new User(
                request.name(),
                request.email(),
                request.tc()
        );

        User savedUser = userRepository.save(user);

        return mapToUserResponse(savedUser);
    }

    @Override
    @Transactional
    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

            return mapToUserResponse(user);

    }

    @Override
    @Transactional
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserResponse updateUserName(Long userId, UserUpdateNameRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.changeName(request.name());

        User updatedUser = userRepository.save(user);

        return mapToUserResponse(updatedUser);
    }

    @Override
    @Transactional
    public void softDeleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.deactivate();

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void hardDeleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }

        userRepository.deleteById(userId);
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