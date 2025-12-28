package com.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum ChatSelectMessageType {
    BACKEND(
            "멘티 추천을 도와드릴게요 😊\n" +
                    "관심 있는 분야를 선택해주세요.\n\n" +
                    "1️⃣ 백엔드\n" +
                    "   - Java\n" +
                    "   - Python\n" +
                    "   - Go\n" +
                    "   - C"
    ),

    FRONTEND(
            "멘티 추천을 도와드릴게요 😊\n" +
                    "관심 있는 분야를 선택해주세요.\n\n" +
                    "2️⃣ 프론트엔드\n" +
                    "   - JavaScript\n" +
                    "   - TypeScript\n" +
                    "   - React\n" +
                    "   - Vue"
    ),

    MOBILE(
            "멘티 추천을 도와드릴게요 😊\n" +
                    "관심 있는 분야를 선택해주세요.\n\n" +
                    "3️⃣ 모바일\n" +
                    "   - Android(Kotlin)\n" +
                    "   - iOS(Swift)\n" +
                    "   - Flutter\n" +
                    "   - React Native"
    );

    private final String message;

    public static ChatSelectMessageType from(String content) {
        return Arrays.stream(values())
                .filter(v -> v.name().equalsIgnoreCase(content))
                .findFirst()
                .orElse(null);
    }

}
