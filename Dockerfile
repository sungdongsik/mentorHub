# syntax=docker/dockerfile:1
FROM eclipse-temurin:17-jdk-alpine

# 프로젝트 파일이 위치할 디렉토리
WORKDIR /app

# Gradle 또는 Maven 빌드로 만들어진 jar 복사
# odule-core/build/libs/*.jar -> 실제 jar 이름 맞춰서 수정
COPY module-core/build/libs/*.jar app.jar

# 서버 포트 (application.yml/server.port와 동일해야 함)
EXPOSE 8080

# 자바 실행 명령어
ENTRYPOINT ["java", "-jar", "app.jar"]
