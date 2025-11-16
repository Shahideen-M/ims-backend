# Use Eclipse Temurin JDK 21
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy Maven build output
COPY target/*.jar app.jar

# Expose port (Render forwards this automatically)
EXPOSE 10000

# Run the app
ENTRYPOINT ["java","-jar","app.jar"]
