package com.mentorHub.api.entity;

import com.util.UserType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "TB_MENTEE")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class MenteeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long writingId;

    private Long userId;

    private String name;

    private String title;

    private String content;

    private LocalDateTime startDate;

    private String[] keyword;

    private String job;

    @OneToMany(mappedBy = "mentee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewEntity> reviews;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

    @Builder.Default
    @Column(name = "del_yn", length = 1)
    private String delYn = "N";  // 기본값 N
}
