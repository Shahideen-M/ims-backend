FROM maven:4.0.0-openjdk-21 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN #mvn clean package -DskipTests

FROM openjdk:21

WORKDIR /app

COPY --from=build /app/target/inventory-management-system-0.0.1-SNAPSHOT.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app/inventory-management-system-0.0.1-SNAPSHOT.jar"]