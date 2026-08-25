package com.fixflow.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fixflow.backend.dto.IssueRequest;
import com.fixflow.backend.dto.IssueResponse;
import com.fixflow.backend.entity.Issue;
import com.fixflow.backend.entity.IssueStatus;
import com.fixflow.backend.entity.User;
import com.fixflow.backend.repository.IssueRepository;
import com.fixflow.backend.repository.UserRepository;

@Service
public class IssueService {

    private final IssueRepository issueRepository;
    private final UserRepository userRepository;

    public IssueService(
            IssueRepository issueRepository,
            UserRepository userRepository
    ) {
        this.issueRepository = issueRepository;
        this.userRepository = userRepository;
    }

    public IssueResponse createIssue(
            IssueRequest request,
            String currentUserEmail
    ) {

        User creator = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Issue issue = new Issue();

        issue.setTitle(request.getTitle());
        issue.setDescription(request.getDescription());
        issue.setPriority(request.getPriority());
        issue.setStatus(IssueStatus.OPEN);
        issue.setCreatedBy(creator);

        if (request.getAssignedToId() != null) {

            User assignee = userRepository
                    .findById(request.getAssignedToId())
                    .orElseThrow(() ->
                            new RuntimeException("Assigned user not found"));

            issue.setAssignedTo(assignee);
        }

        Issue savedIssue = issueRepository.save(issue);

        return toResponse(savedIssue);
    }

    public List<IssueResponse> getMyIssues(String currentUserEmail) {

        User user = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return issueRepository
                .findByCreatedByOrAssignedTo(user, user)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    /*
     * Get all issues assigned to the current agent.
     */
    public List<IssueResponse> getAssignedIssues(
            String currentUserEmail
    ) {

        User agent = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return issueRepository
                .findByAssignedTo(agent)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public IssueResponse getIssueById(
            Long id,
            String currentUserEmail
    ) {

        User currentUser = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Issue issue = issueRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Issue not found"));

        boolean isCreator =
                issue.getCreatedBy().getId()
                        .equals(currentUser.getId());

        boolean isAssignee =
                issue.getAssignedTo() != null &&
                issue.getAssignedTo().getId()
                        .equals(currentUser.getId());

        if (!isCreator && !isAssignee) {
            throw new RuntimeException(
                    "You do not have access to this issue"
            );
        }

        return toResponse(issue);
    }

    public IssueResponse updateStatus(
            Long id,
            IssueStatus status,
            String currentUserEmail
    ) {

        User currentUser = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Issue issue = issueRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Issue not found"));

        boolean isCreator =
                issue.getCreatedBy().getId()
                        .equals(currentUser.getId());

        boolean isAssignee =
                issue.getAssignedTo() != null &&
                issue.getAssignedTo().getId()
                        .equals(currentUser.getId());

        if (!isCreator && !isAssignee) {
            throw new RuntimeException(
                    "You do not have access to this issue"
            );
        }

        issue.setStatus(status);

        return toResponse(issueRepository.save(issue));
    }

    public void deleteIssue(
            Long id,
            String currentUserEmail
    ) {

        User currentUser = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Issue issue = issueRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Issue not found"));

        if (!issue.getCreatedBy().getId()
                .equals(currentUser.getId())) {

            throw new RuntimeException(
                    "Only the issue creator can delete this issue"
            );
        }

        issueRepository.delete(issue);
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