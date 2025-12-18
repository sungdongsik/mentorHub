package com.mentorHub.api.dto.request;

import com.mentorHub.api.entity.UserEntity;
import com.util.UserType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserPutRequest {
    private Long userId;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8~20자여야 합니다.")
    private String pass;

    @NotBlank(message = "이름은 필수입니다.")
    @Size(max = 20, message = "이름은 20자 이하로 입력해주세요.")
    private String name;

    @NotBlank(message = "핸드폰 번호는 필수입니다.")
    @Pattern(
            regexp = "^01[0-9]-?\\d{3,4}-?\\d{4}$",
            message = "핸드폰 번호 형식이 올바르지 않습니다."
    )
    private String phoneNumber;

    private UserType status;

    private String keyword[];

    private String job;

    public UserEntity toEntity() {
        return UserEntity.builder()
                .userId(userId)
                .pass(pass)
                .name(name)
                .phoneNumber(phoneNumber)
                .keyword(keyword)
                .job(job)
                .build();
    }
}
