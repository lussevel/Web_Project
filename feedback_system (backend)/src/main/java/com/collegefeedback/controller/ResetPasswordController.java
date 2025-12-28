package com.collegefeedback.controller;

import com.collegefeedback.model.Admin;
import com.collegefeedback.repository.AdminRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class ResetPasswordController {

    @Autowired
    private AdminRepository adminRepository;

    // Show reset password form
    @GetMapping("/reset-password")
    public String resetPasswordForm(@RequestParam int id,
                                    HttpSession session,
                                    Model model) {
        // Block access if not logged in
        if (session.getAttribute("admin_id") == null) {
            return "redirect:/admin/login";
        }

        Admin admin = adminRepository.findById(id).orElse(null);
        if (admin == null) {
            model.addAttribute("error", "Admin not found");
            return "redirect:/admin/accounts";
        }

        model.addAttribute("admin", admin);
        return "ResetPassword"; // must match ResetPassword.html
    }

    // Handle password update
    @PostMapping("/reset-password")
    public String updatePassword(@RequestParam int id,
                                 @RequestParam String password,
                                 HttpSession session,
                                 Model model) {
        // Block access if not logged in
        if (session.getAttribute("admin_id") == null) {
            return "redirect:/admin/login";
        }

        Admin admin = adminRepository.findById(id).orElse(null);
        if (admin != null) {
            admin.setPassword(password); // later: hash this for security
            adminRepository.save(admin);
            model.addAttribute("success", "Password updated successfully");
        } else {
            model.addAttribute("error", "Admin not found");
        }

        return "redirect:/admin/accounts";
    }
}
