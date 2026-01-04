package com.mentorHub.formatter;

import com.mentorHub.api.entity.MenteeEntity;

import java.util.List;
import java.util.stream.Collectors;

public class MessageFormatter {
    public static String toChatMessage(List<MenteeEntity> mentees) {
        return mentees.stream()
                .map(m -> m.getName() + ", " + m.getKeyword() + " 입니다.")
                .collect(Collectors.joining("\n"));
    }
}
