FROM openjdk:21-rc
WORKDIR /build
#RUN  mvn clean package
WORKDIR /app
COPY target/quarkus-app /app
CMD ["java", "-jar", "/app/quarkus-run.jar"]