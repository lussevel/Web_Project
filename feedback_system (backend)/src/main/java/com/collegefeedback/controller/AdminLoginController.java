package com.collegefeedback.controller;

import com.collegefeedback.model.Admin;
import com.collegefeedback.repository.AdminRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminLoginController {

    @Autowired
    private AdminRepository adminRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @GetMapping("/admin/login")
    public String showLoginForm() {
        return "AdminLogin";
    }

    @PostMapping("/admin/login")
    public String login(
            HttpSession session,
            @RequestParam String username,
            @RequestParam String password,
            Model model
    ) {
        Admin admin = adminRepository.findByUsername(username);

        if (admin == null || !encoder.matches(password, admin.getPassword())) {
            model.addAttribute("error", "Invalid username or password");
            return "AdminLogin";
        }

        session.setAttribute("admin_id", admin.getId());
        return "redirect:/admin/dashboard";
    }
}
