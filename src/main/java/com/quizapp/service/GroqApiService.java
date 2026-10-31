package com.quizapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quizapp.dto.GroqRequest;
import com.quizapp.dto.GroqResponse;
import com.quizapp.dto.QuestionDTO;
import com.quizapp.entity.Question;
import com.quizapp.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class GroqApiService {
    private final QuestionRepository questionRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${groq.api.key:}")
    private String apiKey;
    @Value("${groq.api.url:https://api.groq.com/openai/v1/chat/completions}")
    private String apiUrl;
    @Value("${groq.model:llama-3.3-70b-versatile}")
    private String model;

    public GroqApiService(QuestionRepository questionRepository, RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.questionRepository = questionRepository;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public List<Question> generateQuestions(String topic, String difficulty, int count) {
        if (topic == null || topic.isBlank()) {
            throw new IllegalArgumentException("Enter a topic before generating questions.");
        }
        int safeCount = Math.max(3, Math.min(count, 10));
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("AI generation is not configured. You can start with the built-in question bank or set GROQ_API_KEY.");
        }
        try {
            List<QuestionDTO> questionDTOs = parseResponse(callGroqApi(buildPrompt(topic.trim(), difficulty, safeCount)));
            if (questionDTOs.size() < safeCount) {
                throw new IllegalStateException("The AI returned fewer valid questions than requested.");
            }
            return saveQuestions(questionDTOs.subList(0, safeCount), topic.trim(), difficulty);
        } catch (Exception e) {
            throw new RuntimeException("Unable to generate AI questions: " + rootMessage(e), e);
        }
    }

    private String buildPrompt(String topic, String difficulty, int count) {
        return String.format("Generate %d multiple choice questions about \"%s\" with %s difficulty. "
                + "Return ONLY a valid JSON array with keys question, optionA, optionB, optionC, optionD, correctAnswer. "
                + "correctAnswer must be one of A, B, C, D.", count, topic, difficulty);
    }

    private String callGroqApi(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);
        GroqRequest request = new GroqRequest(List.of(new GroqRequest.Message("user", prompt)), model, 0.7, 2500);
        ResponseEntity<GroqResponse> response = restTemplate.exchange(apiUrl, HttpMethod.POST,
                new HttpEntity<>(request, headers), GroqResponse.class);
        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null
                || response.getBody().getChoices() == null || response.getBody().getChoices().isEmpty()
                || response.getBody().getChoices().get(0).getMessage() == null) {
            throw new IllegalStateException("Groq returned an empty or unsuccessful response.");
        }
        return response.getBody().getChoices().get(0).getMessage().getContent();
    }

    private List<QuestionDTO> parseResponse(String response) throws Exception {
        if (response == null || response.isBlank()) {
            throw new IllegalArgumentException("AI returned an empty response.");
        }
        String json = response.trim().replaceFirst("^```(?:json)?\\s*", "").replaceFirst("\\s*```$", "").trim();
        JsonNode root = objectMapper.readTree(json);
        if (!root.isArray()) {
            throw new IllegalArgumentException("AI response was not a JSON array.");
        }
        List<QuestionDTO> result = new ArrayList<>();
        for (JsonNode node : root) {
            String correct = text(node, "correctAnswer").toUpperCase();
            if (!List.of("A", "B", "C", "D").contains(correct)) continue;
            QuestionDTO dto = new QuestionDTO();
            dto.setQuestion(text(node, "question"));
            dto.setOptionA(text(node, "optionA"));
            dto.setOptionB(text(node, "optionB"));
            dto.setOptionC(text(node, "optionC"));
            dto.setOptionD(text(node, "optionD"));
            dto.setCorrectAnswer(correct);
            result.add(dto);
        }
        return result;
    }

    private String text(JsonNode node, String field) {
        JsonNode value = node.get(field);
        if (value == null || value.asText().isBlank()) throw new IllegalArgumentException("Missing field: " + field);
        return value.asText().trim();
    }

    private List<Question> saveQuestions(List<QuestionDTO> dtos, String topic, String difficulty) {
        List<Question> questions = new ArrayList<>();
        for (QuestionDTO dto : dtos) {
            Question question = new Question();
            question.setQuestionText(dto.getQuestion());
            question.setOptionA(dto.getOptionA());
            question.setOptionB(dto.getOptionB());
            question.setOptionC(dto.getOptionC());
            question.setOptionD(dto.getOptionD());
            question.setCorrectAnswer(dto.getCorrectAnswer());
            question.setTopic(topic);
            question.setDifficulty(difficulty);
            question.setCreatedAt(LocalDateTime.now());
            questions.add(questionRepository.save(question));
        }
        return questions;
    }

    private String rootMessage(Exception e) {
        Throwable current = e;
        while (current.getCause() != null) current = current.getCause();
        return current.getMessage() == null ? e.getClass().getSimpleName() : current.getMessage();
    }
}
