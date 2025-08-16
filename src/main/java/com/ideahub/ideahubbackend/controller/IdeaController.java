package com.ideahub.ideahubbackend.controller;

import com.ideahub.ideahubbackend.model.Idea;
import com.ideahub.ideahubbackend.service.IdeaService;
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

    public IdeaController(IdeaService ideaService, JwtUtil jwtUtil) {
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


