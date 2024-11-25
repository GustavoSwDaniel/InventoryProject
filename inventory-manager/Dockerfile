# Etapa 1: Build da aplicação
FROM maven:3.8-openjdk-17-slim AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests -X

RUN ls -l /app/target

FROM openjdk:17-jdk-slim

WORKDIR /src

COPY --from=build /app/target/*.jar ./inventory-manager.jar

CMD ["java", "-jar", "inventory-manager.jar"]
