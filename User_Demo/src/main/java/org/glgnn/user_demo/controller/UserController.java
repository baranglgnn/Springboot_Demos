package org.glgnn.user_demo.controller;
import org.glgnn.user_demo.dto.UserCreateRequest;
import org.glgnn.user_demo.dto.UserUpdateNameRequest;
import org.glgnn.user_demo.dto.UserResponse;
import org.glgnn.user_demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/add")
    public ResponseEntity<UserResponse> createUser(
            @RequestBody UserCreateRequest request
    ) {
        UserResponse response = userService.createUser(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // READ - by id
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(
            @PathVariable Long id
    ) {
        UserResponse response = userService.getUserById(id);
        return ResponseEntity.ok(response);
    }

    // READ - all
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // UPDATE - name only
    @PutMapping("/{id}/name")
    public ResponseEntity<UserResponse> updateUserName(
            @PathVariable Long id,
            @RequestBody UserUpdateNameRequest request
    ) {
        UserResponse response = userService.updateUserName(id, request);
        return ResponseEntity.ok(response);
    }

    // SOFT DELETE
    @DeleteMapping("/{id}/soft")
    public ResponseEntity<Void> softDeleteUser(
            @PathVariable Long id
    ) {
        userService.softDeleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // HARD DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> hardDeleteUser(
            @PathVariable Long id
    ) {
        userService.hardDeleteUser(id);
        return ResponseEntity.noContent().build();
    }
}