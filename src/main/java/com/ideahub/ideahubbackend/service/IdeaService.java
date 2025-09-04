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

    public Idea updateIdea(Long id, Idea updatedIdea, String username) {
        return ideaRepository.findById(id).map(existingIdea -> {
            if (!existingIdea.getCreatedBy().equals(username)) {
                throw new RuntimeException("Unauthorized to update this idea");
            }
            existingIdea.setTopic(updatedIdea.getTopic());
            existingIdea.setSummary(updatedIdea.getSummary());
            existingIdea.setExplanation(updatedIdea.getExplanation());
            existingIdea.setReferences(updatedIdea.getReferences());
            existingIdea.setProofs(updatedIdea.getProofs());
            existingIdea.setTags(updatedIdea.getTags());
            return ideaRepository.save(existingIdea);
        }).orElse(null);
    }

    public boolean deleteIdea(Long id, String username) {
        return ideaRepository.findById(id).map(existingIdea -> {
            if (!existingIdea.getCreatedBy().equals(username)) {
                throw new RuntimeException("You are not authorized to delete this idea");
            }
            ideaRepository.delete(existingIdea);
            return true;
        }).orElse(false);
    }
}

