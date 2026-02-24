package com.mentorHub.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to); // 보낼 이메일 주소
        message.setSubject(subject); // 메일 제목
        message.setText(text); // 메일 내용
        emailSender.send(message); // 메일 전송
    }
}
