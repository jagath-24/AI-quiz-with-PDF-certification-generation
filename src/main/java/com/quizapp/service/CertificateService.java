package com.quizapp.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.quizapp.entity.Certificate;
import com.quizapp.entity.QuizSession;
import com.quizapp.entity.User;
import com.quizapp.repository.CertificateRepository;
import com.quizapp.repository.QuizSessionRepository;
import com.quizapp.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class CertificateService {
    private final CertificateRepository certificateRepository;
    private final QuizSessionRepository quizSessionRepository;
    private final UserRepository userRepository;

    public CertificateService(CertificateRepository certificateRepository, QuizSessionRepository quizSessionRepository,
                              UserRepository userRepository) {
        this.certificateRepository = certificateRepository;
        this.quizSessionRepository = quizSessionRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Certificate generateCertificate(Long quizSessionId) {
        QuizSession session = quizSessionRepository.findById(quizSessionId)
                .orElseThrow(() -> new IllegalArgumentException("Quiz session not found."));
        if (session.getScore() == null || session.getScore() < 60) {
            throw new IllegalStateException("A score of at least 60% is required to generate a certificate.");
        }
        return certificateRepository.findByQuizSessionId(quizSessionId).orElseGet(() -> {
            Certificate certificate = new Certificate();
            certificate.setUserId(session.getUserId());
            certificate.setQuizSessionId(quizSessionId);
            certificate.setCertificateNumber("CERT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
            certificate.setIssueDate(LocalDateTime.now());
            certificate.setScore(session.getScore());
            return certificateRepository.save(certificate);
        });
    }

    public byte[] generatePDF(Long quizSessionId) {
        try {
            QuizSession session = quizSessionRepository.findById(quizSessionId)
                    .orElseThrow(() -> new IllegalArgumentException("Quiz session not found."));
            User user = userRepository.findById(session.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found."));
            Certificate certificate = certificateRepository.findByQuizSessionId(quizSessionId)
                    .orElseThrow(() -> new IllegalArgumentException("Certificate not found. Generate it first."));

            ByteArrayOutputStream output = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document, output);
            document.open();
            addCentered(document, "Certificate of Achievement", new Font(Font.HELVETICA, 36, Font.BOLD, new Color(0, 51, 102)), 30);
            addCentered(document, "This is to certify that", new Font(Font.HELVETICA, 16), 20);
            addCentered(document, user.getUsername(), new Font(Font.HELVETICA, 24, Font.BOLD), 20);
            addCentered(document, "has successfully completed the quiz on", new Font(Font.HELVETICA, 16), 15);
            addCentered(document, session.getTopic(), new Font(Font.HELVETICA, 24, Font.BOLD), 20);
            addCentered(document, "Score: " + session.getScore() + "%", new Font(Font.HELVETICA, 16), 30);
            addCentered(document, "Date: " + certificate.getIssueDate().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")), new Font(Font.HELVETICA, 12), 10);
            addCentered(document, "Certificate ID: " + certificate.getCertificateNumber(), new Font(Font.HELVETICA, 12), 0);
            document.close();
            return output.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF: " + e.getMessage(), e);
        }
    }

    private void addCentered(Document document, String text, Font font, int spacingAfter) throws DocumentException {
        Paragraph paragraph = new Paragraph(text, font);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.setSpacingAfter(spacingAfter);
        document.add(paragraph);
    }

    public Certificate verifyCertificate(String certificateNumber) {
        if (certificateNumber == null || certificateNumber.isBlank()) return null;
        return certificateRepository.findByCertificateNumber(certificateNumber.trim().toUpperCase()).orElse(null);
    }
}
