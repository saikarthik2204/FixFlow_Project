package com.fixflow.backend.controller;

import com.fixflow.backend.dto.IssueResponse;
import com.fixflow.backend.service.IssueService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agent")
public class AgentController {

    private final IssueService issueService;

    public AgentController(IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping("/issues")
    public ResponseEntity<List<IssueResponse>> getAssignedIssues(
            Authentication authentication
    ) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                issueService.getAssignedIssues(email)
        );
    }
}