package com.mentorHub.api.entity;

import com.util.CommonStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@Table(name = "TB_EMAIL_HISTORY")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class EmailHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long emailHisId;

    private String recipient;

    private String subject;

    @Lob
    private String content;

    @Enumerated(EnumType.STRING)
    private CommonStatus isSuccess;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    public static EmailHistoryEntity of(String recipient, String subject, String content, CommonStatus isSuccess) {
        return EmailHistoryEntity.builder()
                .recipient(recipient)
                .subject(subject)
                .content(content)
                .isSuccess(isSuccess)
                .build();
    }
}
