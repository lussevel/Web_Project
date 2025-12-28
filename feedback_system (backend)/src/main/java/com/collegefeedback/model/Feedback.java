package com.collegefeedback.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "feedback")
@Getter
@Setter   // Lombok generates setters (including setStatus)
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String category;
    private String title;
    private String description;
    private String status;
    private int votes;
    private boolean anonymous;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // getters & setters
    public Long getId() { return (long) id; }
    public String getCategory() { return category; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public int getVotes() { return votes; }

    public void setStatus(String status) { this.status = status; }
}
