package com.mentorHub.api.dto.response;

import com.mentorHub.mapper.CommentMapper;
import com.mentorHub.api.entity.ReviewEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ReviewResponse {

    private Long reviewsId;

    private Long writingId;

    private String title;

    private String content;

    private String name;

    private LocalDateTime createDate;

    private List<CommentResponse> comments;

    public static ReviewResponse from(ReviewEntity en) {
        return ReviewResponse.builder()
                .reviewsId(en.getReviewId())
                .title(en.getMentee().getTitle())
                .content(en.getContent())
                .name(en.getName())
                .writingId(en.getMentee().getWritingId())
                .createDate(en.getCreatedDate())
                .comments(CommentMapper.fromList(en.getComments()))
                .build();
    }
}
