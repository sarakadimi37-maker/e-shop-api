# =========================
# Build stage
# =========================
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copier les fichiers nécessaires au build
COPY ../pom.xml .
COPY ../src ./src

# Build sans tests (idéal pour CI/CD)
RUN mvn clean package -DskipTests

# =========================
# Runtime stage
# =========================
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copier uniquement le jar généré
COPY --from=build /app/target/*.jar app.jar

# Port exposé par Spring Boot
EXPOSE 8080

# Variables JVM optimisées pour Docker
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

# Lancer l'application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]