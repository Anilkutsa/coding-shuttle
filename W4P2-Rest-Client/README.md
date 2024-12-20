# 🚀 Coding Shuttle: Week 4 (PART 2) - REST CLIENT, LOGGING (SLF4J)
Welcome to Week 4 of the Coding Shuttle course! In this week, we will cover the below-mentioned topics.

# RestClient

The **RestClient** is a synchronous HTTP client that provides a modern, fluent API. It simplifies interaction with HTTP services by abstracting low-level HTTP details and offering convenient methods for converting Java objects to HTTP requests and vice versa.

---

## Key Features
- **Fluent API**: Simplified and readable chaining methods for constructing HTTP requests.
- **Object Conversion**: Seamless conversion between Java objects and HTTP requests/responses.
- **HTTP Methods**: Support for common HTTP methods like `GET`, `POST`, `PUT`, `PATCH`, and `DELETE`.
- **Error Handling**: Built-in mechanisms to handle HTTP errors effectively.

---

## Building a RestClient

You can create a `RestClient` instance using the fluent builder approach. Here's an example:

```java
RestClient restClient = RestClient.builder()
        .baseUrl(BASE_URL) // Base URL for all HTTP requests
        .defaultHeader(HttpHeaders.AUTHORIZATION,
                encodeBasic(properties.getUsername(), properties.getPassword())) // Default headers
        .build();
```

Parameters:
* **baseUrl:** The root URL to which endpoints will be appended.
* **defaultHeader:** A default header to include in every request. In this example, it’s an Authorization header.

## Using the RestClient
Performing an HTTP GET request to fetch a resource:
```java
CustomerResponse customer = restClient.get()
        .uri("/{id}", 3) // Specify the endpoint with a path variable
        .accept(MediaType.APPLICATION_JSON) // Specify the accepted response content type
        .retrieve() // Retrieve the HTTP response
        .body(CustomerResponse.class); // Convert the response body to a Java object
```
Supported HTTP Methods:
* **GET:** restClient.get()
* **POST:** restClient.post()
* **PUT:** restClient.put()
* **PATCH:** restClient.patch()
* **DELETE:** restClient.delete()

## Handling Errors in RestClient
```java
ResponseEntity<Void> response = restClient.delete()
        .uri("/{id}", 3)
        .onStatus(HttpStatusCode::is4xxClientError,
                (req, res) -> {
                    logger.error("Couldn't delete resource: " + res.getStatusText());
                    return Mono.empty();
                })
        .toBodilessEntity();
```
Explanation:
* onStatus: A callback method to handle specific HTTP status codes.
* HttpStatusCode::is4xxClientError: Checks for client-side errors (4xx codes).
* logger.error: Logs an error message for debugging purposes.
* toBodilessEntity: Processes the response as an empty body.

## Configuration Details

### RestClientConfig
This class is used to configure the RestClient. Common configurations include:
* Base URL
* Default headers
* Error handling strategies

### Example Use Cases

#### Fetching a Customer by ID
```java
CustomerResponse customer = restClient.get()
        .uri("/customers/{id}", 1)
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .body(CustomerResponse.class);
```
#### Creating a New Employee
```java
EmployeeRequest request = new EmployeeRequest("John Doe", "Developer");
EmployeeResponse response = restClient.post()
        .uri("/employees")
        .contentType(MediaType.APPLICATION_JSON)
        .body(request)
        .retrieve()
        .body(EmployeeResponse.class);
```
#### Deleting an Employee by ID with Error Handling
```java
ResponseEntity<Void> response = restClient.delete()
        .uri("/employees/{id}", 10)
        .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
            logger.error("Error deleting employee: " + res.getStatusText());
            return Mono.empty();
        })
        .toBodilessEntity();
```

Check out **`RestClientConfig`** & **`EmployeeClientImpl`** classes for more details.

# Logging in Java with SLF4J and Spring Boot

Logging is the process of tracking events that occur while a piece of software runs. It is an essential aspect of software development, as it aids in debugging, performance monitoring, and understanding application behavior.

A logging framework simplifies tasks such as:
- Setting log file destinations
- Customizing log messages
- Formatting output for readability

---

## Introduction to SLF4J

SLF4J (**Simple Logging Façade for Java**) is an abstraction layer for various logging frameworks like:
- **Log4J**
- **java.util.logging (JUL)**
- **TinyLog**
- **Logback**

### Why Use SLF4J?
1. **Flexibility**: Enables switching between logging frameworks without modifying application code.
2. **Simplicity**: Provides a unified API for all supported frameworks.
3. **Inbuilt in Spring Boot**: Spring Boot comes with SLF4J pre-configured, making it a go-to choice for logging in Java applications.

---

## Elements of a Logging Framework

Every logging framework consists of three primary components:

1. **Logger**: Captures log messages.
2. **Formatter**: Formats log messages captured by the logger.
3. **Handler**: Dispatches messages to various destinations such as:
    - Console
    - Files
    - Emails

---

## Log Levels

Log levels represent the severity of messages being logged. They help developers focus on relevant information during development and debugging.

### Supported Log Levels in Spring Boot:
1. **TRACE**: Most detailed level, used for granular debugging.
2. **DEBUG**: Provides information about the flow of the system.
3. **INFO**: General runtime information about the application.
4. **WARN**: Indicates potential issues that may not necessarily crash the application.
5. **ERROR**: Logs runtime errors that require immediate attention.
6. **FATAL**: Critical errors causing system failure.

### Example:
When you set a log level, events of that level and higher severity are logged. For instance:
- `logging.level.root=INFO`: Logs `INFO`, `WARN`, `ERROR`, and `FATAL`, but not `DEBUG` or `TRACE`.

---

## Configuring Log Levels in Spring Boot

You can set log levels in the `application.properties` file:

```properties
# Set root log level
logging.level.root=INFO

# Set log level for a specific package
logging.level.com.myPackageName=DEBUG
```

## Customizing Log Messages with Formatters

Spring Boot allows you to customize the format of log messages. This can include:
* Date and time
* Log level
* Class name
* Thread executing the log
* Custom messages

Formatting Options:

1.	Console Logs: Customize log patterns for console output.
```properties
logging.pattern.console=%d [%level] %c{1.} [%t] %m%n
```
* %d: Date and time of the log
* %level: Log level (e.g., INFO, DEBUG)
* %c: Class name
* %t: Thread name
* %m: Log message
* %n: New line

## Setting Log Handlers

Log handlers define where the log output should be sent. Common destinations include:
1.	Console: Default logging destination.
2.	File: Store logs in a file for auditing or debugging.

### File Configuration Examples:
1. Set log file name:
```properties
logging.file.name=application.log
```
2. Set log file path:
```properties
logging.file.path=/var/logs/
```

## Summary
Logging frameworks like SLF4J provide developers with tools to:
* Track and debug application behavior.
* Configure log levels and destinations.
* Customize log message formats.

Spring Boot’s integration with SLF4J makes logging setup easy and efficient, supporting various back-end frameworks seamlessly.