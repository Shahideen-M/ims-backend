# Use OpenJDK 21
FROM openjdk:21-jdk-slim

# Set working directory
WORKDIR /app

# Copy Maven build output
COPY target/*.jar app.jar

# Expose port (Render forwards this automatically)
EXPOSE 10000

# Run the app
ENTRYPOINT ["java","-jar","app.jar"]
