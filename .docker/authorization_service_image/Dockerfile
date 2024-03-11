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
COPY --from=builder /workspace/app/service/authorization/authorization-service/build/libs/ /workspace/app/
RUN ls -a
RUN rm authorization-service.jar
RUN mv authorization-service-*.jar authorization-service.jar
ENTRYPOINT ["java", "-jar", "authorization-service.jar"]