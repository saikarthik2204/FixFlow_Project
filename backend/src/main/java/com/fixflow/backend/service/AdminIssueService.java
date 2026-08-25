package com.fixflow.backend.service;

import org.springframework.stereotype.Service;

import com.fixflow.backend.dto.IssueResponse;
import com.fixflow.backend.entity.Issue;
import com.fixflow.backend.entity.Role;
import com.fixflow.backend.entity.User;
import com.fixflow.backend.repository.IssueRepository;
import com.fixflow.backend.repository.UserRepository;

@Service
public class AdminIssueService {

    private final IssueRepository issueRepository;
    private final UserRepository userRepository;

    public AdminIssueService(
            IssueRepository issueRepository,
            UserRepository userRepository
    ) {
        this.issueRepository = issueRepository;
        this.userRepository = userRepository;
    }

    public IssueResponse assignIssue(
            Long issueId,
            Long agentId
    ) {

        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() ->
                        new RuntimeException("Issue not found"));

        User agent = userRepository.findById(agentId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (agent.getRole() != Role.AGENT) {
            throw new RuntimeException(
                    "Selected user is not an AGENT"
            );
        }

        issue.setAssignedTo(agent);

        Issue savedIssue = issueRepository.save(issue);

        return toResponse(savedIssue);
    }

    private IssueResponse toResponse(Issue issue) {

        Long assignedToId = null;
        String assignedToName = null;

        if (issue.getAssignedTo() != null) {
            assignedToId = issue.getAssignedTo().getId();
            assignedToName = issue.getAssignedTo().getName();
        }

        return new IssueResponse(
                issue.getId(),
                issue.getTitle(),
                issue.getDescription(),
                issue.getStatus(),
                issue.getPriority(),
                issue.getCreatedBy().getId(),
                issue.getCreatedBy().getName(),
                assignedToId,
                assignedToName,
                issue.getCreatedAt(),
                issue.getUpdatedAt()
        );
    }
}