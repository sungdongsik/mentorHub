package com.mentorHub.api.entity;

import com.util.RootKeywordAliasStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
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

    @Column(updatable = false)
    private String canonicalName;

    @Enumerated(EnumType.STRING)
    private RootKeywordAliasStatus status;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

    // 정적 팩토리 메서드
    public static RootKeywordEntity create(String canonicalName) {
        return RootKeywordEntity.builder()
                .canonicalName(canonicalName)
                .status(RootKeywordAliasStatus.PENDING)
                .build();
    }
}
