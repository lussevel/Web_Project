package com.collegefeedback.controller;

import com.collegefeedback.DTO.FeedbackUpdateRequest;
import com.collegefeedback.model.Feedback;
import com.collegefeedback.model.FeedbackReply;
import com.collegefeedback.repository.FeedbackRepository;
import com.collegefeedback.repository.FeedbackReplyRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/feedback")
@RequiredArgsConstructor
public class AdminFeedbackController {

    private final FeedbackRepository feedbackRepo;
    private final FeedbackReplyRepository replyRepo;

    @PostMapping("/update")
    public ResponseEntity<?> updateFeedback(
            HttpSession session,
            @RequestBody FeedbackUpdateRequest req
    ) {

        Integer adminId = (Integer) session.getAttribute("admin_id");
        if (adminId == null) {
            return ResponseEntity.status(403).body(Map.of(
                    "success", false,
                    "message", "Unauthorized"
            ));
        }

        Feedback feedback = feedbackRepo.findById(req.getFeedbackId().longValue()).orElse(null);
        if (feedback == null) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Feedback not found"
            ));
        }

        // ✅ UPDATE STATUS
        feedback.setStatus(req.getStatus());
        feedbackRepo.save(feedback);

        // ✅ SAVE REPLY (OPTIONAL)
        if (req.getReply() != null && !req.getReply().isBlank()) {
            FeedbackReply reply = new FeedbackReply();
            reply.setFeedbackId(feedback.getId().intValue());
            reply.setAdminId(adminId);
            reply.setReplyText(req.getReply());
            replyRepo.save(reply);
        }

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Feedback updated",
                "counts", Map.of(
                        "total", feedbackRepo.count(),
                        "resolved", feedbackRepo.countByStatus("Resolved"),
                        "reviewed", feedbackRepo.countByStatus("Reviewed")
                ),
                "updated_row", Map.of(
                        "id", feedback.getId(),
                        "status", feedback.getStatus(),
                        "votes", feedback.getVotes()
                )
        ));
    }
}
