package com.collegefeedback.controller;

import com.collegefeedback.model.Feedback;
import com.collegefeedback.repository.FeedbackRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminDashboardController {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @GetMapping("/admin/dashboard")
    public String dashboard(HttpSession session, Model model) {

        // Block access if not logged in
        if (session.getAttribute("admin_id") == null) {
            return "redirect:/admin/login";
        }

        String adminName = (String) session.getAttribute("admin_name");
        if (adminName == null) adminName = "Admin";

        long totalFeedback = feedbackRepository.count();
        long resolved = feedbackRepository.countByStatus("Resolved");
        long reviewed = feedbackRepository.countByStatus("Reviewed");

        List<Feedback> feedbackList =
                feedbackRepository.findAllByOrderByCreatedAtDesc();

        model.addAttribute("adminName", adminName);
        model.addAttribute("totalFeedback", totalFeedback);
        model.addAttribute("resolved", resolved);
        model.addAttribute("reviewed", reviewed);
        model.addAttribute("feedbackList", feedbackList);

        return "AdminDashboard";
    }
}
