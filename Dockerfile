# Use Eclipse Temurin 21 JDK
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy everything (pom.xml, src, mvnw, etc.)
COPY . .

# Make mvnw executable
RUN chmod +x mvnw

# Build the JAR
RUN ./mvnw clean package -DskipTests

# Expose port
EXPOSE 10000

# Run the app
ENTRYPOINT ["java","-jar","target/inventory-management-system-0.0.1-SNAPSHOT.jar"]