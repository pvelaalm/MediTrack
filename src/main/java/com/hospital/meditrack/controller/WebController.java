package com.hospital.meditrack.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping({"/", "/index"})
    public String index(Authentication auth) {
        if (auth == null) {
            return "redirect:/login";
        }
        for (GrantedAuthority authority : auth.getAuthorities()) {
            String role = authority.getAuthority();
            if (role.equals("ROLE_SUPERVISOR")) return "redirect:/supervisor/dashboard";
            if (role.equals("ROLE_MEDICINA")) return "redirect:/medicina/dashboard";
            if (role.equals("ROLE_ENFERMERIA")) return "redirect:/enfermeria/dashboard";
        }
        return "redirect:/login";
    }
}
