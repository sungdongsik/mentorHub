# ============================================
# Stage 1: Build (Gradle 빌드 수행)
# ============================================
# 컴파일 + 빌드 전용
FROM gradle:8.5-jdk17-alpine AS builder

WORKDIR /web

# 루트 Gradle 구성 복사
COPY settings.gradle .
COPY buildSrc buildSrc

# 모든 모듈 복사 (멀티모듈 필수)
COPY module-core module-core
COPY module-util module-util

# 루트 기준으로 module-core만 빌드
RUN gradle clean :module-core:bootJar --no-daemon

# ============================================
# Stage 2: Runtime
# ============================================
FROM eclipse-temurin:17-jre-alpine

WORKDIR /web

# builder 스테이지에서 빌드된 jar 파일만 복사
COPY --from=builder /web/module-core/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
