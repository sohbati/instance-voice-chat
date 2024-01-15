FROM openjdk:21-rc
WORKDIR /build
#RUN  mvn clean package
WORKDIR /app
COPY target/embedded-iam-0.0.1.jar /app/app.jar
CMD ["java", "-jar", "/app/app.jar"]