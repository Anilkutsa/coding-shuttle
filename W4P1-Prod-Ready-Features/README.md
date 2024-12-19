# 🚀 Coding Shuttle: Week 4 - Production ready Spring Boot Features
Welcome to Week 4 of the Coding Shuttle course! In this week, we will cover the below-mentioned topics.

# 📚 Topics Covered in Week 4
1. DevTools
2. Auditing
3. RestClient
4. Logging
5. Actuator
6. OpenAPI and Swagger

# 🚧 DevTools

## Installation 
Add DevTools Dependency: Make sure you have the spring-boot-devtools dependency in your pom.xml or build.gradle file.
```java
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
</dependency>
```
After this, set the IntelliJ settings to allow restarting.

## Automatic Restart
* Automatically restarts the application whenever files on the classpath change. This is faster than a full restart and only reloads the changed parts.
* Uses two class loaders: one for the base classes that don't change (typically libraries) and another for the classes that do change (application code). When a change is detected, only the latter is reloaded, making the restart faster.

## Useful configurations for Dev-tools
```java
// Disable automatic restart feature of Spring DevTools
spring.devtools.restart.enabled=false
 
//Exclude certain directories (static and public) from triggering restarts
spring.devtools.restart.exclude=<PATH>
 
// Set the interval (in milliseconds) for checking file changes
spring.devtools.restart.pollInterval=20

// Set the quiet period (in milliseconds) before restarting after detecting changes
spring.devtools.restart.quietPeriod=10
```

# 🕵️‍♂️ Auditing

Auditing in Spring Boot allows you to automatically populate certain fields, such as creation and modification timestamps, as well as the user who created or modified the entity.

## Steps to Add Auditing
1. Create Auditable base Entity using the following annotations **`@EntityListeners`**, **`AuditingBaseEntity.class`** in our case. This way you will get access to @CreatedBy, @CreatedDate, @LastModifiedBy, and @LastModifiedDate annotations.
2. Extend all Entities from the Superclass. In our case, extend **`PostEntity`** with **`AuditingBaseEntity`**.
3. Enable JPA Auditing by adding the **`@EnableJpaAuditing`** annotation to a configuration class.
4. Create a class and Implement the **`AuditorAware`** interface. **`auth/AuditorAwareImpl.java`** class, in our case.
   * This will provide the current authenticated user from Spring Security. Used for createdBy/updatedBy names.
   * Create a bean for above class in config class **`AppConfig`** (**`getAuditorAwareImpl`** method), so that bean is supplied to spring security by the system.
   * Pass the bean as param for **`@EnableJpaAuditing`** with param name auditorAwareRef.

## Internal working of Auditing
1. When an entity is persisted or updated, the **`AuditingEntityListener`** triggers and performs the following actions: PrePersist PreUpdate
2. The AuditorAware interface provides the information about the current user.

# Advanced Auditing using hibernate-envers

## Steps Involved
1. Install the dependency in POM file
2. Add **`@Audited`** annotation in **`AuditingBaseEntity`** class. 
3. Add the annotation to entity classes where you want to monitor the changes.
4. Alternately, you can add **`@NotAudited`** annotation to fields which you don't want to monitor.

That's it. Now hibernate-envers will create 2 additional tables posts_aud and revinfo for tracking all the changes to data.

hibernate-envers provides us with AuditReaderFactory object which we can use reading audit files of a particular entity class.
You can call {{URL}}/posts/{postId} (built using **`AuditController`**) to check audit logs.

