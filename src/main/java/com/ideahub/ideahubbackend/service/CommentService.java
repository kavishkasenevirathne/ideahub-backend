package com.ideahub.ideahubbackend.service;

import com.ideahub.ideahubbackend.model.Comment;
import com.ideahub.ideahubbackend.model.Idea;
import com.ideahub.ideahubbackend.repository.CommentRepository;
import com.ideahub.ideahubbackend.repository.IdeaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final IdeaRepository ideaRepository;

    public CommentService(CommentRepository commentRepository, IdeaRepository ideaRepository) {
        this.commentRepository = commentRepository;
        this.ideaRepository = ideaRepository;
    }

    //Add a comment to an idea
    public Comment addComment(Long ideaId, Comment comment) {
        Idea idea = ideaRepository.findById(ideaId)
                .orElseThrow(() -> new RuntimeException("Idea not found"));
        comment.setIdea(idea);
        return commentRepository.save(comment);
    }

    //Get all comments under an idea
    public List<Comment> getCommentsByIdea(Long ideaId) {
        return commentRepository.findByIdeaId(ideaId);
    }

    //(Optional) Delete a comment by ID
    public boolean deleteComment(Long commentId) {
        if (commentRepository.existsById(commentId)) {
            commentRepository.deleteById(commentId);
            return true;
        }
        return false;
    }

    //(Optional) Update a comment text or state
    public Comment updateComment(Long commentId, Comment updatedComment) {
        return commentRepository.findById(commentId).map(existing -> {
            existing.setText(updatedComment.getText());
            existing.setState(updatedComment.getState());
            return commentRepository.save(existing);
        }).orElse(null);
    }
}
