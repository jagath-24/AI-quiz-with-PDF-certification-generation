package com.quizapp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_answers")
public class UserAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quiz_session_id", nullable = false)
    private Long quizSessionId;

    @Column(name = "question_id", nullable = false)
    private Long questionId;

    @Column(name = "selected_answer", nullable = false, length = 1)
    private String selectedAnswer;

    public UserAnswer() {}

    public UserAnswer(Long id, Long quizSessionId, Long questionId, String selectedAnswer) {
        this.id = id;
        this.quizSessionId = quizSessionId;
        this.questionId = questionId;
        this.selectedAnswer = selectedAnswer;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getQuizSessionId() { return quizSessionId; }
    public void setQuizSessionId(Long quizSessionId) { this.quizSessionId = quizSessionId; }

    public Long getQuestionId() { return questionId; }
    public void setQuestionId(Long questionId) { this.questionId = questionId; }

    public String getSelectedAnswer() { return selectedAnswer; }
    public void setSelectedAnswer(String selectedAnswer) { this.selectedAnswer = selectedAnswer; }
}
