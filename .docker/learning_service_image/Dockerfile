FROM gradle:8.2.1-jdk17-jammy as builder
WORKDIR /workspace/app
COPY build.gradle .
COPY settings.gradle .
COPY common/ common/
COPY service/ service/
RUN gradle build -x test

FROM eclipse-temurin:17-jre-jammy
WORKDIR /workspace/app
EXPOSE 8080
COPY --from=builder /workspace/app/service/learning/learning-service/build/libs/ /workspace/app/
RUN rm learning-service.jar
RUN mv learning-service-*.jar learning-service.jar
ENTRYPOINT ["java", "-jar", "learning-service.jar"]