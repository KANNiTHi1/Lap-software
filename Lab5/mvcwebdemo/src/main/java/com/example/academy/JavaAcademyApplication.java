package com.example.academy;

import com.example.academy.model.Registrant;
import com.example.academy.service.RegistrantService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JavaAcademyApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaAcademyApplication.class, args);
    }

    // Seed data (ตัวอย่างผู้สมัคร)
    @Bean
    CommandLineRunner init(RegistrantService service) {
        return args -> {
            service.add(new Registrant(null, "Alice", "alice@example.com", "Spring Boot Fundamentals"));
            service.add(new Registrant(null, "Bob", "bob@example.com", "OOP & Java Core"));
            service.add(new Registrant(null, "Carol", "carol@example.com", "Spring MVC & Thymeleaf"));
        };
    }
}
