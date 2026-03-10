package com.mentorHub.api.service;

import com.mentorHub.api.entity.EmailHistoryEntity;
import com.mentorHub.api.repository.EmailHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailHistoryService {

    private final EmailHistoryRepository emailHistoryRepository;

    public void saveEmailHistory(String recipient, String subject, String content) {
        EmailHistoryEntity emailHistory = EmailHistoryEntity.of(recipient, subject, content);
        emailHistoryRepository.save(emailHistory);
    }
}
