package com.quizapp.controller;

import com.quizapp.entity.Question;
import com.quizapp.service.GroqApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/questions")
public class AIQuestionController {

    private static final Logger log = LoggerFactory.getLogger(AIQuestionController.class);

    private final GroqApiService groqApiService;

    public AIQuestionController(GroqApiService groqApiService) {
        this.groqApiService = groqApiService;
    }

    @PostMapping("/generate")
    public ResponseEntity<?> generateQuestions(
            @RequestParam String topic,
            @RequestParam(defaultValue = "medium") String difficulty,
            @RequestParam(defaultValue = "5") int count) {

        try {
            List<Question> questions = groqApiService.generateQuestions(topic, difficulty, count);
            return ResponseEntity.ok(questions);
        } catch (Exception e) {
            log.error("Failed to generate questions: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage() == null ? "AI generation failed." : e.getMessage()));
        }
    }
}
