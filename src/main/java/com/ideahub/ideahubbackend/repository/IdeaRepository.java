package com.ideahub.ideahubbackend.repository;

import com.ideahub.ideahubbackend.model.Idea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdeaRepository extends JpaRepository<Idea, Long> {
}
