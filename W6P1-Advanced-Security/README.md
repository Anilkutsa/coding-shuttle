# 🚀 Coding Shuttle: Week 6 - Advanced Spring Security

Welcome to Week 6 of the Coding Shuttle course! This week, we dive into ADVANCED features of Spring Security. Below are the topics we will cover:

---

# 📚 Topics Covered in Week 6
1. Refresh Token vs Access Token
2. Google OAuth2 Client Authentication in Spring Security
3. User Session management using JWT
4. Role Based Authorization
5. Granular Authorization with Authority 
6. Security Method Annotations - @Secured and @PreAuthorize

---

# 1. Refresh Token vs Access Token

## Issues with JWT Access Tokens

#### 1. Increased Risk with Long-Lived Tokens
If an access token is used for an extended period, its exposure in requests increases the risk of it being intercepted or misused. Short-lived tokens reduce this risk by limiting how long a stolen token can be exploited. However, short-lived tokens alone are not a complete solution.

#### 2. Frequent Reauthentication Disrupts User Experience
Since access tokens are typically short-lived, users may need to reauthenticate frequently if a refresh token mechanism is not implemented. This can disrupt the user experience and reduce overall convenience.

---

### Solution: Two-Token Approach
To address these challenges, a two-token approach is recommended:

#### 1. Access Token
- **Purpose**: Grants short-term access to protected resources.
- **Characteristics**: Short-lived to minimize risk if intercepted. Typically stored in memory or as a client-side variable.

#### 2. Refresh Token
- **Purpose**: Used to obtain a new access token when the old one expires.
- **Characteristics**: Long-lived and securely stored (e.g., in HTTP-only cookies). Only transmitted during the token renewal process, reducing its exposure.

---

By combining access tokens for frequent, secure resource access and refresh tokens for extended user sessions, this approach ensures both security and convenience in JWT-based authentication systems.

<img src="./assets/two-token-approach.png" alt="Spring Security Flow" width="1000">

# Access Token and Refresh Token Implementation in Spring Boot

This document provides a step-by-step explanation of implementing access tokens and refresh tokens in a Spring Boot application to ensure secure and efficient authentication mechanisms.

## Overview

1. **Access Token**: A short-lived token used for accessing protected resources.
2. **Refresh Token**: A long-lived token used to obtain a new access token when the old one expires.

The application uses Spring Boot with JWT (JSON Web Token) for token-based authentication.

---

## Implementation Steps

### 1. Implement `AuthController`

The `AuthController` provides endpoints for user signup, login, and token refresh.

#### Key Endpoints:

- **`/auth/signup`**: Allows users to create an account.
- **`/auth/login`**: Authenticates the user and provides access and refresh tokens.
- **`/auth/refresh`**: Generates a new access token using the refresh token stored in cookies.

### 2. Create necessary methods in `AuthService`

The `AuthService` handles user login and token refresh logic. In our case, we have created two methods `login()` and `refreshToken` which help generate access/refresh token and return that in `LoginResponseDto` format.

### 3. Implement the `JwtService`

The `JwtService` generates and validates JWTs. In our case, we have added 2 new methods namely `generateAccessToken()` and `generateRefreshToken()` that help generate Jwt tokens with data and expiration dates.

### 4. Test the Application

Please test the application using `/auth/login` and  `/auth/refresh` API's. Reduce the expiry time of access and refresh token for the purpose of testing.

---

# 2. Google OAuth2 Client Authentication in Spring Security

This section explains how Google OAuth2 authentication works using your code implementation as a reference.

## Workflow of Google OAuth2 Authentication
Google OAuth2 is an authentication mechanism that allows users to log in using their Google account. It involves the following steps:

<img src="./assets/google-oauth-workflow.png" alt="Spring Security Flow" width="1000">

## Key Components in Your Implementation

### 1. **Security Configuration**

The `WebSecurityConfig` class configures security settings and integrates OAuth2 login.

```java

public class WebSecurityConfig {

    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                ...
                .oauth2Login(oauth2Config -> oauth2Config
                        .failureUrl("/login?error=true")
                        .successHandler(oAuth2SuccessHandler)
                );
        return httpSecurity.build();
    }
}
```
- **OAuth2 Success Handler**: The `oAuth2SuccessHandler` processes successful authentication requests.
- **JWT Authentication**: The `JwtAuthFilter` validates JWTs for secured routes.

### 2. **OAuth2SuccessHandler**

The `OAuth2SuccessHandler` handles successful authentication by:
- Retrieving user details from Google.
- Checking if the user exists in the local database; if not, creating a new user.
- Generating JWT access and refresh tokens.
- Redirecting to the frontend with the access token.

(
Replace below details - 
- gcid: 451477101490-0mk13k8vhj3e1rbp4mrsuk6915cv86ui.apps.googleusercontent.com
- gcs: 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
)
---

# 3. User Session management using JWT

User session management refers to the practice of maintaining and controlling user interactions with an application over a period of time. It involves tracking and managing user login sessions, ensuring security, and providing a seamless user experience.

---

## JWT Session management:
1. Generate ACCESS-TOKEN + REFRESH-TOKEN and store the Session using this schema 
   - `(session_id, refreshToken, userId, lastUsedAt)`
2. When renewing ACCESS-TOKEN using REFRESH-TOKEN
   - if REFRESH-TOKEN is not expired AND the session is present, return ACCESS-TOKEN
   - if session is NOT present, return Exception
3. Upon a New Login request, check if the session limit is full. 
   - if full -> remove the least recently used session 
   - else -> Follow step 1

---
## Session Workflow

<img src="./assets/session-workflow.png" alt="Spring Security Flow" width="1000">

---

## Components and Logic

### 1. **SessionService**
Handles session generation and validation logic.

#### **Methods**:

- **`generateNewSession(User user, String refreshToken)`**:
    - Creates a new session for the user.
    - If the user exceeds the session limit (default: 2), deletes the least recently used session.
    - Saves the new session to the database.

- **`validateSession(String refreshToken)`**:
    - Checks if the provided refresh token exists in the database.
    - Updates the `lastUsedAt` timestamp for the session if valid.
    - Throws `SessionAuthenticationException` if the session is invalid.

---

### 2. **SessionRepository**
A JPA repository for managing session entities in the database.

---

### 3. **Session Entity**
Defines the `Session` object with attributes such as `refreshToken`, `lastUsedAt`, and a relationship with the `User` entity.

---

### 4. **AuthService**
Handles authentication, session generation, and token-based authorization.

#### **Methods**:

- **`login(LoginDto loginDto)`**:
    - Authenticates the user using `AuthenticationManager`.
    - Generates access and refresh tokens.
    - Creates a new session using `SessionService.generateNewSession()`.

- **`refreshToken(String refreshToken)`**:
    - Validates the refresh token using `SessionService.validateSession()`.
    - Generates a new access token.
    - Fails to create refreshToken if session data does not exist

---

## Summary
This implementation ensures:
- **Session Limitation**: Prevents users from exceeding a predefined number of active sessions.
- **Session Validation**: Validates and updates session activity for secure refresh token usage.
- **Scalability**: Easily extendable to support more advanced session management features.

# 4. Role Based Authorization

## Authentication vs Authorization 
- **`Authentication`** is the process of verifying the identity of a user. It ensures that the user is who they claim to be. Authentication typically involves validating credentials, such as a username and password, and creating a security context for the user. 
- **`Authorization`** is the process of determining whether an authenticated user has the necessary permissions to access a particular resource or perform an action. It controls what an authenticated user can or cannot do. 

### Who are you– Authentication 
### What can you do - Authorization

## Components and Logic

## Overview
RBAC ensures that only authorized users with specific roles can access certain resources or perform particular actions. The implemented roles in this application are:

- **USER**: Basic access.
- **CREATOR**: Can create resources (e.g., posts).
- **ADMIN**: Full access to manage resources and users.

---

## Components and Logic

### 1. **User Entity**
The `User` entity implements `UserDetails` from Spring Security to integrate with Spring's authentication framework.

#### Code:
```java
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    ...

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toSet());
    }
    ...
}
```

### 2. **Role Enum**
Defines the available roles in the application.

#### Code:
```java
public enum Role {
    USER,
    CREATOR,
    ADMIN
}
```

### 3. **WebSecurityConfig**
Configures security settings to apply role-based access to endpoints.

#### Code:
```java
@Configuration
public class WebSecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(publicRoutes).permitAll()
                        .requestMatchers(HttpMethod.GET, "/posts/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/posts/**")
                            .hasAnyRole(Role.ADMIN.name(), Role.CREATOR.name())
                        .anyRequest().authenticated())
                ...
                );
        return httpSecurity.build();
    }
}
```

### Summary
This implementation ensures:
1. **Role-Based Authorization**: Protects endpoints based on user roles.
2. **Granular Permissions**: Allows fine-grained access control (e.g., only `CREATOR` and `ADMIN` can create posts).
3. **Scalable Design**: Easily extendable to add more roles and permissions as needed.

---

# 5. Granular Authorization with Authority 

## Role vs Authority

- Roles represent high-level, permissions that are typically associated with a group of users. For example, `USER`, `ADMIN`, `MANAGER` etc.
- Authorities, also known as privileges or permissions, represent finegrained access rights within an application. For example, different authorities can be defined to allow users to `CREATE`, `UPDATE` AND `DELETE` a particular resource. `POST_CREATE`, `POST_UPDATE`, `POST_DELETE`, `BLOG_CREATE`, `BLOG_UPDATE`, `BLOG_DELETE`.

---

## Role Implementation 

We have defined all our roles in `/entities/enums/Role.java` class.

#### Role (Entities/Enum)

```java
public enum Role {
    USER,
    CREATOR,
    ADMIN
}
```

#### User (Entity)
- We assign roles to a user during the time of his entity creation.
- User entity class implements UserDetails interface which overrides `getAuthorities()` method. We return the list of all the roles that a user has through `SimpleGrantedAuthority` object through `getAuthorities()` method. 

```java
public class User implements UserDetails {
    ...
    
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_"+role.name()))
                .collect(Collectors.toSet());
    }
    ...
}
```

#### WebSecurityConfig

- Define paths user can access based on the his role inside config class

```java
public class WebSecurityConfig {
    ...
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(publicRoutes).permitAll()
                        .requestMatchers(HttpMethod.GET, "/posts/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/posts/**")
                            .hasAnyRole(ADMIN.name(), CREATOR.name())
                        .anyRequest().authenticated())
                ...
                );
        return httpSecurity.build();
    }
```
---

## Permission Implementation (Naive)

#### Permission (Entities/Enum)

```java
public enum Permission {

    POST_VIEW,
    POST_CREATE,
    POST_UPDATE,
    POST_DELETE,

    USER_VIEW,
    USER_CREATE,
    USER_UPDATE,
    USER_DELETE

}
```

#### SignUpDto (dto)
Start accepting list of permissions for a user in the API
```java
@Data
public class SignUpDto {
    private String email;
    private String password;
    private String name;
    private Set<Role> roles;
    private Set<Permission> permissions;
}
```

#### User (Entity)
- Now you can pass permissions that a user has along with a role

```java
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class User implements UserDetails {
    ...

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Permission> permissions;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toSet());

        permissions.stream().forEach(
                permission -> authorities.add(new SimpleGrantedAuthority((permission.name()))));
        
        return authorities;
    }
    ...
}
```

#### WebSecurityConfig

- Define actions user can perform based on his authorities (permissions) inside config class

```java
import com.codingshuttle.sample.w6p1_advanced_security.entities.enums.Permission;

public class WebSecurityConfig {
    ...

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(auth -> auth
                        ...
                        .requestMatchers(HttpMethod.POST, "/posts/**")
                            .hasAnyRole(ADMIN.name(), CREATOR.name())
                        .requestMatchers(HttpMethod.POST, "/posts/**")
                            .hasAnyAuthority(Permission.POST_DELETE.name(), Permission.POST_UPDATE.name())
                        .requestMatchers(HttpMethod.DELETE, "/posts/**")
                            .hasAuthority(Permission.POST_DELETE.name())
                        .anyRequest().authenticated())
                ...
                );
        return httpSecurity.build();
    }
```

---

## Permission Optimisation - 1

#### PermissionMapping (Utils)
- Set a mapping between user role and permission in utils package and return SET of permissions given a role

```java
public class PermissionMapping {

    private static final Map<Role, Set<Permission>> map = Map.of(
            USER, Set.of(USER_VIEW, POST_VIEW),
            CREATOR, Set.of(POST_CREATE, USER_UPDATE, POST_UPDATE),
            ADMIN, Set.of(POST_CREATE, USER_UPDATE, POST_UPDATE, USER_DELETE, USER_CREATE, POST_DELETE)
    );

    public static Set<SimpleGrantedAuthority> getAuthoritiesForRole(Role role) {
        return map.get(role).stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toSet());
    }

}
```

#### User (Entity)

- Given a user role, use PermissionMapping to get permissions for the role and return the list

```java
public class User implements UserDetails {
    ....
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        roles.forEach(
                role -> {
                    Set<SimpleGrantedAuthority> permissions = PermissionMapping.getAuthoritiesForRole(role);
                    authorities.addAll(permissions);
                    authorities.add(new SimpleGrantedAuthority("ROLE_"+role.name()));
                }
        );
        return authorities;
    }
```

### Drawbacks of this approach

- Permissions are tightly coupled with Roles
- Not optimal when you application starts growing beyond a certain point

---

# 6. Security Method Annotations - @Secured and @PreAuthorize

In a Spring Boot application, security method annotations such as @Secured and @PreAuthorize are used to enforce security constraints at the method level. These annotations define the roles or permissions required to access specific methods, providing fine-grained security controls for your application.

## **@Secured Annotation**
   - **Purpose:** Specifies security roles (authorities) required to invoke a method.
   - **Configuration:** It is a part of Spring Security and needs to be enabled by adding @EnableGlobalMethodSecurity(securedEnabled = true) in your configuration class.
   - **Usage:**
     - You annotate methods and specify one or more roles required to access them.
     - If the current user does not have the specified role(s), an AccessDeniedException is thrown.

```java
@Secured("ROLE_ADMIN")
public void adminOnlyMethod() {
    // Method accessible only by users with "ROLE_ADMIN"
}

@Secured({"ROLE_USER", "ROLE_MANAGER"})
public void userOrManagerMethod() {
    // Method accessible by users with "ROLE_USER" or "ROLE_MANAGER"
}
```

## Code implementation 

- Let's suppose we want user with `USER/ADMIN` role to view all posts. You can do that by declaring below annotation. 
- Please mind to add `@EnableMethodSecurity(securedEnabled = true)` in config class before using this.


```java
    @GetMapping
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public List<PostDTO> getAllPosts() {
        return postService.getAllPosts();
    }
```

Also, since we are delighting the control at method level, we don't need to define rules inside `WebSecurityConfig`.

```java
public class WebSecurityConfig {
    ...
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
                httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(publicRoutes).permitAll()
                        .requestMatchers("/posts/**").authenticated()
                        .anyRequest().authenticated())
                .csrf(csrfConfig -> csrfConfig.disable())
                ...
                );

        return httpSecurity.build();
    }
```

### Limitations:
- Only works with roles (not fine-grained expressions).
- not handle complex security logic or conditions.

---

## @PreAuthorize Annotation
   - **Purpose:** Allows more complex security expressions using Spring Expression Language (SpEL).
   - **Configuration:** Requires enabling with @EnableGlobalMethodSecurity(prePostEnabled = true) in the configuration class.
   - **Usage:**
     - Annotate methods with SpEL expressions to define security constraints.
     - Provides greater flexibility than @Secured.

```java
@PreAuthorize("hasRole('ROLE_ADMIN')")
public void adminOnlyMethod() {
    // Method accessible only by users with "ROLE_ADMIN"
}

@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_MANAGER')")
public void userOrManagerMethod() {
    // Method accessible by users with either "ROLE_USER" or "ROLE_MANAGER"
}

@PreAuthorize("hasAuthority('VIEW_REPORTS') and #id == authentication.principal.id")
public void viewReport(Long id) {
    // Method accessible if the user has "VIEW_REPORTS" authority and their ID matches the given ID
}
```

## Code implementation

1. Define security rule for Posts like shown below in `utils/PostSecurity.java` class.

```java
@Component
@RequiredArgsConstructor
public class PostSecurity {

    private  final PostService postService;

    public boolean isOwnerOfPost(Long postId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PostDTO post = postService.getPostById(postId);
        return post.getAuthor().getId().equals(user.getId());
    }
}
```

2. Add `author` field for Post in DTO and Entity classes.
3. Add necessary condition in PostController class.

```java
public class PostController {
    ...
    @GetMapping("/{postId}")
    @PreAuthorize("@postSecurity.isOwnerOfPost(#postId)")
    public PostDTO getPostById(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }
    ...
}
```

Now, only posts created by Author will be shown when we request posts by id.

### Advantages:
- Can use authorities and roles interchangeably.
- Allows logical operators (and, or, not).
- Supports dynamic expressions, e.g., checking user attributes or method arguments.