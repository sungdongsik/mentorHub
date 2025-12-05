package com.mentorHub.api.entity;

import com.util.ApplicationStausType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "TB_MENTEE_APPLICATION")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenteeApplicationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menteeId;

    @Column(updatable = false)
    private Long userId;

    @Column(updatable = false)
    private String name;

    @Column(updatable = false)
    private LocalDateTime startDate;

    @Column(updatable = false)
    private String comment;

    @Enumerated(EnumType.STRING)
    private ApplicationStausType admission;

    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

    @Builder.Default
    @Column(name = "del_yn", length = 1)
    private String delYn = "N";  // 기본값 N
}
