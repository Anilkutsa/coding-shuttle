# 🚀 Coding Shuttle: Week 6 - Advanced Spring Security

Welcome to Week 7 of the Coding Shuttle course! This week, we dive into types of Testing of Spring App. Below are the topics we will cover:

---

# 📚 Topics Covered in Week 7 - Testing
1. Software Development Life Cycle (SDLC)
2. Understanding Junit and AssertJ
3. Unit Test vs Integration Test
4. Unit testing the persistence layer and Setting up TestContainer

---


# Software Development Life Cycle (SDLC)

The Software Development Life Cycle (SDLC) is a systematic process for planning, creating, testing, and deploying software applications. It defines a structured approach to software development, ensuring that software meets business and technical requirements.

## Phases of SDLC and Associated Roles

### 1. **Requirement Analysis** 📋
- **Description:** Gather and analyze business requirements to define the project's goals and scope.
- **Key Roles Involved:**
    - **Business Analyst (BA):** Gathers and documents requirements from stakeholders.
    - **Product Manager:** Prioritizes requirements based on business needs.
    - **Project Manager (PM):** Coordinates with stakeholders to ensure clarity of requirements.

### 2. **Planning** 🗓️
- **Description:** Develop a roadmap and allocate resources for the project.
- **Key Roles Involved:**
    - **Project Manager (PM):** Creates the project plan and timeline.
    - **Scrum Master (in Agile):** Facilitates sprint planning and team coordination.
    - **Stakeholders:** Provide inputs for prioritization and deliverables.

### 3. **System Design** 🖌️
- **Description:** Translate requirements into technical designs and architecture.
- **Key Roles Involved:**
    - **System Architect:** Designs the overall system architecture.
    - **UI/UX Designer:** Designs user interfaces and experiences.
    - **Technical Lead:** Provides technical guidance for design decisions.

### 4. **Development** 💻
- **Description:** Write and implement code based on the design specifications.
- **Key Roles Involved:**
    - **Software Developers/Engineers:** Develop and implement the software.
    - **Version Control Manager:** Ensures proper versioning of the codebase.

### 5. **Testing** 🧪
- **Description:** Identify and fix defects in the software through rigorous testing.
- **Key Roles Involved:**
    - **QA Engineer/Tester:** Performs functional, integration, and regression testing.
    - **Automation Tester:** Develops scripts for automated testing.
    - **Test Manager:** Oversees the testing process and ensures quality.

### 6. **Deployment** 🚀
- **Description:** Deploy the software to the production environment.
- **Key Roles Involved:**
    - **DevOps Engineer:** Manages CI/CD pipelines and deployment processes.
    - **System Administrator:** Ensures smooth deployment and server configuration.
    - **Release Manager:** Oversees the deployment schedule and release notes.

### 7. **Maintenance** 🔧
- **Description:** Provide ongoing support and updates to the software.
- **Key Roles Involved:**
    - **Support Engineer:** Addresses user issues and bugs.
    - **Maintenance Team:** Implements updates and improvements.
    - **Product Manager:** Collects feedback for future iterations.

## SDLC Models

1. **Waterfall Model:** Sequential approach, suitable for well-defined projects. 💧
2. **Agile Model:** Iterative and incremental, suitable for dynamic requirements. 🌀
3. **Spiral Model:** Combines iterative and risk-driven approaches. 🔄
4. **V-Model:** Testing is integrated with each phase of development. ✅
5. **DevOps Model:** Focuses on collaboration between development and operations. 🤝

---

## Importance of SDLC

- Provides a structured framework for software development. 🏗️
- Enhances project planning and resource allocation. 📊
- Ensures high-quality deliverables and customer satisfaction. 🌟
- Reduces risks and improves cost management. 💰

---

**Note:** Each phase in SDLC may overlap or iterate depending on the methodology used (e.g., Agile or Waterfall). Collaboration and communication among all roles are essential for successful delivery.

---

# 2. Understanding Junit and AssertJ

## Common Junit Annotations 
* **`@Test:`** Marks a method as a test method. JUnit will execute this method when running tests. 
* **`@DisplayName:`** Sets a custom display name for the test class or test method. This name is used in test reports and IDEs. 
* **`@Disabled:`** Disables a test class or test method. Disabled tests are not executed.

## Junit Annotations 
* **`@BeforeEach:`** Indicates that the annotated method should be executed before each test method. These can be used to reset each test case conditions. 
* **`@AfterEach:`** Indicates that the annotated method should be executed after each test method. 
* **`@BeforeAll:`** Indicates that the annotated method should be executed once before all test methods in the class. The method must be static. (Executed once) 
* **`@AfterAll:`** Indicates that the annotated method should be executed once after all test methods in the class. The method must be static. (Executed once)

## JUnit vs AssertJ
* **`JUnit`** is one of the most widely used testing frameworks in the Java ecosystem. JUnit provides a simple and standardized way to write test cases, execute them, and report the results. 
* **`AssertJ`**, on the other hand, is not a testing framework but rather a library that complements testing frameworks like JUnit. It focuses on providing fluent and expressive assertions, enhancing the readability and maintainability of your test code.  
* **`JUnit and AssertJ`** are both popular tools used in Java for testing, but they serve different purposes and have distinct features. 

---

# 3. Unit Test vs Integration Test

1. **`Unit Tests:`** A unit test covers a single “unit”, where a unit commonly is a single class, but can also be a cluster of cohesive classes that is tested in combination. 
   * **`Focus:`** Individual components, such as methods in a service class. 
   * **`Tools:`** JUnit, Mockito 
   * **`Example:`** Testing a service method that performs a calculation or business logic

2. **`Integration Tests:`** A test that covers multiple layers. This might cover the interaction between a business service and the persistence layer, for instance. 
   * **`Focus:`** Interaction between multiple components, such as repositories, services, and controllers. 
   * **`Tools:`** Spring Test, @SpringBootTest. 
   * **`Example:`** Testing the interaction between a service and a repository.

---

# 4. Unit testing the persistence layer and Setting up TestContainer

- Press **`CMD + SHIFT + T`** for creating a new test case.

## Using @DataJpaTest Slice
- `@DataJpaTest` is tailored to test JPA components like repositories. It configures an in-memory database, sets up Spring Data JPA repositories, and scans for JPA entities. This makes it ideal for testing repository methods and their interactions with the database. 
- By default, `@DataJpaTest` runs each test within a transaction, which is rolled back after the test completes. This ensures tests do not affect each other and provides a clean state for each test.

## Configuring Test Database

Use the AutoConfiureTestDatabase Annotation to configure the test database 

```java
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
```

- **`ANY`**: Replace the DataSource bean whether it was auto-configured or manually defined. 
- **`NONE`**: Don't replace the application default DataSource. 
- **`AUTO_CONFIGURED`**: Only replace the DataSource if it was auto-configured.

## Running TestContainer
Use Testcontainer to mock a real database, Use when mocking the repository or in Integration testing. 
#### - Step 1: Download the Docker Application from here 
#### - Step 2: Add the following dependency:

```properties
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-testcontainers</artifactId>
  <scope>test</scope>
</dependency>
```

#### Step 3: Create the following Test Configuration: 

```java
@TestConfiguration
public class TestContainerConfiguration {
    @Bean
    @ServiceConnection
    PostgreSQLContainer<?> postgresContainer() {
        return new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));
    }
}
```

#### Step 4: Import the Configuration in your test file:

```java
@Import(TestContainerConfiguration.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmployeeRepositoryTest {...}
```
---
