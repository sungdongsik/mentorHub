package com.mentorHub.api.entity;

import com.util.MenteeRecruitmentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TB_MENTEE")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@SQLRestriction("del_yn = 'N'")
public class MenteeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long writingId;

    @Column(updatable = false)
    private Long userId;

    private String name;

    private String title;

    private String content;

    private LocalDateTime startDate;

    private String job;

    @OneToMany(mappedBy = "mentee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenteeKeywordEntity> keywords = new ArrayList<>();

    @OneToMany(mappedBy = "mentee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewEntity> reviews = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private MenteeRecruitmentStatus recruitmentStatus;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

    @Builder.Default
    @Column(name = "del_yn", length = 1)
    private String delYn = "N";  // 기본값 N

    /**
     * 내부 상태를 안전하게 변경하기 위한 메서드
     */
    public void addReviews(List<ReviewEntity> reviews) {
        if (reviews == null) return;
        this.reviews.addAll(reviews);
    }

    public void addKeywords(List<MenteeKeywordEntity> keywords) {
        if (reviews == null) return;
        this.keywords.addAll(keywords);
    }

    public void replaceKeywords(List<MenteeKeywordEntity> newKeywords) {
        this.keywords.clear();       // 기존 데이터 비워주기
        this.keywords.addAll(newKeywords);
    }
}
