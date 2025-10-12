# Usa una imagen ligera de Java 17
FROM openjdk:17-jdk-slim

# Copia el JAR compilado por Maven
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Ejecuta la aplicación
ENTRYPOINT ["java", "-jar", "/app.jar"]
