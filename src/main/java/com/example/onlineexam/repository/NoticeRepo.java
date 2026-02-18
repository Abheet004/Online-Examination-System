package com.example.onlineexam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.onlineexam.entity.Notice;

public interface NoticeRepo extends JpaRepository<Notice, Long>{

	List<Notice> findAllByOrderByPostDateDesc();
}
