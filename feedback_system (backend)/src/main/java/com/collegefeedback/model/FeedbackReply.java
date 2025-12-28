package com.collegefeedback.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "feedback_replies")
public class FeedbackReply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer feedbackId;
    private Integer adminId;

    @Column(columnDefinition = "TEXT")
    private String replyText;

    private LocalDateTime repliedAt = LocalDateTime.now();
}
