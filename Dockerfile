# Build stage
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom.xml and download dependencies (cache layer)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests -B

# Run stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy jar file from build stage
COPY --from=build /app/target/pharmacy_webapp-0.0.1-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 8080

# Optimize JVM for Render
ENV JAVA_OPTS="-Xmx400m -Xms256m -XX:+UseG1GC"

# Run application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]