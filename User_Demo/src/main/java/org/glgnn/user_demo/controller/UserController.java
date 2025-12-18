package org.glgnn.user_demo.controller;

import org.glgnn.user_demo.dto.ProductResponse;
import org.glgnn.user_demo.dto.UserCreateRequest;
import org.glgnn.user_demo.dto.UserUpdateNameRequest;
import org.glgnn.user_demo.dto.UserResponse;
import org.glgnn.user_demo.service.ProductService;
import org.glgnn.user_demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final ProductService productService;

    public UserController(UserService userService,
                          ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserCreateRequest request) {
        return new ResponseEntity<>(userService.createUser(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getActiveUserById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll() {
        return ResponseEntity.ok(userService.getAllActiveUsers());
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<List<ProductResponse>> getUserProducts(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getActiveProductsByUserId(id));
    }

    @PutMapping("/{id}/name")
    public ResponseEntity<UserResponse> updateName(
            @PathVariable Long id,
            @RequestBody UserUpdateNameRequest request) {
        return ResponseEntity.ok(userService.updateUserName(id, request));
    }

    @DeleteMapping("/{id}/soft")
    public ResponseEntity<Void> softDelete(@PathVariable Long id) {
        userService.softDeleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> hardDelete(@PathVariable Long id) {
        userService.hardDeleteUser(id);
        return ResponseEntity.noContent().build();
    }
}

