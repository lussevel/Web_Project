package com.collegefeedback.controller;

import com.collegefeedback.model.Admin;
import com.collegefeedback.repository.AdminRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class AdminEditAccountController {

    @Autowired
    private AdminRepository adminRepository;

    @GetMapping("/admin/edit-account")
    public String showEdit(
            @RequestParam int id,
            HttpSession session,
            Model model
    ) {
        if (session.getAttribute("admin_id") == null) {
            return "redirect:/admin/login";
        }

        Optional<Admin> opt = adminRepository.findById(id);
        if (opt.isEmpty()) {
            return "redirect:/admin/accounts";
        }

        model.addAttribute("admin", opt.get());
        return "AdminEditAccount";
    }

    @PostMapping("/admin/edit-account")
    public String update(
            @RequestParam int id,
            @RequestParam String firstname,
            @RequestParam String lastname,
            @RequestParam String username,
            @RequestParam String email,
            HttpSession session,
            Model model
    ) {

        if (session.getAttribute("admin_id") == null) {
            return "redirect:/admin/login";
        }

        List<String> errors = new ArrayList<>();

        if (firstname.isBlank() || lastname.isBlank()
                || username.isBlank() || email.isBlank()) {
            errors.add("All fields are required.");
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            errors.add("Invalid email format.");
        }

        if (adminRepository.existsByUsernameOrEmailAndIdNot(username, email, id)) {
            errors.add("Username or email already exists.");
        }

        Optional<Admin> opt = adminRepository.findById(id);
        if (opt.isEmpty()) {
            return "redirect:/admin/accounts";
        }

        Admin admin = opt.get();

        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            model.addAttribute("admin", admin);
            return "AdminEditAccount";
        }

        admin.setFirstname(firstname);
        admin.setLastname(lastname);
        admin.setUsername(username);
        admin.setEmail(email);

        adminRepository.save(admin);

        model.addAttribute("success", "Admin updated successfully.");
        model.addAttribute("admin", admin);

        return "AdminEditAccount";
    }
}
