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

    public Idea updateIdea(Long id, Idea updatedIdea) {
        return ideaRepository.findById(id).map(existingIdea -> {
            existingIdea.setTopic(updatedIdea.getTopic());
            existingIdea.setSummary(updatedIdea.getSummary());
            existingIdea.setExplanation(updatedIdea.getExplanation());
            existingIdea.setReferences(updatedIdea.getReferences());
            existingIdea.setProofs(updatedIdea.getProofs());
            existingIdea.setTags(updatedIdea.getTags());
            return ideaRepository.save(existingIdea);
        }).orElse(null);
    }

    public boolean deleteIdea(Long id) {
        if (ideaRepository.existsById(id)) {
            ideaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

