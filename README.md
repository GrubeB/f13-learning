# Solo learning system

## Table of Contents

* [General Info](#general-information)
* [Technologies Used](#technologies-used)

## General Information

The goal of the project was to write application to learn.

## Technologies Used

- Spring Boot 3.1
- PostgreSQL 13

## Docker

### Dev
1. Start dev env:

        docker-compose -f .\.docker\docker-compose.yaml up -d

2. Build learning-service image:

        docker build -f .\.docker\learning_service_image\Dockerfile -t learning-service:latest .

3. Publish image:

         docker push grubeb/learning-service:latest

### Prod
1. Remove old service image:

        docker rmi learning-service -f

2. Start prod env:

        docker-compose -f .\.docker\docker-compose-prod.yaml up -d