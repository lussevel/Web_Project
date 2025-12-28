package com.collegefeedback.controller;

import com.collegefeedback.model.Feedback;
import com.collegefeedback.repository.FeedbackRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class FeedbackListController {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @GetMapping("/admin/feedback")
    public String feedbackList(
            HttpSession session,
            Model model,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) LocalDate date
    ) {

        // Block access if not logged in
        if (session.getAttribute("admin_id") == null) {
            return "redirect:/admin/login";
        }

        List<Feedback> feedbacks =
                feedbackRepository.filterFeedback(category, status, date);

        model.addAttribute("feedbacks", feedbacks);
        model.addAttribute("category", category);
        model.addAttribute("status", status);
        model.addAttribute("date", date);

        return "FeedbackList";
    }
}
