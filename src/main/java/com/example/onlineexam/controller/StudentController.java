package com.example.onlineexam.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.onlineexam.entity.User;
import com.example.onlineexam.repository.UserRepository;

@Controller
@RequestMapping("/student")
public class StudentController {

	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder; // Inject password encoder

    // 1. Show the Edit Profile Form
    @GetMapping("/edit-profile")
    public String showEditProfileForm(Model model, Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username);
        model.addAttribute("user", user);
        return "edit_profile"; // We will create this HTML file next
    }

    // 2. Save the Updates
    @PostMapping("/update-profile")
    public String updateProfile(@ModelAttribute("user") User updatedUser, Principal principal) {
        // Fetch the existing user from DB to keep their ID and Role safe
        User existingUser = userRepository.findByUsername(principal.getName());

        // Update fields
        existingUser.setFullName(updatedUser.getFullName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPhoneNumber(updatedUser.getPhoneNumber()); // Optional

        // Only update password if the user actually typed a new one
        if (!updatedUser.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        // Save back to DB
        userRepository.save(existingUser);

        return "redirect:/student/profile?success"; // Redirect back to profile page
    }

    @GetMapping("/profile")
    public String viewProfile(Model model, Principal principal) {
        // 1. Get the username of the logged-in user
        String username = principal.getName();

        // 2. Fetch the full user details from the database
        User user = userRepository.findByUsername(username);

        // 3. Send the user object to the HTML page
        model.addAttribute("user", user);

        return "student_profile"; // We will create this HTML file next
    }
}
