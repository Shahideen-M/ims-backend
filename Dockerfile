# Use OpenJDK 21 for compatibility
FROM openjdk:21

# Set working directory
WORKDIR /app

# Copy project files
COPY . .

# Give permission to mvnw and build
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# Expose backend port
EXPOSE 8080

# Run the built jar file from target folder
CMD ["sh", "-c", "java -jar target/*.jar"]