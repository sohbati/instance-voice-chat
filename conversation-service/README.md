### docker build image
  - docker buildx build --platform linux/arm64 --load -t myapp:latest .
### run java in local machine
  -  java -jar target/quarkus-app/quarkus-run.jar 

### Remove all stopped containers
  -  docker container prune 

