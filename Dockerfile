# ============================================
# Stage 1: Build (Gradle 빌드 수행)
# ============================================
# 컴파일 + 빌드 전용
FROM gradle:8.5-jdk17-alpine AS builder

WORKDIR /web

# 전체 프로젝트 복사 (.dockerignore가 필터링)앷
COPY . .

# 루트 기준으로 module-core만 빌드
RUN gradle clean :module-core:bootJar --no-daemon

# ============================================
# Stage 2: Runtime
# ============================================
FROM eclipse-temurin:17-jre-alpine

WORKDIR /web

# 스테이지에서 빌드된 jar 파일만 복사
COPY --from=builder /web/module-core/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
