package com.fixflow.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fixflow.backend.dto.IssueRequest;
import com.fixflow.backend.dto.IssueResponse;
import com.fixflow.backend.entity.IssueStatus;
import com.fixflow.backend.service.IssueService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @PostMapping
    public ResponseEntity<IssueResponse> createIssue(
            @Valid @RequestBody IssueRequest request,
            Authentication authentication
    ) {

        String email = authentication.getName();

        IssueResponse response =
                issueService.createIssue(request, email);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<IssueResponse>> getMyIssues(
            Authentication authentication
    ) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                issueService.getMyIssues(email)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<IssueResponse> getIssue(
            @PathVariable Long id,
            Authentication authentication
    ) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                issueService.getIssueById(id, email)
        );
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<IssueResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam IssueStatus status,
            Authentication authentication
    ) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                issueService.updateStatus(
                        id,
                        status,
                        email
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIssue(
            @PathVariable Long id,
            Authentication authentication
    ) {

        String email = authentication.getName();

        issueService.deleteIssue(id, email);

        return ResponseEntity.noContent().build();
    }
}