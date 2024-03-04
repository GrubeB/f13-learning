# Solo learning system

## Table of Contents

* [General Info](#general-information)
* [Technologies Used](#technologies-used)

## General Information

The goal of the project was to write application to learn.

## Technologies Used

- Spring Boot 3.1
- PostgreSQL 13

## Documentation
1. Api documentation: 
   - [Postman](https://www.postman.com/f13-learning-service/workspace/team-workspace/overview)

## Run
### Prod
1. Remove old service image:

        docker rmi learning-service -f

2. Start prod env:

        docker-compose -f .\.docker\docker-compose-prod.yaml up -d

### Dev
1. Start dev env:

        docker-compose -f .\.docker\docker-compose.yaml up -d

2. Build:
   - Without tests:

            .\gradlew build -x test
   - With test:

            .\gradlew build -x test
3. Run application:

         .\gradlew :service:learning:learning-service:bootRun


## Docker
### Publish image
1. Build learning-service image:

        docker build -f .\.docker\learning_service_image\Dockerfile -t grubeb/learning-service:latest .

2. Publish image:

         docker push grubeb/learning-service:latest
