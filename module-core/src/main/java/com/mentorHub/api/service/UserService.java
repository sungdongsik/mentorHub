package com.mentorHub.api.service;

import com.mentorHub.api.dto.request.UserCreateRequest;
import com.mentorHub.api.dto.response.UserResponse;
import com.mentorHub.api.entity.UserEntity;
import com.mentorHub.api.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserEntity setUsers(UserEntity request) {
        return userRepository.save(request);
    }

    public UserEntity putUsers(UserEntity request) {
        return userRepository.save(request);
    }
}
