package com.mentorHub.api.entity;

import com.util.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "TB_USER")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String email;

    private String name;

    private String pass;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private UserType status;

    private String keyword[];

    private String job;

    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

    @Builder.Default
    @Column(name = "del_yn", length = 1)
    private String delYn = "N";  // 기본값 N
}
