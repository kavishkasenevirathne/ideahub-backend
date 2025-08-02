package com.ideahub.ideahubbackend.repository;

import com.ideahub.ideahubbackend.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {}
