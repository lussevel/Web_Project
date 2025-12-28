package com.collegefeedback.controller;

import com.collegefeedback.model.Feedback;
import com.collegefeedback.repository.FeedbackRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class UpdateFeedbackController {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @PostMapping("/update-feedback")
    public ResponseEntity<Map<String, Object>> updateFeedback(
            @RequestParam int feedback_id,
            @RequestParam String status,
            @RequestParam(required = false) String reply,
            HttpSession session
    ) {

        Map<String, Object> response = new HashMap<>();

        // check admin session
        if (session.getAttribute("admin_id") == null) {
            response.put("success", false);
            response.put("message", "Unauthorized");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        if (feedback_id <= 0 || status.trim().isEmpty()) {
            response.put("success", false);
            response.put("message", "Missing required fields");
            return ResponseEntity.badRequest().body(response);
        }

        Optional<Feedback> opt = feedbackRepository.findById((long) feedback_id);
        if (opt.isEmpty()) {
            response.put("success", false);
            response.put("message", "Feedback not found");
            return ResponseEntity.badRequest().body(response);
        }

        Feedback feedback = opt.get();
        feedback.setStatus(status);
        feedbackRepository.save(feedback);

        // counts
        long total = feedbackRepository.count();
        long resolved = feedbackRepository.countByStatus("Resolved");
        long reviewed = feedbackRepository.countByStatus("Reviewed");

        response.put("success", true);
        response.put("message", "Feedback updated");

        Map<String, Object> counts = new HashMap<>();
        counts.put("total", total);
        counts.put("resolved", resolved);
        counts.put("reviewed", reviewed);

        response.put("counts", counts);

        Map<String, Object> updatedRow = new HashMap<>();
        updatedRow.put("id", feedback.getId());
        updatedRow.put("status", feedback.getStatus());
        updatedRow.put("votes", feedback.getVotes());

        response.put("updated_row", updatedRow);

        return ResponseEntity.ok(response);
    }
}
