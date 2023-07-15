FROM maven:3.9.3-eclipse-temurin-17-alpine

COPY . .
RUN --mount=type=cache,target=/root/.m2 mvn verify

ENTRYPOINT ["java", "-ea", "-jar", "target/corona.jar"]
