package com.quizapp.repository;

import com.quizapp.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    Optional<Certificate> findByQuizSessionId(Long quizSessionId);
    Optional<Certificate> findByCertificateNumber(String certificateNumber);
}
