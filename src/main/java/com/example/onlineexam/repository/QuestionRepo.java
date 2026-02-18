package com.example.onlineexam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.onlineexam.entity.Question;

public interface QuestionRepo extends JpaRepository<Question, Long> {

}
