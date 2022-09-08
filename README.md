# Confluent Schema Registry demo

Proof of concept for  integration of Spring Boot application with Kafka and Schema Registry.

### How to run

1. Startup Kafka and Schema Registry
```shell
docker-compose up
```

2. Startup service 

```shell
./greadlew bootRun
```

3. Send message

```shell
curl --silent -X GET http://localhost:8080
```

To check currently registered schemas go to http://schema-registry-ui.local
