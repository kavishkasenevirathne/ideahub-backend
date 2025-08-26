package com.ideahub.ideahubbackend.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "ideas")
public class Idea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String topic;

    @Column(length = 500)
    private String summary;

    @Column(length = 5000)
    private String explanation;

    @Column(name = "idea_references", length = 2000)
    private String references;

    @Column(length = 2000)
    private String proofs;

    @ElementCollection
    @CollectionTable(name = "idea_tags", joinColumns = @JoinColumn(name = "idea_id"))
    @Column(name = "tag")
    private List<String> tags;

    private String createdBy; // username from JWT

    @Column(length = 2000)
    private String aiConclusion; // Optional field for AI-generated conclusion

    public Idea() {}

    public Idea(String topic, String summary, String explanation, String references, String proofs, List<String> tags) {
        this.topic = topic;
        this.summary = summary;
        this.explanation = explanation;
        this.references = references;
        this.proofs = proofs;
        this.tags = tags;
        //this.createdBy = createdBy;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }

    public String getReferences() { return references; }
    public void setReferences(String references) { this.references = references; }

    public String getProofs() { return proofs; }
    public void setProofs(String proofs) { this.proofs = proofs; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public String getAiConclusion() { return aiConclusion; }   // <-- NEW GETTER
    public void setAiConclusion(String aiConclusion) { this.aiConclusion = aiConclusion; } // <-- NEW SETTER
}
