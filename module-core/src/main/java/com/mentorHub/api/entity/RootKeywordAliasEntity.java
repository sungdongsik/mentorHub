package com.mentorHub.api.entity;

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
@Table(name = "TB_ROOT_KEYWORD_ALIAS")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class RootKeywordAliasEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rootKeywordAliasId;

    private String aliasName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rootKeywordId")
    private RootKeywordEntity rootKeyword;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    public static RootKeywordAliasEntity create(String aliasName, RootKeywordEntity en) {
        return RootKeywordAliasEntity.builder()
                .aliasName(aliasName)
                .rootKeyword(en)
                .build();
    }
}
