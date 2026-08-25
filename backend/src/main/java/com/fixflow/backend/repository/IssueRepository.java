package com.fixflow.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fixflow.backend.entity.Issue;
import com.fixflow.backend.entity.IssueStatus;
import com.fixflow.backend.entity.User;

public interface IssueRepository extends JpaRepository<Issue, Long> {

    List<Issue> findByCreatedBy(User user);

    List<Issue> findByAssignedTo(User user);

    List<Issue> findByStatus(IssueStatus status);

    List<Issue> findByCreatedByOrAssignedTo(User createdBy, User assignedTo);
}