package com.quizapp.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "certificates")
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "quiz_session_id", nullable = false)
    private Long quizSessionId;

    @Column(name = "certificate_number", nullable = false, unique = true)
    private String certificateNumber;

    @Column(name = "issue_date")
    private LocalDateTime issueDate = LocalDateTime.now();

    @Column(nullable = false)
    private Integer score;

    public Certificate() {}

    public Certificate(Long id, Long userId, Long quizSessionId, String certificateNumber, LocalDateTime issueDate, Integer score) {
        this.id = id;
        this.userId = userId;
        this.quizSessionId = quizSessionId;
        this.certificateNumber = certificateNumber;
        this.issueDate = issueDate;
        this.score = score;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getQuizSessionId() { return quizSessionId; }
    public void setQuizSessionId(Long quizSessionId) { this.quizSessionId = quizSessionId; }

    public String getCertificateNumber() { return certificateNumber; }
    public void setCertificateNumber(String certificateNumber) { this.certificateNumber = certificateNumber; }

    public LocalDateTime getIssueDate() { return issueDate; }
    public void setIssueDate(LocalDateTime issueDate) { this.issueDate = issueDate; }

    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }
}
