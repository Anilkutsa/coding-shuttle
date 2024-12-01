# 🚀 Coding Shuttle: Week 2 - Build Set of API's for Engineering Department!
The following was discovered as part of building this project:

* The original package name 'com.codingshuttle.w2p2.department.w2p2-department' is invalid and this project uses 'com.codingshuttle.w2p2.department.w2p2_department' instead.

# 📚 Topics Covered in Week 2
1. Spring boot web and MVC Architecure
2. Presentation Layer
3. Persistence Layer
4. Service Layer
5. Exception Handling in Spring Boot MVC
6. Input Validation Annotations

# Topic 1 - Introduction to Spring MVC Architecture
* Backend consists of APIs, webservices and Databases
* There are multiple standards for APIs - RESTFul, SOAP, WebSockets
* REST - Rest stands for (Representational State Transfer) APIs are set of rules and conventions for building and interacting with web services. REST API's are stateless.

## REST Methods -
- GET /users: Retrieve a list of all users.
- GET /users/{id}: Retrieve a specific user by ID.
- POST /users: Create a new user.
- PUT /users/{id}: Update an existing user by ID.
- PATCH /users/{id}: Partially update an existing user by ID.
- DELETE /users/{id}: Delete a user by ID.

## MVC (MVC Architecture)
In Spring Boot, the Model-View-Controller (MVC) architecture is a design pattern that separates an application into three interconnected components, promoting organized and maintainable code. Here's a brief overview of each layer and its significance:
- **`Model:`** Represents the application's data and business logic. It manages the data, logic, and rules of the application, ensuring that the data is accurate and up-to-date.
- **`View:`** Handles the presentation layer, rendering the data from the model in a user-friendly format. It is responsible for displaying the data to the user and capturing user input.
- **`Controller:`** Acts as an intermediary between the Model and View. It processes user inputs, updates the model, and determines the appropriate view to display.
By adhering to the MVC pattern, Spring Boot applications achieve a clear separation of concerns, making the codebase more modular, easier to maintain, and scalable.

![image info](./assets/mvc_architecture.png)

## Layered Architecture
- **`Presentation Layer:`** Handles the user interface and user interactions, presenting information to users and capturing user input. This includes Authenctication & JSON Translation.
- **`Business Layer:`** Implements the core functionality and rules that drive the business processes and operations. This includes Business Logic, Validation, & Authorisation.
- **`Persistence layer:`** Handles the storage & retrieval of data from databases. This includes Storage logic and JPQL (Java Persistance Queries Language).
- **`Database layer:`** Stores, maintains, and manages data structurally and organizationally.

![image info](./assets/spring-project-structure.png)

# Topic 2 - Presentation Layer, DTO & Controllers

#### DispatcherServlet
In Spring MVC, a single servlet known as the Dispatcher Servlet handles all incoming requests (front controller). A design pattern used in the creation of web applications is the front controller. All requests are received by a single servlet, which then distributes them to every other part of the program.

