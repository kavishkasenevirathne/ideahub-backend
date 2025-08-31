package com.ideahub.ideahubbackend.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "comments")
public class Comment {

    public enum CommentState {
        AGREE, DISAGREE, NEUTRAL
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 2000)
    private String text;

    @Enumerated(EnumType.STRING)
    private CommentState state;

    private String createdBy; // username from JWT

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idea_id")
    @JsonBackReference
    private Idea idea;

    public Comment() {}

    public Comment(String text, CommentState state, String createdBy, Idea idea) {
        this.text = text;
        this.state = state;
        this.createdBy = createdBy;
        this.idea = idea;
    }

    // Getters and Setters
    public Long getId() { return id; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public CommentState getState() { return state; }
    public void setState(CommentState state) { this.state = state; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public Idea getIdea() { return idea; }
    public void setIdea(Idea idea) { this.idea = idea; }
}
