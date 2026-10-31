package com.quizapp.controller;

import com.quizapp.entity.QuizSession;
import com.quizapp.service.QuizService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final QuizService quizService;

    public HomeController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/generate")
    public String generateQuiz() {
        return "generateQuiz";
    }

    @GetMapping("/history")
    public String history(Model model) {
        List<QuizSession> leaderboard = quizService.getLeaderboard();
        model.addAttribute("leaderboard", leaderboard);
        return "history";
    }
}
