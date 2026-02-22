package com.mentorHub.api.dto.request;

import com.mentorHub.api.entity.RootKeywordEntity;
import com.util.RootKeywordAliasStatus;
import lombok.*;

@Getter
@Setter
@ToString
public class RootKeywordRequest {
    private RootKeywordAliasStatus status;
    private int page = 0;
    private int size = 10;

    public RootKeywordEntity toEntity() {
        return RootKeywordEntity.builder()
                .status(status)
                .build();
    }
}
