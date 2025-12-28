package com.collegefeedback.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "votes")
@Getter
@Setter
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "feedback_id")
    private int feedbackId;

    @Column(name = "session_id")
    private String sessionId;

    @Column(name = "voted_at")
    private LocalDateTime votedAt;
}
