package com.fixflow.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fixflow.backend.dto.UserResponse;
import com.fixflow.backend.entity.Role;
import com.fixflow.backend.entity.User;
import com.fixflow.backend.repository.UserRepository;

@Service
public class AdminService {

    private final UserRepository userRepository;

    public AdminService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<UserResponse> getUsersByRole(Role role) {

        return userRepository.findByRole(role)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public UserResponse updateUserRole(
            Long userId,
            Role role
    ) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        user.setRole(role);

        User updatedUser = userRepository.save(user);

        return toResponse(updatedUser);
    }

    private UserResponse toResponse(User user) {

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name()
        );
    }
}