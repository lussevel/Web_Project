package com.collegefeedback.controller;

import com.collegefeedback.model.Feedback;
import com.collegefeedback.repository.FeedbackRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.UUID;

@Controller
public class VotingController {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @GetMapping("/voting") // <--- This is the GET request to SHOW the page
    public String showVotingPage(HttpSession session, Model model) {

        // 1. Generate Anonymous ID if missing
        if (session.getAttribute("anon_id") == null) {
            session.setAttribute("anon_id", UUID.randomUUID().toString());
        }

        // 2. Fetch the list of issues from the database
        // (Make sure you have this method in your FeedbackRepository!)
        List<Feedback> feedbacks = feedbackRepository.findAllOrderByVotesDescCreatedAtDesc();

        // 3. Put the list in the model so HTML can use it
        model.addAttribute("feedbacks", feedbacks);

        return "Voting"; // Matches Voting.html
    }
}