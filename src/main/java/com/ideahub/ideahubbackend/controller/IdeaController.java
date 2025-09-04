package com.ideahub.ideahubbackend.controller;

import com.ideahub.ideahubbackend.model.Idea;
import com.ideahub.ideahubbackend.service.IdeaService;
import com.ideahub.ideahubbackend.model.Comment;
import com.ideahub.ideahubbackend.service.CommentService;
import com.ideahub.ideahubbackend.service.AiService;
import com.ideahub.ideahubbackend.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ideas")
public class IdeaController {

    private final IdeaService ideaService;
    private final JwtUtil jwtUtil;
    private final AiService aiService;
    private final CommentService commentService;

    public IdeaController(IdeaService ideaService, JwtUtil jwtUtil, AiService aiService, CommentService commentService) {
        this.aiService = aiService;
        this.ideaService = ideaService;
        this.commentService = commentService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<Idea> createIdea(
            @RequestBody Idea idea) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        // Set createdBy field
        idea.setCreatedBy(username);

        // Generate AI conclusion before saving
        try {
            String aiConclusion = aiService.generateConclusion(
                    idea.getTopic(),
                    idea.getSummary(),
                    idea.getExplanation()
            );
            idea.setAiConclusion(aiConclusion);
        } catch (Exception e) {
            idea.setAiConclusion("AI analysis unavailable (quota exceeded).");
        }

        Idea savedIdea = ideaService.saveIdea(idea);
        return ResponseEntity.ok(savedIdea);
    }

    @GetMapping
    public List<Idea> getAllIdeas() {
        return ideaService.getAllIdeas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Idea> getIdea(@PathVariable Long id) {
        Idea idea = ideaService.getIdeaById(id);
        return idea != null ? ResponseEntity.ok(idea) : ResponseEntity.notFound().build();
    }

    //  Update Idea
    @PutMapping("/{id}")
    public ResponseEntity<Idea> updateIdea(@PathVariable Long id, @RequestBody Idea updatedIdea) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        try {
            Idea idea = ideaService.updateIdea(id, updatedIdea, username);
            return idea != null ? ResponseEntity.ok(idea) : ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).build(); // Forbidden
        }
    }

    //  Delete Idea
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIdea(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); // Logged-in user
        try {
            boolean deleted = ideaService.deleteIdea(id, username);
            return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).build(); // Forbidden
        }
    }

    //  Add Comment
    @PostMapping("/{id}/comments")
    public ResponseEntity<Comment> addComment(@PathVariable Long id, @RequestBody Comment comment) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        comment.setCreatedBy(auth.getName());
        Comment savedComment = commentService.addComment(id, comment);
        return ResponseEntity.ok(savedComment);
    }

    //  Get Comments for an Idea
    @GetMapping("/{id}/comments")
    public List<Comment> getComments(@PathVariable Long id) {
        return commentService.getCommentsByIdea(id);
    }
}




