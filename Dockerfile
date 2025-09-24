# Use Maven to build the application
FROM maven:4.0.0-rc-4-sapmachine-21 AS build
WORKDIR /app
COPY . .
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# Use OpenJDK to run the environment
FROM sapmachine:24-jdk-alpine-3.22
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]

