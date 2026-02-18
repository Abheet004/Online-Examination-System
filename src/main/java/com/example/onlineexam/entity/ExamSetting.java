package com.example.onlineexam.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "exam_settings")
public class ExamSetting {

	
	@Id
    private Long id = 1L; // We only need ONE row for settings

    private int examTimeInMinutes;

    // Default Constructor
    public ExamSetting() {
        this.examTimeInMinutes = 10; // Default 10 mins
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getExamTimeInMinutes() {
		return examTimeInMinutes;
	}

	public void setExamTimeInMinutes(int examTimeInMinutes) {
		this.examTimeInMinutes = examTimeInMinutes;
	}
    
    
}
