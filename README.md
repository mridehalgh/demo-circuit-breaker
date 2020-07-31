# Circuit Breaker JMS Example

## Setting Up

Start a backend service:

```bash
$ docker-compose up
```

Running demo app

```bash
$ mvn spring-boot:run
```

## Triggering backend to fail

To make the backend service fail run:
```bash
curl --location --request POST 'http://localhost:9898/readyz/disable'
```

To allow the backend service to succeed again run:

```bash
curl --location --request POST 'http://localhost:9898/readyz/enable'
```

