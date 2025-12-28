package com.collegefeedback.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ThankYouController {

    @GetMapping("/student/thank-you")
    public String thankYou() {
        return "ThankYou";
    }
}
