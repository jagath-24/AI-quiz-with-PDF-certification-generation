package com.quizapp.repository;

import com.quizapp.entity.QuizSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuizSessionRepository extends JpaRepository<QuizSession, Long> {
    List<QuizSession> findByUserIdOrderByDateTakenDesc(Long userId);
    List<QuizSession> findTop10ByOrderByScoreDesc();
}
