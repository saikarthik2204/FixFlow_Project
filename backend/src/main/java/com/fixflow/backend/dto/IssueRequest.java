package com.fixflow.backend.dto;

import com.fixflow.backend.entity.IssuePriority;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class IssueRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private IssuePriority priority;

    private Long assignedToId;

    public IssueRequest() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public IssuePriority getPriority() {
        return priority;
    }

    public void setPriority(IssuePriority priority) {
        this.priority = priority;
    }

    public Long getAssignedToId() {
        return assignedToId;
    }

    public void setAssignedToId(Long assignedToId) {
        this.assignedToId = assignedToId;
    }
}