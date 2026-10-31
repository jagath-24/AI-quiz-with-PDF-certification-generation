package com.quizapp.service;

import com.quizapp.entity.User;
import com.quizapp.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UserService {
    
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Transactional
    public User createOrGetUser(String username, String email) {
        // Check by username first, then by email to avoid duplicate constraint
        return userRepository.findByUsername(username)
            .or(() -> userRepository.findByEmail(email))
            .orElseGet(() -> {
                User user = new User();
                user.setUsername(username);
                user.setEmail(email);
                user.setCreatedAt(LocalDateTime.now());
                return userRepository.save(user);
            });
    }
    
    public User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
