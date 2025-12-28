package com.collegefeedback.controller;

import com.collegefeedback.repository.AdminRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminDeleteAccountController {

    @Autowired
    private AdminRepository adminRepository;

    @GetMapping("/admin/delete-account")
    public String deleteAccount(
            @RequestParam int id,
            HttpSession session
    ) {

        // check login
        Integer currentAdminId = (Integer) session.getAttribute("admin_id");
        if (currentAdminId == null) {
            return "redirect:/admin/login";
        }

        // prevent deleting self
        if (id == currentAdminId) {
            session.setAttribute("error", "You cannot delete your own account.");
            return "redirect:/admin/accounts";
        }

        // delete admin
        adminRepository.deleteById(id);

        session.setAttribute("success", "Admin deleted successfully.");
        return "redirect:/admin/accounts";
    }
}
