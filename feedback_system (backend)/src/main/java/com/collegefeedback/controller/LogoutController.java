package com.collegefeedback.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogoutController {

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        // Destroy all session data (PHP: session_unset + session_destroy)
        session.invalidate();

        // Redirect to Admin login (same as PHP)
        return "redirect:/admin/login";
    }
}
