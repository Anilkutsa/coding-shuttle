# 🚀 Coding Shuttle: Week 1 - Your Journey Begins!
Welcome to Week 1 of the Coding Shuttle course! This week is all about laying the foundation for your coding journey. Whether you're a complete beginner or brushing up on your skills, we've designed this module to make sure you start strong.


# 📚 Topics Covered in Week 1

## Introduction to Spring Framework
   What is Spring?
   An overview of the Spring Framework, its modularity, and how it simplifies enterprise Java development.
   
   Core Concepts:
   * Dependency Injection (DI): Understanding how Spring manages object dependencies.
   * Inversion of Control (IoC): How Spring decouples application components for better flexibility.

## Spring Beans

In Spring, a **Bean** is an object managed by the **Spring IoC (Inversion of Control) container**.  
Beans are central to the Spring Framework, representing components or services used throughout the application.

### Key Features
- The Spring container controls their lifecycle and instantiation.
- Beans are defined in configuration files (XML or Java-based) or annotated using `@Component`, `@Bean`, etc.

### Bean Lifecycle
1. **Instantiation**: The container creates the bean instance.
2. **Dependency Injection**: Dependencies are resolved and injected.
3. **Initialization**: Bean-specific initialization logic runs.
4. **Destruction**: Cleanup happens when the application context is shut down.

### Configuring Beans

#### Using Annotations
- `@Component`: Marks a class as a Spring Bean.
- `@Autowired`: Automatically injects bean dependencies.
- `@Scope`: Defines the bean's scope (e.g., `singleton`, `prototype`).

### Bean Scopes
- **Singleton** (default): One instance per Spring container.
- **Prototype**: A new instance every time the bean is requested.
- **Others**:
   - `request`: One instance per HTTP request.
   - `session`: One instance per HTTP session.
   - `application`: One instance per servlet context.

## Dependency Injection in Spring

**Dependency Injection (DI)** is a design pattern in Spring that provides objects with their dependencies, managed by the **Spring IoC (Inversion of Control) container**. This promotes **loose coupling** and enhances testability.

### Why Use DI?
- Reduces coupling between components.
- Centralizes dependency management.
- Simplifies testing and configuration.

### Types of DI
1. **Constructor Injection**: Dependencies are passed through the constructor.
   ```java
   @Component
   public class Service {
       private final Repository repository;

       public Service(Repository repository) {
           this.repository = repository;
       }
   }

2. **Setter Injection**: Dependencies are set via setter methods

      ```java
       @Component
       public class Service {
       private Repository repository;

       @Autowired
       public void setRepository(Repository repository) {
           this.repository = repository;
       }
    }
   
3. **Field Injection** (Not Recommended)
    ```java
   @Component
   public class Service {
   @Autowired
   private Repository repository;
   }

### Key Annotations

- `@Autowired`: Automatically wires dependencies.
- `@Component`: Marks a class as a Spring-managed bean.
- `@Qualifier`: Specifies which bean to inject when multiple are available.
- `@Primary`: Marks a default bean for injection.

## application.properties

The application.properties file in a Spring Boot project is a configuration file used to define key-value pairs that configure the application's behavior. It is part of Spring Boot's externalized configuration mechanism, allowing developers to customize settings without modifying the code.

### Purpose
- Centralizes configuration for the application.
- Defines settings for server properties, database connectivity, logging, and more.
- Supports multiple environments (e.g., development, production) via profiles.

## How @ConditionalOnProperty Works
The annotation allows you to define:
- The property name to check.
- The expected value of the property.
- Whether the bean should load based on the presence or absence of the property.

Example: If a property my.feature.enabled=true is set in application.properties, only then the bean will be created.
    
```java
    @Bean
    @ConditionalOnProperty(name = "my.feature.enabled", havingValue = "true", matchIfMissing = false)
    public MyFeatureBean myFeatureBean() {
        return new MyFeatureBean();
    }

