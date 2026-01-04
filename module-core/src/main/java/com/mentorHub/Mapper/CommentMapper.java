package com.mentorHub.Mapper;

import com.mentorHub.api.dto.response.CommentResponse;
import com.mentorHub.api.entity.CommentEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

// 객체 생성해서 쓸수 없게 막아두기
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentMapper {
    public static List<CommentResponse> fromList(List<CommentEntity> comments) {

        // 댓글만 필터링
        List<CommentResponse> parents = comments.stream()
                .filter(c -> c.getParentId() == null)
                .map(CommentResponse::from)
                .sorted(Comparator.comparing(CommentResponse::getCreateDate))
                .toList();

        // 부모별 children 매핑
        parents.forEach(parent -> {
            List<CommentResponse> children = comments.stream()
                    .filter(c -> Objects.equals(c.getParentId(), parent.getCommentId()))
                    .map(CommentResponse::from)
                    .sorted(Comparator.comparing(CommentResponse::getCreateDate))
                    .toList();

            parent.getChildren().addAll(children);
        });

        return parents;
    }
}
