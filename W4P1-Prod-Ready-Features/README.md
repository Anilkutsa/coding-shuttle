# 🚀 Coding Shuttle: Week 4 (PART 1) - Production Ready Spring Boot Features

Welcome to Week 4 of the Coding Shuttle course! This week, we dive into advanced features of Spring Boot, equipping you with tools to build robust, production-ready applications. Below are the topics we will cover:

---

# 📚 Topics Covered in Week 4
1. **DevTools**
2. **Auditing**
3. **RestClient**
4. **Logging**
5. **Actuator**
6. **OpenAPI and Swagger**

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
