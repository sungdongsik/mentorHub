package com.mentorHub.api.dto.request;

import com.mentorHub.api.entity.RootKeywordEntity;
import com.util.RootKeywordAliasStatus;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RootKeywordPutRequest {

    private Long rootKeywordId;

    private RootKeywordAliasStatus status;

    public RootKeywordEntity toEntity() {
        return RootKeywordEntity.builder()
                .rootKeywordId(rootKeywordId)
                .status(status)
                .build();
    }
}
