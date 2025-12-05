# Etapa 1: Construcción (Build)
FROM gradle:8.5-jdk21 AS build
WORKDIR /app
COPY . .
# Damos permisos al wrapper y construimos el JAR (saltando tests para ir más rápido)
RUN chmod +x gradlew
RUN ./gradlew bootJar --no-daemon -x test

# Etapa 2: Ejecución (Run)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# Copiamos el .jar generado desde la etapa anterior
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]