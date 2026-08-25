package com.fixflow.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fixflow.backend.dto.IssueResponse;
import com.fixflow.backend.dto.UserResponse;
import com.fixflow.backend.entity.Role;
import com.fixflow.backend.service.AdminIssueService;
import com.fixflow.backend.service.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;
    private final AdminIssueService adminIssueService;

    public AdminController(
            AdminService adminService,
            AdminIssueService adminIssueService
    ) {
        this.adminService = adminService;
        this.adminIssueService = adminIssueService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getUsers(
            @RequestParam(required = false) Role role
    ) {

        if (role != null) {
            return ResponseEntity.ok(
                    adminService.getUsersByRole(role)
            );
        }

        return ResponseEntity.ok(
                adminService.getAllUsers()
        );
    }

    @PatchMapping("/users/{id}/role")
    public ResponseEntity<UserResponse> updateUserRole(
            @PathVariable Long id,
            @RequestParam Role role
    ) {

        return ResponseEntity.ok(
                adminService.updateUserRole(id, role)
        );
    }

    @PatchMapping("/issues/{issueId}/assign/{agentId}")
    public ResponseEntity<IssueResponse> assignIssue(
            @PathVariable Long issueId,
            @PathVariable Long agentId
    ) {

        return ResponseEntity.ok(
                adminIssueService.assignIssue(
                        issueId,
                        agentId
                )
        );
    }
}