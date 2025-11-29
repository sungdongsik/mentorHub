package com.mentorHub.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "TB_MENTEE")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenteeEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long writingId;

    private Long userId;

    private String title;

    private String content;

    private LocalDateTime startDate;

    private String[] keyword;

    private String job;

}
