package com.collegefeedback.controller;

import com.collegefeedback.model.Admin;
import com.collegefeedback.repository.AdminRepository;
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
public class AdminCreateAccountController {

    @Autowired
    private AdminRepository adminRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @GetMapping("/admin/create-account")
    public String showForm() {
        return "AdminCreateAccount"; // always use this template
    }

    @PostMapping("/admin/create-account")
    public String createAccount(
            @RequestParam String firstname,
            @RequestParam String lastname,
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            Model model
    ) {
        List<String> errors = new ArrayList<>();

        // Example validation
        if (password.length() < 8) {
            errors.add("Password must be at least 8 characters long.");
        }

        if (adminRepository.findByEmail(email) != null) {
            errors.add("Email already exists!");
        }

        if (!errors.isEmpty()) {
            model.addAttribute("errors", new ArrayList<>());
            return "AdminCreateAccount"; // same template for errors
        }

        Admin admin = new Admin();
        admin.setFirstname(firstname);
        admin.setLastname(lastname);
        admin.setUsername(username);
        admin.setEmail(email);
        admin.setPassword(encoder.encode(password));

        try {
            adminRepository.save(admin);
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            model.addAttribute("error", "Email already exists!");
            return "AdminCreateAccount";
        }

        adminRepository.save(admin);

        return "redirect:/admin/login";
    }
}
