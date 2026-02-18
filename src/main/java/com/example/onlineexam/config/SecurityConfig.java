package com.example.onlineexam.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	
	@Autowired
    private CustomLoginSuccessHandler successHandler;
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // Allow anyone to access /register and static css/js files
                .requestMatchers("/","/register", "/css/**", "/js/**").permitAll()
                // All other requests require a login
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(login -> login
                // Allow anyone to see the login page
            	.loginPage("/login")
            	.permitAll()
                .successHandler(successHandler)
            )
            .logout(logout -> logout
            	.logoutUrl("/logout")             // Trigger logout when accessing /logout
                .logoutSuccessUrl("/")
                .permitAll()
            )
            // Disable CSRF for now to keep testing simple (prevents 403 errors on form submit)
            .csrf(csrf -> csrf.disable());

        return http.build();
    }
}