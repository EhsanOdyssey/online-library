# online-library

## Table of Contents ##
1. [Running the server as Docker Container](#Running-the-server-as-Docker-Container)

## Running the server as Docker Container ##
To Build image use below command:

```
COMPOSE_DOCKER_CLI_BUILD=1 DOCKER_BUILDKIT=1 docker-compose -f ./.docker/docker-compose.yaml up -d
```

http://localhost:8070/olib/api/swagger-ui.html
