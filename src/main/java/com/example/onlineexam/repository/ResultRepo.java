package com.example.onlineexam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.onlineexam.entity.Result;

public interface ResultRepo extends JpaRepository<Result, Long> {

	List<Result> findByUsername(String username);
}
