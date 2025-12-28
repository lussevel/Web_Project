package com.collegefeedback.controller;

import com.collegefeedback.model.Admin;
import com.collegefeedback.repository.AdminRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AccountsController {

    @Autowired
    private AdminRepository adminRepository;

    @GetMapping("/admin/accounts")
    public String accounts(HttpSession session, Model model) {

        if (session.getAttribute("admin_id") == null) {
            return "redirect:/admin/login";
        }

        List<Admin> admins = adminRepository.findAllByOrderByIdAsc();

        model.addAttribute("admins", admins);
        model.addAttribute("success", session.getAttribute("success"));
        model.addAttribute("error", session.getAttribute("error"));

        session.removeAttribute("success");
        session.removeAttribute("error");

        return "Accounts";
    }
}
