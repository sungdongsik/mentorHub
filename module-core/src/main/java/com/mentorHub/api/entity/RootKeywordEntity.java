package com.mentorHub.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "TB_ROOT_KEYWORD",
        uniqueConstraints = @UniqueConstraint(columnNames = "canonicalName")
)
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class RootKeywordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rootKeywordId;

    private String canonicalName;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;
}
