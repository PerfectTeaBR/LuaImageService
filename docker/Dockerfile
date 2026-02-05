FROM gradle:8.5-jdk21 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
# Builda o JAR do projeto
RUN gradle buildFatJar --no-daemon

# Estágio 2: Execução
FROM openjdk:21-slim
EXPOSE 8080
RUN mkdir /app
# Cria a pasta de uploads para o java.io.File não dar erro
RUN mkdir /app/uploads 
COPY --from=build /home/gradle/src/build/libs/*.jar /app/image-service.jar
WORKDIR /app
CMD ["java", "-jar", "image-service.jar"]
