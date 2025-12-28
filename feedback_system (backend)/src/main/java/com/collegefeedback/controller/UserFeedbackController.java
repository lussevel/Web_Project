package com.collegefeedback.controller;

import com.collegefeedback.model.Feedback;
import com.collegefeedback.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
public class UserFeedbackController {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @GetMapping("/user/feedback")
    public String showForm(Model model) {
        // We pass an empty object so the HTML form has something to hold the data
        model.addAttribute("feedback", new Feedback());
        return "UserFeedback";
    }

    @PostMapping("/user/feedback")
    public String submit(@ModelAttribute Feedback feedback) {
        // We only need to set the fields that the user CANNOT see
        feedback.setStatus("Pending");
        feedback.setVotes(0);
        feedback.setCreatedAt(LocalDateTime.now());

        // Save to database
        feedbackRepository.save(feedback);

        // Redirect to voting page after success
        return "redirect:/voting";
    }
}