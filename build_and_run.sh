#!/bin/bash

git pull

docker-compose down

cd embedded-iam
mvn clean package
cd ..

cd gateway-service
mvn clean package
cd ..

cd user-management-service
mvn clean package
cd ..

cd conversation-service
mvn clean package
cd ..
# Run Docker Compose
docker-compose up -d
