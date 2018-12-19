# auth

## Project setup

1. Docker - PostgreSQL 10 install
```
cd auth-server/src/main/docker
docker build -t postgres-tsmrp .
docker-compose -f postgres.yaml up
```

2.. Build & Run
```
UNIX
./mvnw clean install && ./mvnw spring-boot:run -pl auth-server

WINDOWS
./mvnw.cmd clean install && ./mvnw.cmd spring-boot:run -pl auth-server
```

