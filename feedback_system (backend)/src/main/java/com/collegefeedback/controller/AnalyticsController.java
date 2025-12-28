package com.collegefeedback.controller;

import com.collegefeedback.repository.FeedbackRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class AnalyticsController {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @GetMapping("/admin/analytics")
    public String analytics(HttpSession session, Model model) {

        if (session.getAttribute("admin_id") == null) {
            return "redirect:/admin/login";
        }

        long totalFeedback = feedbackRepository.count();

        String[] categories = {"Facilities", "Academics", "Events", "Others"};
        Map<String, Integer> categoryPercentages = new HashMap<>();

        for (String cat : categories) {
            long count = feedbackRepository.countByCategory(cat);

            int percent = (totalFeedback > 0)
                    ? (int) Math.round((double) count / totalFeedback * 100)
                    : 0;

            categoryPercentages.put(cat, percent);
        }

        Long totalVotes = feedbackRepository.sumVotes();

        String mostVotedTitle =
                feedbackRepository.findMostVotedTitles()
                        .stream()
                        .findFirst()
                        .orElse("No votes yet");

        long resolved = feedbackRepository.countByStatus("Resolved");
        long pending = feedbackRepository.countByStatus("Pending");

        int resolvedPercentage =
                (resolved + pending > 0)
                        ? (int) Math.round((double) resolved / (resolved + pending) * 100)
                        : 0;

        model.addAttribute("totalFeedback", totalFeedback);
        model.addAttribute("categoryPercentages", categoryPercentages);
        model.addAttribute("totalVotes", totalVotes != null ? totalVotes : 0);
        model.addAttribute("mostVotedTitle", mostVotedTitle);
        model.addAttribute("resolvedPercentage", resolvedPercentage);

        return "Analytics";
    }
}
