package com.quizapp.controller;

import com.quizapp.entity.Certificate;
import com.quizapp.service.CertificateService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CertificateController {
    
    private final CertificateService certificateService;

    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }
    
    @PostMapping("/certificate/generate/{quizSessionId}")
    public String generateCertificate(@PathVariable Long quizSessionId) {
        certificateService.generateCertificate(quizSessionId);
        return "redirect:/certificate/download/" + quizSessionId;
    }
    
    @GetMapping("/certificate/download/{quizSessionId}")
    public ResponseEntity<byte[]> downloadCertificate(@PathVariable Long quizSessionId) {
        byte[] pdfBytes = certificateService.generatePDF(quizSessionId);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "certificate.pdf");
        
        return ResponseEntity.ok()
            .headers(headers)
            .body(pdfBytes);
    }
    
    @GetMapping("/certificate/verify")
    public String verifyCertificatePage() {
        return "certificate";
    }
    
    @PostMapping("/certificate/verify")
    @ResponseBody
    public ResponseEntity<Certificate> verifyCertificate(@RequestParam String certificateNumber) {
        Certificate certificate = certificateService.verifyCertificate(certificateNumber);
        if (certificate != null) {
            return ResponseEntity.ok(certificate);
        }
        return ResponseEntity.notFound().build();
    }
}
