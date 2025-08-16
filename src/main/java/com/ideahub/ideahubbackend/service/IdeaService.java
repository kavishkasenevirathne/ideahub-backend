package com.ideahub.ideahubbackend.service;

import com.ideahub.ideahubbackend.model.Idea;
import com.ideahub.ideahubbackend.repository.IdeaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IdeaService {
    private final IdeaRepository ideaRepository;

    public IdeaService(IdeaRepository ideaRepository) {
        this.ideaRepository = ideaRepository;
    }

    public Idea saveIdea(Idea idea) {
        return ideaRepository.save(idea);
    }

    public List<Idea> getAllIdeas() {
        return ideaRepository.findAll();
    }

    public Idea getIdeaById(Long id) {
        return ideaRepository.findById(id).orElse(null);
    }
}

