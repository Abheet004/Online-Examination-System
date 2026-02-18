package com.example.onlineexam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.onlineexam.entity.User;



public interface UserRepository extends JpaRepository<User, Long>{

	User findByUsername(String username);
	List<User> findByRole(String role);
}
