package com.example.onlineexam.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.onlineexam.entity.ExamSetting;
import com.example.onlineexam.entity.Question;
import com.example.onlineexam.entity.Result;
import com.example.onlineexam.repository.ExamSettingRepo;
import com.example.onlineexam.repository.QuestionRepo;
import com.example.onlineexam.repository.ResultRepo;
import com.example.onlineexam.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class ExamController {

	@Autowired
    private QuestionRepo questionRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ResultRepo resultRepo;
    
    @Autowired
    private ExamSettingRepo examSettingRepository;

    // 1. Start the Exam
    @GetMapping("/exam/start")
    public String startExam(Model model, Principal principal, HttpSession session) {
        // Fetch all questions (In a real app, you might want to pick just 10 random ones)
    	ExamSetting setting = examSettingRepository.findById(1L).orElse(new ExamSetting());
        int durationMins = setting.getExamTimeInMinutes();

        // 2. Manage Timer in Session (Prevents refresh reset)
        LocalTime endTime = (LocalTime) session.getAttribute("examEndTime");
        
        if (endTime == null) {
            // First time starting? Set the End Time
            endTime = LocalTime.now().plusMinutes(durationMins);
            session.setAttribute("examEndTime", endTime);
        }

        // 3. Calculate seconds remaining for the View
        long secondsLeft = java.time.Duration.between(LocalTime.now(), endTime).getSeconds();
        
        if (secondsLeft < 0) {
            return "redirect:/exam/submit"; // Time already up!
        }

        model.addAttribute("secondsLeft", secondsLeft);
    	
    	List<Question> questions = questionRepository.findAll();
        
        model.addAttribute("questions", questions);
        model.addAttribute("username", principal.getName());
        return "exam_page"; // We will create this HTML file next
    }

    // 2. Submit Answers & Calculate Score
    @PostMapping("/exam/submit")
    public String submitExam(@RequestParam Map<String, String> allParams, Model model, Principal principal) {
        int score = 0;
        int totalQuestions = 0;

        // "allParams" contains every input from the form. 
        // The keys will be like "q_1", "q_2" (where numbers are Question IDs)
        
        for (Map.Entry<String, String> entry : allParams.entrySet()) {
            if (entry.getKey().startsWith("q_")) {
                totalQuestions++;
                
                // Extract Question ID from the key name "q_12" -> 12
                Long questionId = Long.parseLong(entry.getKey().substring(2));
                String selectedAnswer = entry.getValue(); // "A", "B", etc.

                // Check Database for correct answer
                Question q = questionRepository.findById(questionId).orElse(null);
                if (q != null && q.getAnswer().equals(selectedAnswer)) {
                    score++;
                }
            }
        }
        
        Result result = new Result();
        result.setUsername(principal.getName());
        result.setTotalQuestions(totalQuestions);
        result.setCorrectAnswers(score);
        result.setExamDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        
        resultRepo.save(result);

        // Pass results to the view
        model.addAttribute("result", result);
        model.addAttribute("score", score);
        model.addAttribute("total", totalQuestions);
        model.addAttribute("username", principal.getName());
        
        return "result_page"; // We will create this HTML file too
    }
}
