package com.collegefeedback.controller;

import com.collegefeedback.model.Admin;
import com.collegefeedback.repository.AdminRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminLoginActionController {

    @Autowired
    private AdminRepository adminRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/admin/login-action")
    public String loginAction(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session
    ) {

        // Check empty
        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            session.setAttribute("login_error", "Please fill out all fields.");
            return "redirect:/admin/login";
        }

        // Check username
        Admin admin = adminRepository.findByUsername(username);
        if (admin == null) {
            session.setAttribute("login_error", "Username does not exist.");
            return "redirect:/admin/login";
        }

        // Verify password
        if (!passwordEncoder.matches(password, admin.getPassword())) {
            session.setAttribute("login_error", "Incorrect password.");
            return "redirect:/admin/login";
        }

        // SUCCESS LOGIN — Create session
        session.setAttribute("admin_id", admin.getId());
        session.setAttribute("admin_username", admin.getUsername());
        session.setAttribute(
                "admin_name",
                admin.getFirstname() + " " + admin.getLastname()
        );

        return "redirect:/admin/dashboard";
    }
}
