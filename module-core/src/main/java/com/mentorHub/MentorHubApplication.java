package com.mentorHub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MentorHubApplication {
    public static void main(String[] args) {
        SpringApplication.run(MentorHubApplication.class, args);
    }
}