package com.quizapp.controller;

import com.quizapp.entity.Question;
import com.quizapp.entity.QuizSession;
import com.quizapp.entity.User;
import com.quizapp.repository.QuestionRepository;
import com.quizapp.service.QuizService;
import com.quizapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/quiz")
public class QuizController {
    
    private final QuizService quizService;
    private final UserService userService;
    private final QuestionRepository questionRepository;

    public QuizController(QuizService quizService, UserService userService, QuestionRepository questionRepository) {
        this.quizService = quizService;
        this.userService = userService;
        this.questionRepository = questionRepository;
    }
    
    @PostMapping("/start")
    public String startQuiz(@RequestParam String username,
                           @RequestParam String email,
                           @RequestParam String topic,
                           @RequestParam(defaultValue = "Medium") String difficulty,
                           @RequestParam(defaultValue = "5") int questionCount,
                           @RequestParam(required = false) String questionIds,
                           Model model) {
        if (username == null || username.isBlank() || email == null || email.isBlank() || topic == null || topic.isBlank()) {
            model.addAttribute("error", "Name, email, and topic are required to start a quiz.");
            return "generateQuiz";
        }
        int safeQuestionCount = Math.max(3, Math.min(questionCount, 10));
        User user = userService.createOrGetUser(username, email);

        List<Question> questions;
        if (questionIds != null && !questionIds.isBlank()) {
            // Use AI-generated questions directly by their IDs
            try {
                List<Long> ids = Arrays.stream(questionIds.split(","))
                        .map(String::trim)
                        .map(Long::parseLong)
                        .collect(Collectors.toList());
                questions = questionRepository.findAllById(ids);
            } catch (NumberFormatException ex) {
                model.addAttribute("error", "The generated question set is invalid. Please generate it again.");
                return "generateQuiz";
            }
        } else {
            // Fall back to DB questions filtered by topic + difficulty
            questions = quizService.getQuestionsByTopicAndDifficulty(topic, difficulty, safeQuestionCount);
            if (questions.isEmpty()) {
                questions = quizService.getQuestionsByTopic(topic, safeQuestionCount);
            }
        }
        
        if (questions.isEmpty()) {
            model.addAttribute("error", "No questions available for this topic. Please generate AI questions first.");
            return "generateQuiz";
        }
        
        QuizSession quizSession = quizService.createQuizSession(user.getId(), topic, questions.size());
        
        model.addAttribute("quizSession", quizSession);
        model.addAttribute("questions", questions);
        model.addAttribute("username", username);
        
        return "quiz";
    }
    
    @PostMapping("/submit")
    public String submitQuiz(@RequestParam Long sessionId,
                            @RequestParam Map<String, String> answers,
                            Model model) {
        
        answers.forEach((key, value) -> {
            if (key.startsWith("question_")) {
                Long questionId = Long.parseLong(key.substring(9));
                quizService.saveUserAnswer(sessionId, questionId, value);
            }
        });
        
        return "redirect:/result/" + sessionId;
    }
    
    @GetMapping("/leaderboard")
    @ResponseBody
    public List<QuizSession> getLeaderboard() {
        return quizService.getLeaderboard();
    }
}
