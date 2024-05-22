FROM eclipse-temurin:17-alpine
RUN apk add --no-cache tzdata
EXPOSE 8080

COPY ./target/SimpleRestService-1.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java", "-Xms512m", "-Xmx1024m", "-Djava.security.egd=file:/dev/./urandom", "-Djava.net.preferIPv4Stack=true", "-jar", "/app.jar"]