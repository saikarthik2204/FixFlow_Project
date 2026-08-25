package com.fixflow.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fixflow.backend.dto.CommentRequest;
import com.fixflow.backend.dto.CommentResponse;
import com.fixflow.backend.entity.Comment;
import com.fixflow.backend.entity.Issue;
import com.fixflow.backend.entity.User;
import com.fixflow.backend.repository.CommentRepository;
import com.fixflow.backend.repository.IssueRepository;
import com.fixflow.backend.repository.UserRepository;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final IssueRepository issueRepository;
    private final UserRepository userRepository;

    public CommentService(
            CommentRepository commentRepository,
            IssueRepository issueRepository,
            UserRepository userRepository
    ) {
        this.commentRepository = commentRepository;
        this.issueRepository = issueRepository;
        this.userRepository = userRepository;
    }

    public CommentResponse addComment(
            Long issueId,
            CommentRequest request,
            String currentUserEmail
    ) {

        User currentUser = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() ->
                        new RuntimeException("Issue not found"));

        checkIssueAccess(issue, currentUser);

        Comment comment = new Comment();

        comment.setContent(request.getContent());
        comment.setIssue(issue);
        comment.setAuthor(currentUser);

        Comment savedComment = commentRepository.save(comment);

        return toResponse(savedComment);
    }

    public List<CommentResponse> getComments(
            Long issueId,
            String currentUserEmail
    ) {

        User currentUser = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() ->
                        new RuntimeException("Issue not found"));

        checkIssueAccess(issue, currentUser);

        return commentRepository
                .findByIssueOrderByCreatedAtAsc(issue)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private void checkIssueAccess(
            Issue issue,
            User currentUser
    ) {

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
    }

    private CommentResponse toResponse(Comment comment) {

        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getAuthor().getId(),
                comment.getAuthor().getName(),
                comment.getCreatedAt()
        );
    }
}