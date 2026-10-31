package com.quizapp.service;

import com.quizapp.entity.Question;
import com.quizapp.entity.QuizSession;
import com.quizapp.entity.UserAnswer;
import com.quizapp.repository.QuestionRepository;
import com.quizapp.repository.QuizSessionRepository;
import com.quizapp.repository.UserAnswerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuizService {
    private final QuestionRepository questionRepository;
    private final QuizSessionRepository quizSessionRepository;
    private final UserAnswerRepository userAnswerRepository;

    public QuizService(QuestionRepository questionRepository, QuizSessionRepository quizSessionRepository,
                       UserAnswerRepository userAnswerRepository) {
        this.questionRepository = questionRepository;
        this.quizSessionRepository = quizSessionRepository;
        this.userAnswerRepository = userAnswerRepository;
    }

    public List<Question> getQuestionsByTopic(String topic, int limit) {
        int safeLimit = Math.max(1, Math.min(limit, 20));
        return questionRepository.findByTopicIgnoreCase(topic == null ? "" : topic.trim())
                .stream().limit(safeLimit).toList();
    }

    public List<Question> getQuestionsByTopicAndDifficulty(String topic, String difficulty, int limit) {
        int safeLimit = Math.max(1, Math.min(limit, 20));
        return questionRepository.findByTopicIgnoreCaseAndDifficultyIgnoreCase(
                        topic == null ? "" : topic.trim(), difficulty == null ? "Medium" : difficulty.trim())
                .stream().limit(safeLimit).toList();
    }

    @Transactional
    public QuizSession createQuizSession(Long userId, String topic, int totalQuestions) {
        if (userId == null || topic == null || topic.isBlank() || totalQuestions < 1) {
            throw new IllegalArgumentException("A valid user, topic, and question set are required.");
        }
        QuizSession session = new QuizSession();
        session.setUserId(userId);
        session.setTopic(topic.trim());
        session.setTotalQuestions(totalQuestions);
        session.setScore(0);
        session.setDateTaken(LocalDateTime.now());
        return quizSessionRepository.save(session);
    }

    @Transactional
    public void saveUserAnswer(Long quizSessionId, Long questionId, String selectedAnswer) {
        if (quizSessionId == null || questionId == null || selectedAnswer == null || selectedAnswer.isBlank()) {
            return;
        }
        String normalized = selectedAnswer.trim().toUpperCase();
        if (!List.of("A", "B", "C", "D").contains(normalized)) {
            return;
        }
        UserAnswer answer = userAnswerRepository.findByQuizSessionIdAndQuestionId(quizSessionId, questionId)
                .orElseGet(UserAnswer::new);
        answer.setQuizSessionId(quizSessionId);
        answer.setQuestionId(questionId);
        answer.setSelectedAnswer(normalized);
        userAnswerRepository.save(answer);
    }

    public QuizSession getQuizSession(Long sessionId) {
        return quizSessionRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("Quiz session not found."));
    }

    public List<QuizSession> getUserHistory(Long userId) {
        return quizSessionRepository.findByUserIdOrderByDateTakenDesc(userId);
    }

    public List<QuizSession> getLeaderboard() {
        return quizSessionRepository.findTop10ByOrderByScoreDesc();
    }
}
