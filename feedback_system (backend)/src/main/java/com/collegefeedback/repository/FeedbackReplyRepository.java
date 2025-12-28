package com.collegefeedback.repository;

import com.collegefeedback.model.FeedbackReply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackReplyRepository
        extends JpaRepository<FeedbackReply, Integer> {
}
