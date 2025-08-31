package com.ideahub.ideahubbackend.repository;

import com.ideahub.ideahubbackend.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByIdeaId(Long ideaId);
}
