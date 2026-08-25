package com.fixflow.backend.dto;

import java.time.LocalDateTime;

import com.fixflow.backend.entity.IssuePriority;
import com.fixflow.backend.entity.IssueStatus;

public class IssueResponse {

    private Long id;
    private String title;
    private String description;
    private IssueStatus status;
    private IssuePriority priority;

    private Long createdById;
    private String createdByName;

    private Long assignedToId;
    private String assignedToName;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public IssueResponse() {
    }

    public IssueResponse(
            Long id,
            String title,
            String description,
            IssueStatus status,
            IssuePriority priority,
            Long createdById,
            String createdByName,
            Long assignedToId,
            String assignedToName,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.createdById = createdById;
        this.createdByName = createdByName;
        this.assignedToId = assignedToId;
        this.assignedToName = assignedToName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public IssueStatus getStatus() {
        return status;
    }

    public IssuePriority getPriority() {
        return priority;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public Long getAssignedToId() {
        return assignedToId;
    }

    public String getAssignedToName() {
        return assignedToName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}