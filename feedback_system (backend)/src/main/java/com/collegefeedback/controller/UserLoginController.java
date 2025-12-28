package com.collegefeedback.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserLoginController {

    @GetMapping("/user")
    public String userLogin() {
        return "UserLogin";
    }
}
