package com.mentorHub.api.dto.response;

import com.mentorHub.api.entity.RootKeywordAliasEntity;
import com.util.RootKeywordAliasStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RootKeywordPutResponse {
    private Long rootKeywordAliasId;

    private RootKeywordAliasStatus status;

    private String remark;

    public static RootKeywordPutResponse from(RootKeywordAliasEntity en) {
        return RootKeywordPutResponse.builder()
                .rootKeywordAliasId(en.getRootKeywordAliasId())
                .status(en.getStatus())
                .remark(en.getRemark())
                .build();
    }
}
