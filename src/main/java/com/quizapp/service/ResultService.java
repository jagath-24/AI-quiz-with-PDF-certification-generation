package com.quizapp.service;

import com.quizapp.entity.Question;
import com.quizapp.entity.QuizSession;
import com.quizapp.entity.UserAnswer;
import com.quizapp.repository.QuestionRepository;
import com.quizapp.repository.QuizSessionRepository;
import com.quizapp.repository.UserAnswerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ResultService {
    
    private final UserAnswerRepository userAnswerRepository;
    private final QuestionRepository questionRepository;
    private final QuizSessionRepository quizSessionRepository;

    public ResultService(UserAnswerRepository userAnswerRepository, QuestionRepository questionRepository, QuizSessionRepository quizSessionRepository) {
        this.userAnswerRepository = userAnswerRepository;
        this.questionRepository = questionRepository;
        this.quizSessionRepository = quizSessionRepository;
    }
    
    @Transactional
    public int calculateScore(Long quizSessionId) {
        QuizSession session = quizSessionRepository.findById(quizSessionId)
            .orElseThrow(() -> new IllegalArgumentException("Quiz session not found."));
        int total = session.getTotalQuestions() == null ? 0 : session.getTotalQuestions();
        if (total == 0) {
            session.setScore(0);
            quizSessionRepository.save(session);
            return 0;
        }

        List<UserAnswer> userAnswers = userAnswerRepository.findByQuizSessionId(quizSessionId);
        Set<Long> countedQuestions = new HashSet<>();
        int correctCount = 0;

        for (UserAnswer userAnswer : userAnswers) {
            if (!countedQuestions.add(userAnswer.getQuestionId())) {
                continue;
            }
            Question question = questionRepository.findById(userAnswer.getQuestionId()).orElse(null);
            if (question != null && question.getCorrectAnswer() != null
                    && question.getCorrectAnswer().equalsIgnoreCase(userAnswer.getSelectedAnswer())) {
                correctCount++;
            }
        }

        int score = Math.min(100, (correctCount * 100) / total);
        session.setScore(score);
        quizSessionRepository.save(session);
        return score;
    }
    
    public boolean isPassed(int score) {
        return score >= 60;
    }
}
