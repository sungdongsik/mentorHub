package com.mentorHub.api.entity;

import com.util.KeywordStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "TB_ROOT_KEYWORD",
        uniqueConstraints = @UniqueConstraint(columnNames = "canonicalName")
)
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class RootKeywordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rootKeywordId;

    private String canonicalName;

    @Enumerated(EnumType.STRING)
    private KeywordStatus status;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    // 정적 팩토리 메서드
    public static RootKeywordEntity create(String canonicalName) {
        return RootKeywordEntity.builder()
                .canonicalName(canonicalName)
                .status(KeywordStatus.PENDING)
                .build();
    }
}
