FROM eclipse-temurin:21-jdk AS build
WORKDIR /workspace
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw
COPY src/ src/
RUN ./mvnw -B package

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /workspace/target/swimteam-web-0.0.1-SNAPSHOT.jar app.jar
ENV SWIMTEAM_DATA_FILE=/app/data/team-data.xml
EXPOSE 8080
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=65.0", "-jar", "/app/app.jar"]
