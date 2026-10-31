package com.quizapp.repository;

import com.quizapp.entity.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long> {
    List<UserAnswer> findByQuizSessionId(Long quizSessionId);
    Optional<UserAnswer> findByQuizSessionIdAndQuestionId(Long quizSessionId, Long questionId);
}
