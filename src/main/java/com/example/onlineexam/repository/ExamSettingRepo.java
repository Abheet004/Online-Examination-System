package com.example.onlineexam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.onlineexam.entity.ExamSetting;

public interface ExamSettingRepo extends JpaRepository<ExamSetting, Long> {

}
