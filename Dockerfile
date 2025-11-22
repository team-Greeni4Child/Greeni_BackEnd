# 🔹 빌드 스테이지
FROM eclipse-temurin:21 AS builder
WORKDIR /app

COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew bootJar -x test

# 🔹 실행 스테이지
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

ENV TZ=Asia/Seoul

ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-Duser.timezone=Asia/Seoul", "-jar", "/app/app.jar"]
