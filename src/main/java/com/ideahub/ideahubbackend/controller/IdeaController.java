package com.ideahub.ideahubbackend.controller;

import com.ideahub.ideahubbackend.model.Idea;
import com.ideahub.ideahubbackend.service.IdeaService;
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

    public IdeaController(IdeaService ideaService, JwtUtil jwtUtil, AiService aiService) {
        this.aiService = aiService;
        this.ideaService = ideaService;
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
}


