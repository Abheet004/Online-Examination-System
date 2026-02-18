package com.example.onlineexam.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.onlineexam.repository.NoticeRepo;

@Controller
public class DashboardController {

	@Autowired
    private NoticeRepo noticeRepository; // Inject the repo

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        String username = principal.getName();
        model.addAttribute("username", username);
        
        // NEW: Fetch notices and add to model
        model.addAttribute("notices", noticeRepository.findAllByOrderByPostDateDesc());
        
        return "dashboard";
    }
}
