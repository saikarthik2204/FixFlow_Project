package com.fixflow.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fixflow.backend.entity.Comment;
import com.fixflow.backend.entity.Issue;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByIssueOrderByCreatedAtAsc(Issue issue);
}