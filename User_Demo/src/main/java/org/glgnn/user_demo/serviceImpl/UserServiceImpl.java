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

    @Override
    public UserResponse createUser(UserCreateRequest request) {

        if (userRepository.existsByEmail(request.email()))
            throw new RuntimeException("Email already exists");

        if (userRepository.existsByTc(request.tc()))
            throw new RuntimeException("TC already exists");

        User user = new User(request.name(), request.email(), request.tc());
        return map(userRepository.save(user));
    }

    @Override
    public UserResponse getActiveUserById(Long userId) {
        return map(getActiveUserEntity(userId));
    }

    @Override
    public List<UserResponse> getAllActiveUsers() {
        return userRepository.findAllByStatusTrue()
                .stream()
                .map(this::map)
                .toList();
    }

    @Override
    public UserResponse updateUserName(Long userId, UserUpdateNameRequest request) {
        User user = getActiveUserEntity(userId);
        user.changeName(request.name());
        return map(user);
    }

    @Override
    public void softDeleteUser(Long userId) {
        getActiveUserEntity(userId).deactivate();
    }

    @Override
    public void hardDeleteUser(Long userId) {
        if (!userRepository.existsById(userId))
            throw new RuntimeException("User not found");
        userRepository.deleteById(userId);
    }

    @Override
    public User getActiveUserEntity(Long userId) {
        return userRepository.findByIdAndStatusTrue(userId)
                .orElseThrow(() -> new RuntimeException("Active user not found"));
    }

    private UserResponse map(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getStatus()
        );
    }
}
