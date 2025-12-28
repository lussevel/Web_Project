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

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminAddAccountController {

    @Autowired
    private AdminRepository adminRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @GetMapping("/admin/add-account")
    public String showForm(HttpSession session) {
        if (session.getAttribute("admin_id") == null) {
            return "redirect:/admin/login";
        }
        return "AdminAddAccount";
    }

    @PostMapping("/admin/add-account")
    public String addAccount(
            HttpSession session,
            Model model,
            @RequestParam String firstname,
            @RequestParam String lastname,
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password
    ) {

        if (session.getAttribute("admin_id") == null) {
            return "redirect:/admin/login";
        }

        List<String> errors = new ArrayList<>();

        if (firstname.isBlank() || lastname.isBlank() ||
                username.isBlank() || email.isBlank() || password.isBlank()) {
            errors.add("All fields are required.");
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            errors.add("Invalid email format.");
        }

        if (password.length() < 8) {
            errors.add("Password must be at least 8 characters.");
        }

        if (adminRepository.existsByUsernameOrEmail(username, email)) {
            errors.add("Username or email already exists.");
        }

        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            return "AdminAddAccount";
        }

        Admin admin = new Admin();
        admin.setFirstname(firstname);
        admin.setLastname(lastname);
        admin.setUsername(username);
        admin.setEmail(email);
        admin.setPassword(encoder.encode(password));

        adminRepository.save(admin);

        session.setAttribute("success", "Admin added successfully.");
        return "redirect:/admin/accounts";
    }
}
