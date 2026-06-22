# Calculator Microservice

Spring Boot microservice that performs arithmetic operations between two operands through a REST API.

This proof of concept currently supports:

- Addition
- Subtraction

Each calculated result is sent to the provided tracer library.

## Technical overview

- Maven project.
- Spring Boot REST microservice.
- Strategy-based operation handling, so new operations can be added without changing the main calculator service.
- Structured API error responses.
- The provided tracer artifact is bundled in the project under `lib/maven-repository`, so no manual dependency installation is required.

## Requirements

- Java 17
- Maven 3

## Build

Run the following command from the project root:

```bash
mvn clean package
```

The executable JAR will be generated under:

```text
target/calculator-0.0.1-SNAPSHOT.jar
```

## Run

After building the project, run:

```bash
java -jar target/calculator-0.0.1-SNAPSHOT.jar
```

The service starts on the default Spring Boot port:

```text
8080
```

## API

### Calculate

```http
POST /api/calculator/calculate
Content-Type: application/json
```

Request body:

```json
{
  "operation": "ADD",
  "leftOperand": 2,
  "rightOperand": 3
}
```

Successful response:

```json
{
  "operation": "ADD",
  "leftOperand": 2,
  "rightOperand": 3,
  "result": 5
}
```

### Supported operations

| Operation | Description |
| --- | --- |
| `ADD` | Adds both operands |
| `SUBTRACT` | Subtracts the right operand from the left operand |

Operands are handled as decimal numbers, so integers, decimal values, and negative values are supported.

## Examples

Addition:

```bash
curl -X POST http://localhost:8080/api/calculator/calculate \
  -H "Content-Type: application/json" \
  -d '{"operation":"ADD","leftOperand":2,"rightOperand":3}'
```

Subtraction:

```bash
curl -X POST http://localhost:8080/api/calculator/calculate \
  -H "Content-Type: application/json" \
  -d '{"operation":"SUBTRACT","leftOperand":7,"rightOperand":4}'
```

Negative and decimal values:

```bash
curl -X POST http://localhost:8080/api/calculator/calculate \
  -H "Content-Type: application/json" \
  -d '{"operation":"ADD","leftOperand":-2.5,"rightOperand":3.75}'
```

## Error responses

Invalid requests return `400 Bad Request` with a structured error body.

Example:

```json
{
  "code": "INVALID_REQUEST",
  "message": "The request contains invalid fields",
  "details": [
    "operation: must not be null"
  ]
}
```

Unsupported operation values or malformed JSON return:

```json
{
  "code": "INVALID_REQUEST_BODY",
  "message": "The request body is invalid or malformed",
  "details": [
    "Check the JSON format and supported operation values: ADD, SUBTRACT"
  ]
}
```

## Tests

Run:

```bash
mvn clean test
```

## Project structure

```text
src/main/java/com/gft/calculator/
  config/       Spring configuration
  controller/   REST API controllers
  domain/       Domain enums and concepts
  dto/          API request and response objects
  exception/    API error handling
  service/      Business services and operation strategies
```
