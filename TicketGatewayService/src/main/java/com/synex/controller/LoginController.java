package com.synex.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	@GetMapping("/dashboard")
    public String dashboard(Authentication auth,Model model) {
		model.addAttribute("userEmail", auth.getName());
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "dashboard-admin";
        } else if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_MANAGER"))) {
            return "dashboard-manager";
        } else {
            return "dashboard-user";
        }

    }
	
// @GetMapping("/login")
    @RequestMapping(value="/login")
    public String loginPage() {
        return "login";
    }
}
