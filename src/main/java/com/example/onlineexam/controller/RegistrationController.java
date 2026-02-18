package com.example.onlineexam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.onlineexam.entity.User;
import com.example.onlineexam.repository.UserRepository;

@Controller
public class RegistrationController {

	@Autowired
	private UserRepository userRepository;
	
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//show reg form
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}
	
	// 2. Process the Form Submission
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user) {
        user.setRole("student");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/login?success"; 
    }
	
}
