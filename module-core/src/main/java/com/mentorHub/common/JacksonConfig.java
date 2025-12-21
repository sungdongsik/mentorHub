package com.mentorHub.common;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

@Configuration
// Jackson 설정을 전역으로 관리하기 위한 설정 클래스
public class JacksonConfig {

    @Bean
    // Spring Boot 기본 ObjectMapper에 LocalDateTime 직렬화 설정을 추가하기 위한 Bean
    // ObjectMapper를 새로 생성하지 않고 기존 설정을 커스터마이징함
    public Jackson2ObjectMapperBuilderCustomizer jacksonCustomizer() {

        return builder -> {

            // LocalDateTime 응답 포맷 지정
            // (기본 ISO 포맷: yyyy-MM-dd'T'HH:mm:ss.nnnnnn → 변경)
            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // LocalDateTime → JSON 변환 시 위 포맷을 사용하도록 전역 Serializer 등록
            builder.serializers(
                    new LocalDateTimeSerializer(formatter)
            );

            // JSON 직렬화 시 기준 타임존 설정
            // 서버/컨테이너 환경과 상관없이 Asia/Seoul 기준으로 응답
            builder.timeZone(TimeZone.getTimeZone("Asia/Seoul"));
        };
    }
}

