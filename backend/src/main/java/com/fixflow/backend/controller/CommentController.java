package com.fixflow.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fixflow.backend.dto.CommentRequest;
import com.fixflow.backend.dto.CommentResponse;
import com.fixflow.backend.service.CommentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/issues/{issueId}/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentResponse> addComment(
            @PathVariable Long issueId,
            @Valid @RequestBody CommentRequest request,
            Authentication authentication
    ) {

        String email = authentication.getName();

        CommentResponse response =
                commentService.addComment(
                        issueId,
                        request,
                        email
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponse>> getComments(
            @PathVariable Long issueId,
            Authentication authentication
    ) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                commentService.getComments(
                        issueId,
                        email
                )
        );
    }
}