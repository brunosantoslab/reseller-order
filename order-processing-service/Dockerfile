# Build stage - use OpenJDK image
FROM openjdk:17-jdk-slim as build

# Set working directory inside container
WORKDIR /app

# Copy the pom.xml, mvnw wrapper, and the .mvn directory
COPY pom.xml . 
COPY mvnw ./
COPY .mvn .mvn

# Give execute permissions to mvnw
RUN chmod +x mvnw

# Copy src directory (mantenha a estrutura correta dentro do container)
COPY src /app/src

# Verifique se o mvnw está lá (para diagnóstico)
RUN ls -al /app

# Download dependencies and build the project
RUN ./mvnw clean install -DskipTests

# Verifique o conteúdo da pasta target para garantir que o JAR foi gerado
RUN ls -al /app/target

# Final stage - use OpenJDK image
FROM openjdk:17-jdk-slim

# Set working directory inside container
WORKDIR /app

# Copy the jar file generated from build stage
COPY --from=build /app/target/*.jar /app/app.jar

# Expose the port the microservice will run on
EXPOSE 8080

# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]