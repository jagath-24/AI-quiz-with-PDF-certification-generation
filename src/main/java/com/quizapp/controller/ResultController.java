package com.quizapp.controller;

import com.quizapp.entity.QuizSession;
import com.quizapp.entity.User;
import com.quizapp.service.QuizService;
import com.quizapp.service.ResultService;
import com.quizapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ResultController {
    
    private final ResultService resultService;
    private final QuizService quizService;
    private final UserService userService;

    public ResultController(ResultService resultService, QuizService quizService, UserService userService) {
        this.resultService = resultService;
        this.quizService = quizService;
        this.userService = userService;
    }
    
    @GetMapping("/result/{sessionId}")
    public String showResult(@PathVariable Long sessionId, Model model) {
        int score = resultService.calculateScore(sessionId);
        QuizSession session = quizService.getQuizSession(sessionId);
        User user = userService.getUserById(session.getUserId());
        boolean passed = resultService.isPassed(score);
        
        model.addAttribute("quizSession", session);
        model.addAttribute("user", user);
        model.addAttribute("score", score);
        model.addAttribute("passed", passed);
        
        return "result";
    }
}
