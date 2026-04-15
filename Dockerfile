# Multi-stage Dockerfile for Strong Number Checker
# Stage 1: Build stage
FROM maven:3.8.1-openjdk-11 AS builder

WORKDIR /app

# Copy Maven configuration
COPY pom.xml .

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Runtime stage
FROM amazoncorretto:11

# Set working directory
WORKDIR /app

# Copy JAR from builder
COPY --from=builder /app/target/strong-number-1.0.0.jar .

# Health check
HEALTHCHECK --interval=30s --timeout=3s --retries=3 CMD ["java", "-version"] || exit 1

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
    CMD java -cp strong-number-1.0.0.jar com.example.StrongNumber || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "strong-number-1.0.0.jar"]
