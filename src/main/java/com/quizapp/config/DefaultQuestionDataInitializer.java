package com.quizapp.config;

import com.quizapp.entity.Question;
import com.quizapp.repository.QuestionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DefaultQuestionDataInitializer {
    @Bean
    CommandLineRunner seedDefaultQuestions(QuestionRepository repository) {
        return args -> {
            if (repository.count() > 0) {
                return;
            }
            repository.saveAll(List.of(
                    question("Which keyword creates a subclass in Java?", "extends", "inherits", "implements", "super", "A", "Java", "Easy"),
                    question("Which collection does not allow duplicate values?", "List", "Set", "Queue", "ArrayList", "B", "Java", "Easy"),
                    question("What does JVM stand for?", "Java Variable Method", "Java Virtual Machine", "Java Verified Module", "Java Version Manager", "B", "Java", "Easy"),
                    question("Which HTTP status means a request was successful?", "201", "301", "404", "200", "D", "Web Development", "Easy"),
                    question("What does HTML stand for?", "HyperText Markup Language", "HighText Machine Language", "Hyperlink Text Management Language", "Home Tool Markup Language", "A", "Web Development", "Easy"),
                    question("Which Python type stores key-value pairs?", "list", "tuple", "dict", "set", "C", "Python", "Easy"),
                    question("What does len([1, 2, 3]) return in Python?", "2", "3", "4", "An error", "B", "Python", "Easy"),
                    question("Which SQL command reads rows from a table?", "SELECT", "PUSH", "READ", "FETCH ALL", "A", "SQL", "Easy"),
                    question("What is the primary purpose of CSS?", "Store data", "Style web pages", "Run server code", "Compile Java", "B", "Web Development", "Easy"),
                    question("Which data structure follows first-in, first-out order?", "Stack", "Tree", "Queue", "Graph", "C", "Computer Science", "Medium")
            ));
        };
    }

    private static Question question(String text, String a, String b, String c, String d,
                                      String correct, String topic, String difficulty) {
        Question question = new Question();
        question.setQuestionText(text);
        question.setOptionA(a);
        question.setOptionB(b);
        question.setOptionC(c);
        question.setOptionD(d);
        question.setCorrectAnswer(correct);
        question.setTopic(topic);
        question.setDifficulty(difficulty);
        return question;
    }
}
