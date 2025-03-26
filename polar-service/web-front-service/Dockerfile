FROM gradle:jdk21-graal-jammy AS builder
WORKDIR /workspace
COPY build.gradle .
COPY settings.gradle .
RUN gradle wrapper
RUN ./gradlew dependencies
COPY src src
RUN ./gradlew build -x test

FROM container-registry.oracle.com/graalvm/jdk:21
WORKDIR /workspace
COPY --from=builder /workspace/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]