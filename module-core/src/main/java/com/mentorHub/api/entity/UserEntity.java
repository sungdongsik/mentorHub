package com.mentorHub.api.entity;

import com.util.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TB_USER")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends BaseEntity{

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
}
