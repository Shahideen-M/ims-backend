# Use Eclipse Temurin JDK 21
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy everything (including pom.xml and src)
COPY . .

# Build the JAR inside Docker
RUN ./mvnw clean package -DskipTests

# Expose port
EXPOSE 10000

# Run the app
ENTRYPOINT ["java","-jar","target/inventory-management-system-0.0.1-SNAPSHOT.jar"]
