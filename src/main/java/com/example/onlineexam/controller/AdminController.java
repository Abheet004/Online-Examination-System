package com.example.onlineexam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.onlineexam.entity.ExamSetting;
import com.example.onlineexam.entity.Notice;
import com.example.onlineexam.entity.Question;
import com.example.onlineexam.entity.Result;
import com.example.onlineexam.entity.User;
import com.example.onlineexam.repository.ExamSettingRepo;
import com.example.onlineexam.repository.NoticeRepo;
import com.example.onlineexam.repository.QuestionRepo;
import com.example.onlineexam.repository.ResultRepo;
import com.example.onlineexam.repository.UserRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
    private QuestionRepo questionRepository;
	@Autowired
	private ResultRepo resultRepo;
	
	@Autowired
    private UserRepository userRepository; 
	
	@Autowired
    private NoticeRepo noticeRepository;
	
	@Autowired
    private ExamSettingRepo examSettingRepository;

    @GetMapping("/settings")
    public String showSettings(Model model) {
        // Fetch existing setting or create default
        ExamSetting setting = examSettingRepository.findById(1L).orElse(new ExamSetting());
        model.addAttribute("setting", setting);
        return "admin_settings";
    }

    @PostMapping("/settings")
    public String saveSettings(@ModelAttribute("setting") ExamSetting setting) {
        setting.setId(1L); // Ensure we always update row 1
        examSettingRepository.save(setting);
        return "redirect:/admin/dashboard?settingsSaved";
    }

    @GetMapping("/add-notice")
    public String showNoticeForm(Model model) {
        model.addAttribute("notice", new Notice());
        return "add_notice"; // We will create this HTML file next
    }

    @PostMapping("/add-notice")
    public String addNotice(@ModelAttribute("notice") Notice notice) {
        noticeRepository.save(notice);
        return "redirect:/admin/dashboard?noticeSuccess";
    }// Add this if you haven't already

    @GetMapping("/students")
    public String viewStudents(Model model) {
        // Fetch only users who are "student"
        List<User> students = userRepository.findByRole("student");
        model.addAttribute("students", students);
        
        return "view_students"; // We will create this HTML file next
    }
	
	@GetMapping("/results")
    public String viewResults(Model model) {
        List<Result> results = resultRepo.findAll();
        model.addAttribute("results", results);
        return "view_results";
    }
	
	@GetMapping("/dashboard")
    public String adminDashboard() {
        return "admin_dashboard"; // Looks for admin_dashboard.html
    }
	
	@GetMapping("/add-question")
    public String showAddQuestionForm(Model model) {
        model.addAttribute("question", new Question());
        return "add_question"; // We will create this HTML file next
    }

    @PostMapping("/add-question")
    public String addQuestion(@ModelAttribute("question") Question question) {
        questionRepository.save(question);
        return "redirect:/admin/dashboard?success"; // Go back to dashboard after saving
    }
    @GetMapping("/questions")
    public String viewQuestions(Model model) {
        List<Question> questions = questionRepository.findAll();
        model.addAttribute("questions", questions);
        return "view_questions"; // We will create this file next
    }
    
    // Bonus: Let's add a DELETE feature right away
    @GetMapping("/delete-question")
    public String deleteQuestion(@RequestParam Long id) {
        questionRepository.deleteById(id);
        return "redirect:/admin/questions";
    }
}
