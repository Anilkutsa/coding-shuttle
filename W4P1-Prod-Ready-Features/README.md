# 🚀 Coding Shuttle: Week 4 (PART 1) - Production Ready Spring Boot Features

Welcome to Week 4 of the Coding Shuttle course! This week, we dive into advanced features of Spring Boot, equipping you with tools to build robust, production-ready applications. Below are the topics we will cover:

---

# 📚 Topics Covered in Week 4
1. **DevTools:** A Spring Boot module that enhances the development experience by enabling features like automatic restarts, live reloads, and property overrides during development.
2. **Auditing:** A feature in Spring Boot that tracks and logs changes to entities, such as created and modified timestamps, using annotations like @CreatedDate and @LastModifiedDate.
3. **Actuators:** A Spring Boot module that provides production-ready features like health checks, metrics, and application insights via endpoints.
4. **OpenAPI and Swagger:** Tools used in Spring Boot to document and design REST APIs, enabling automatic generation of API specifications and interactive UI for testing.


---

# 🚧 DevTools

Spring Boot DevTools is a development-time utility that enhances productivity by providing features like automatic restart and live reload.

## 🛠️ Installation

To use DevTools, add the following dependency in your `pom.xml` or `build.gradle` file:

```xml
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-devtools</artifactId>
</dependency>
```

## IntelliJ Configuration:
* Enable “Build project automatically” under File > Settings > Build, Execution, Deployment > Compiler.
* Enable “Registry > compiler.automake.allow.when.app.running” via Ctrl + Shift + A > Registry.

## 🔄 Automatic Restart
Automatically restarts the application when files in the classpath are changed.
* How it works:
  * Two ClassLoaders:
    1. Base ClassLoader: Loads stable libraries.
    2. Restart ClassLoader: Reloads application classes on change.

## 🔧 Configurations for DevTools:
Add these properties to your application.properties to customize DevTools behavior:
```properties
# Disable automatic restart
spring.devtools.restart.enabled=false
        
# Exclude specific directories (e.g., static resources) from triggering restarts
spring.devtools.restart.exclude=static/**,public/**

# Polling interval for checking file changes (in milliseconds)
spring.devtools.restart.pollInterval=20

# Quiet period before restarting (in milliseconds)
spring.devtools.restart.quietPeriod=10
```

# 🕵️‍♂️ Auditing

Auditing in Spring Boot simplifies the tracking of:
* Created By: Who created the entity.
* Created Date: When the entity was created.
* Modified By: Who last updated the entity.
* Modified Date: When the entity was last updated.

## 🛠️ Steps to Implement Auditing
1.	Create a Base Entity: Use @EntityListeners(AuditingEntityListener.class) and annotations like @CreatedDate, @LastModifiedDate, @CreatedBy, @LastModifiedBy.

Example:
```java
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditingBaseEntity {
@CreatedDate
private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;
}
```
2. Extend the Base Entity: Extend all entities (e.g., PostEntity) from the AuditingBaseEntity:

```java
@Entity
public class PostEntity extends AuditingBaseEntity {
   @Id
   @GeneratedValue
   private Long id;
   private String title;
}
```

3.	Enable JPA Auditing: Add @EnableJpaAuditing in a configuration class:
```java
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class AppConfig {
   @Bean
   public AuditorAware<String> auditorProvider() {
       return new AuditorAwareImpl();
   }
}
```
4. Implement AuditorAware: Return the current authenticated user:
```java
public class AuditorAwareImpl implements AuditorAware<String> {
   @Override
   public Optional<String> getCurrentAuditor() {
       return Optional.of(SecurityContextHolder.getContext().getAuthentication().getName());
   }
}
```

## Internal working of Auditing
1. When an entity is persisted or updated, the **`AuditingEntityListener`** triggers and performs the following actions: PrePersist PreUpdate
2. The AuditorAware interface provides the information about the current user.

# Advanced Auditing using hibernate-envers
Hibernate Envers tracks historical changes in entity data, storing detailed revisions.

## Steps Involved
🚀 Steps to Enable Envers:
1.	Add the dependency:
```xml
<dependency>
   <groupId>org.hibernate</groupId>
   <artifactId>hibernate-envers</artifactId>
</dependency>
```
2.	Annotate your entity classes: Add @Audited to the entity or its fields:
```java
@Entity
@Audited
public class PostEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @NotAudited
    private String description; // Excluded from auditing
}
```

3.	Database Changes:
   * Envers automatically creates additional tables like:
     * post_aud: Stores entity change history.
     * revinfo: Tracks revision metadata.

4.	Use AuditReaderFactory to fetch historical data:
```java
AuditReader auditReader = AuditReaderFactory.get(entityManager);
PostEntity oldVersion = auditReader.find(PostEntity.class, postId, revisionNumber);
```

5.	Example API for Auditing: Create a REST endpoint in AuditController
```java
@GetMapping("/posts/{postId}/history")
public List<PostEntity> getPostHistory(@PathVariable Long postId) {
    return auditService.getPostHistory(postId);
}
```

That's it. Now hibernate-envers will create 2 additional tables posts_aud and revinfo for tracking all the changes to data.

hibernate-envers provides us with AuditReaderFactory object which we can use reading audit files of a particular entity class.
You can call {{URL}}/posts/{postId} (built using **`AuditController`**) to check audit logs.

# Actuators
A Spring Boot module that provides production-ready features like health checks, metrics, and application insights via endpoints.

## How to Enable Actuator Endpoints
1. Add the Dependency
```properties
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

2. Configure in application.properties or application.yml
```properties
management.endpoints.web.exposure.include=*
management.endpoint.health.show-details=always
```

3. Secure the Endpoints By default, most Actuator endpoints are restricted. Use Spring Security to restrict access to sensitive endpoints.

4. Once you have integrated, you can view all the API's exposed by actuator using url - http://localhost:8080/actuator

## Common Endpoints 

**`/actuator/health:`** Provides the health status of the application (e.g., UP or DOWN). Can include details about subsystems like databases, queues, or external APIs when configured with health indicators.

**`/actuator/metrics:`** Shows various application metrics such as memory usage, active threads, and HTTP request statistics.
Example: /actuator/metrics/jvm.memory.used shows JVM memory usage.

**`/actuator/info:`** Displays custom application information (e.g., version, build details) that you can configure in application.properties using the info prefix.

**`/actuator/env:`** Exposes the application's environment properties, including system properties, environment variables, and application configuration properties.

**`/actuator/loggers:`** Allows runtime viewing and adjustment of logging levels for different packages/classes.

**`/actuator/beans:`** Lists all Spring beans currently managed by the application context, including their dependencies.

**`/actuator/mappings:`** Shows all the HTTP endpoints and their mappings in the application.

**`/actuator/threaddump:`** Provides a thread dump of the JVM, useful for diagnosing threading issues.

**`/actuator/heapdump:`** Produces a heap dump of the JVM, useful for memory analysis.

**`/actuator/scheduledtasks:`** Displays all scheduled tasks in the application.

**`/actuator/httpexchanges:`** Shows information about recent HTTP requests and responses (requires additional configuration).

# OpenAPI and Swagger in Spring Boot

OpenAPI is a specification for describing REST APIs in a standard, language-agnostic way. Swagger is a set of tools built around the OpenAPI specification that provides features such as interactive documentation, API testing, and client/server code generation.

In Spring Boot applications, springdoc-openapi is a popular library used to integrate OpenAPI and generate Swagger documentation seamlessly.

## Features of OpenAPI and Swagger in Spring Boot
1. **`API Documentation:`** Automatically generates OpenAPI-compliant documentation for your APIs.
2. **`Interactive UI:`** Provides a Swagger UI where developers can test APIs directly.
3. **`Code Generation:`** Generates client or server code from the OpenAPI specification.
4. **`Customizable:`** Allows custom configurations and extensions.

## Integrating OpenAPI/Swagger with Spring Boot
1. Add Dependency
   Add the following Maven dependency to your pom.xml:

```properties
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.1.0</version> <!-- Use the latest compatible version -->
</dependency>
```

2. Enable OpenAPI Documentation
   No additional configuration is required. The library auto-scans your controllers and generates OpenAPI documentation.

3. Access Swagger UI
   Once integrated, the following endpoints are exposed by default:

* Swagger UI: http://localhost:8080/swagger-ui.html
* OpenAPI JSON: http://localhost:8080/v3/api-docs
* OpenAPI YAML: http://localhost:8080/v3/api-docs.yaml

## Configuration Options
You can customize the behavior of springdoc-openapi using application properties or Java configuration.

1. Application Properties
   Add configurations in application.properties or application.yml:
```properties
# Base path for the OpenAPI specification
springdoc.api-docs.path=/openapi

# Customize Swagger UI path
springdoc.swagger-ui.path=/api-docs

# Enable/disable Swagger UI
springdoc.swagger-ui.enabled=true

# Group APIs (for multiple API groups)
springdoc.group-configs[0].group=public
springdoc.group-configs[0].paths-to-match=/public/**
springdoc.group-configs[1].group=admin
springdoc.group-configs[1].paths-to-match=/admin/**
```

## Annotations for OpenAPI/Swagger
Use these annotations to enhance the documentation for your APIs:

### Controller Annotations
* **`@RestController:`** Marks the class as a controller.
* **`@RequestMapping or @GetMapping, @PostMapping, etc.:`** Specify endpoints.

### OpenAPI-Specific Annotations
* **`@Operation:`** Adds metadata for specific endpoints.
* **`@ApiResponses:`** Documents possible responses.
* **`@Parameter:`** Describes a parameter.
* **`@Schema:`** Describes a model schema.

Example:
```java
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Operation(summary = "Get greeting message", description = "Returns a simple greeting message.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful response"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, World!";
    }
}
```

## Customizing Swagger UI
You can customize the Swagger UI using application.properties:

```properties
# Customize UI title
springdoc.swagger-ui.title=My API Documentation

# Set default URL for the API docs
springdoc.swagger-ui.url=/v3/api-docs

# Disable the "Try it out" feature
springdoc.swagger-ui.try-it-out-enabled=false
```