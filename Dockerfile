FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT [ "sh", "-c", "java -jar /app.jar --spring.config.location=$CONFIG_LOCATION" ]
