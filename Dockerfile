FROM openjdk:21
WORKDIR /app
COPY . .
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests
EXPOSE 8080
CMD ["sh", "-c", "java -jar target/*.jar"]
