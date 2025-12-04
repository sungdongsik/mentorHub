package com.mentorHub.api.service;

import com.mentorHub.api.dto.request.UserCreateRequest;
import com.mentorHub.api.dto.response.UserResponse;
import com.mentorHub.api.entity.UserEntity;
import com.mentorHub.api.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse setUsers(UserEntity request) {

        UserEntity en = userRepository.save(request);

        return UserResponse.builder()
                .userId(en.getUserId())
                .name(en.getName())
                .build();
    }
}
