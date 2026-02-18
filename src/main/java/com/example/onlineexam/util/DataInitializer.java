package com.example.onlineexam.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.onlineexam.entity.User;
import com.example.onlineexam.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Check if "admin" user exists
        if (userRepository.findByUsername("admin") == null) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123")); // Default password
            admin.setFullName("System Administrator");
            admin.setRole("ADMIN"); // Important!
            admin.setEmail("admin@exam.com");
            
            userRepository.save(admin);
            System.out.println("... Default Admin User Created ...");
        }
    }
}