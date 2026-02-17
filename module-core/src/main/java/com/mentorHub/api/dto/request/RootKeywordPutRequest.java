package com.mentorHub.api.dto.request;

import com.mentorHub.api.entity.RootKeywordAliasEntity;
import com.mentorHub.api.entity.RootKeywordEntity;
import com.util.RootKeywordAliasStatus;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RootKeywordPutRequest {

    private Long rootKeywordId;

    private Long rootKeywordAliasId;

    private RootKeywordAliasStatus status;

    private String aliasName;

    private String remark;

    public RootKeywordAliasEntity toEntity(RootKeywordEntity en) {
        return RootKeywordAliasEntity.builder()
                .rootKeyword(en)
                .rootKeywordAliasId(rootKeywordAliasId)
                .status(status)
                .aliasName(aliasName)
                .remark(remark)
                .build();
    }

    public boolean isActive() {
        return this.status == RootKeywordAliasStatus.ACTIVE;
    }

    public boolean isDeleted() {
        return this.status == RootKeywordAliasStatus.DELETED;
    }
}
