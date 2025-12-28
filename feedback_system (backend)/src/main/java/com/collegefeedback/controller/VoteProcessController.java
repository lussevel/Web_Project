package com.collegefeedback.controller;

import com.collegefeedback.model.Feedback;
import com.collegefeedback.model.Vote;
import com.collegefeedback.repository.FeedbackRepository;
import com.collegefeedback.repository.VoteRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Controller
public class VoteProcessController {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private VoteRepository voteRepository;

    @PostMapping("/vote-process") // <--- Matches the action="/vote-process" in your HTML
    public String processVote(
            @RequestParam(name = "top_priority", required = false) Integer feedbackId,
            HttpSession session
    ) {
        // 1. Ensure Session ID
        if (session.getAttribute("anon_id") == null) {
            session.setAttribute("anon_id", UUID.randomUUID().toString());
        }
        String anonId = session.getAttribute("anon_id").toString();

        if (feedbackId == null) {
            return "redirect:/voting";
        }

        // 2. Check if this student has voted AT ALL
        boolean alreadyVoted = voteRepository.existsBySessionId(anonId);

        if (alreadyVoted) {
            return "redirect:/student/thank-you?error=already_voted";
        }

        // 3. Save the Vote
        Vote vote = new Vote();
        vote.setFeedbackId(feedbackId);
        vote.setSessionId(anonId);
        vote.setVotedAt(LocalDateTime.now());
        voteRepository.save(vote);

        // 4. Update the Count
        Optional<Feedback> opt = feedbackRepository.findById(feedbackId.longValue());
        if (opt.isPresent()) {
            Feedback f = opt.get();
            f.setVotes(f.getVotes() + 1);
            feedbackRepository.save(f);
        }

        return "redirect:/student/thank-you";
    }
}