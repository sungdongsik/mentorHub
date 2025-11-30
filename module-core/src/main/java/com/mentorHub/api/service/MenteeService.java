package com.mentorHub.api.service;

import com.mentorHub.api.dto.PageResponse;
import com.mentorHub.api.dto.request.*;
import com.mentorHub.api.dto.response.MenteeApplicationResponse;
import com.mentorHub.api.dto.response.MenteeCommandResponse;
import com.mentorHub.api.dto.response.MenteeResponse;
import com.mentorHub.api.entity.MenteeApplicationEntity;
import com.mentorHub.api.entity.MenteeEntity;
import com.mentorHub.api.repository.MenteeApplicationRepository;
import com.mentorHub.api.repository.MenteeRepository;
import com.mentorHub.api.repository.query.MenteeApplicationQuery;
import com.mentorHub.api.repository.query.MenteeQuery;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class MenteeService {

    private final MenteeRepository menteeRepository;

    private final MenteeQuery menteeQuery;

    private final MenteeApplicationRepository menteeApplicationRepository;

    private final MenteeApplicationQuery menteeApplicationQuery;

    @Transactional(readOnly = true)
    public PageResponse<MenteeResponse> getMentees(MenteeRequest request) {
        List<MenteeResponse> list = menteeQuery.getMentees(request);
        return PageResponse.of(list);
    }

    public MenteeCommandResponse setMentees(MenteeCreateRequest request){

        MenteeEntity en = menteeRepository.save(request.toEntity());

        return MenteeCommandResponse.builder()
                .writingId(en.getWritingId())
                .title(en.getTitle())
                .build();
    }

    public MenteeCommandResponse deleteMentees(MenteeDeleteRequest request) {

        menteeRepository.deleteById(request.getWritingId());

        return MenteeCommandResponse.builder()
                .writingId(request.getWritingId())
                .title(null)
                .build();
    }

    public MenteeCommandResponse putMentees(MenteePutRequest request) {

        MenteeEntity en = menteeRepository.save(request.toEntity());

        return MenteeCommandResponse.builder()
                .writingId(en.getWritingId())
                .title(en.getTitle())
                .build();
    }

    public MenteeApplicationResponse createMenteesApplication(MenteeApplicationCreateRequest request) {

        MenteeApplicationEntity en = menteeApplicationRepository.save(request.toEntity());

        return MenteeApplicationResponse.builder()
                .menteeId(en.getMenteeId())
                .admission(en.getAdmission())
                .build();
    }

    public MenteeApplicationResponse updateApplicationStatus(MenteeApplicationPutRequest request) {

        menteeApplicationQuery.updateApplicationStatus(request);

        return MenteeApplicationResponse.builder()
                .menteeId(request.getMenteeId())
                .admission(request.getAdmission())
                .build();
    }
}
