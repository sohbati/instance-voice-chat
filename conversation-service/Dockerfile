FROM openjdk:21-rc
WORKDIR /build
#RUN  mvn clean package
WORKDIR /app
ENV LOG_FILE_LOCATION=/app/logs/app.log
COPY target/quarkus-app /app
CMD ["java", "-jar", "/app/quarkus-run.jar"]