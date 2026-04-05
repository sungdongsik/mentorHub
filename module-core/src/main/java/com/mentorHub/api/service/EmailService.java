package com.mentorHub.api.service;

import com.util.CommonStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;

    public CommonStatus sendSimpleMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to); // 보낼 이메ail 주소
            message.setSubject(subject); // 메일 제목
            message.setText(text); // 메일 내용
            emailSender.send(message); // 메일 전송
            return CommonStatus.SUCCESS;
        } catch (Exception e) {
            log.error("메일 전송 실패: {}", e.getMessage());
            return CommonStatus.FAIL;
        }
    }
}
