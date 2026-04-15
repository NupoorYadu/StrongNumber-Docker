# Multi-stage Dockerfile for Strong Number Checker
# Stage 1: Build stage
FROM maven:3.8.1-openjdk-11-slim AS builder

WORKDIR /app

# Copy Maven configuration
COPY pom.xml .

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Runtime stage
FROM openjdk:11-jre-slim

# Set working directory
WORKDIR /app

# Create non-root user for security
RUN useradd -m -u 1000 appuser

# Copy JAR from builder
COPY --from=builder /app/target/strong-number-1.0.0.jar .

# Change ownership
RUN chown appuser:appuser /app

# Switch to non-root user
USER appuser

# Expose port (if needed for future API)
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
    CMD java -cp strong-number-1.0.0.jar com.example.StrongNumber || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "strong-number-1.0.0.jar"]
