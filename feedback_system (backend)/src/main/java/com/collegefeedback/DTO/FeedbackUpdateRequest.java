package com.collegefeedback.DTO;

import lombok.Data;

@Data
public class FeedbackUpdateRequest {
    private Integer feedbackId;
    private String status;
    private String reply;
}
