# 1단계: 빌드 단계
FROM gradle:8.5-jdk21 AS builder

# 작업 디렉토리 설정
WORKDIR /app

# Gradle 캐시 최적화를 위해 설정 파일만 먼저 복사
COPY gradlew build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle

# 의존성 먼저 다운 (변동이 적으니까 캐시를 활용 가능)
RUN gradle build --no-daemon || return 0

# 전체 소스 복사 후 빌드
COPY . .
RUN gradle clean build --no-daemon

# 2단계: 실행 단계
FROM eclipse-temurin:21-jdk-jammy

# 애플리케이션 디렉토리 생성
WORKDIR /app

# 빌드된 JAR 복사 (파일명은 상황에 따라 조정하세요)
COPY --from=builder /app/build/libs/*.jar app.jar


# 포트 설정 (Render는 PORT=10000 전달함)
ENV PORT=10000
EXPOSE 10000

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
