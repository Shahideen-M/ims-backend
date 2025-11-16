FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copy everything
COPY . .

# Make mvnw executable
RUN chmod +x mvnw

# Build the JAR
RUN ./mvnw clean package -DskipTests

# Expose Spring Boot port
EXPOSE 8080

# Run the JAR
ENTRYPOINT ["java","-jar","target/inventory-management-system-0.0.1-SNAPSHOT.jar"]
