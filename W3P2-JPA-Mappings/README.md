# 🚀 Coding Shuttle: Week 3 (Part 2) - Hibernate & Spring Boot Data JPA
Welcome to Week 3 of the Coding Shuttle course! In this week, we will cover the below-mentioned topics.

# 📚 Topics Covered in Week 3 (Part 2)
1. Data Mappings - 1:1, 
2. Data Mappings - 1:N, N:M

# One-to-One Mapping: 🔗 (Single link between two entities)
One-to-One mapping is a type of association in relational databases where one entity is directly related to exactly one other entity. In Spring Boot, this relationship is implemented using JPA annotations, such as @OneToOne.

## Key Characteristics
* **`Cardinality:`** Each record in one table is associated with exactly one record in another table.
* **`Foreign Key:`** A foreign key in one table references the primary key of another table to establish the relationship.

## Key Annotations
* **`@Entity:`** Marks the class as a persistent Java entity.
* **`@Id:`** Specifies the primary key for the entity.
* **`@GeneratedValue:`** Indicates that the primary key will be automatically generated.
* **`@OneToOne:`** Specifies the one-to-one relationship between Department and Employee.
* **`@JoinColumn:`** Defines the foreign key column in the Department table that references the Employee table.

## @JsonIgnore 
The @JsonIgnore annotation in Spring Boot (from the Jackson library) is used to exclude certain fields from being serialized (converted to JSON) or deserialized (parsed from JSON) when working with JSON data. It is particularly useful when you want to prevent sensitive or unnecessary data from being exposed in API responses or processed in incoming requests.

### Common Use Cases of @JsonIgnore
1. Hiding Sensitive Data
   You can use @JsonIgnore to prevent sensitive fields, such as passwords or confidential information, from being included in the JSON response.
```java
import com.fasterxml.jackson.annotation.JsonIgnore;

public class User {
    private Long id;
    private String username;

    @JsonIgnore
    private String password;

    // Getters and Setters
}
```
JSON Response:
```java
{
"id": 1,
"username": "johndoe"
}
```
2. Avoiding Circular References
   In bidirectional relationships (e.g., parent-child entities), @JsonIgnore can prevent infinite recursion during serialization.
```java
@Entity
public class Employee {
    @Id
    private Long id;
    private String name;

    @OneToOne(mappedBy = "manager")
    @JsonIgnore
    private DepartmentEntity managedDepartment;

    // Getters and Setters
}
```

3. Customizing API Output
   If a field is not relevant for the client, @JsonIgnore ensures that it won't clutter the JSON response.

## Explanation of Mapping
* The **`@OneToOne`** annotation in the Department entity establishes a one-to-one relationship with the Employee entity.
* The **`@JoinColumn`** annotation specifies the foreign key column (department_manager) in the Department table that references the id column in the Employee table.
* **`CascadeType.ALL`** ensures that any operation (e.g., persist, merge, remove) performed on a Department entity is cascaded to its associated Employee.

### Database Tables
When the application runs, Hibernate generates the following tables:

### Department Table
| id |    title    | department_manager |
|:--:|:-----------:|:------------------:|
| 1  | Engineering |        123         |
	 
### Employee Table
| id  | name |
|:---:|:----:|
| 123 | Anil |

The department_manager column in the Department table references the id column in the Employee table.

## One-to-Many Mapping: 🌳 (A tree where one root connects to many branches)
Explanation: A One-to-Many relationship means one entity is associated with multiple entities.

In our case, a DepartmentEntity can have multiple employees (workers), but each employee belongs to only one department.

### Implementation Details:

In DepartmentEntity:
* The mappedBy attribute points to the workerDepartment field in the EmployeeEntity to establish bidirectional mapping.
* @OneToMany(mappedBy = "workerDepartment", fetch = FetchType.LAZY) defines the relationship.

```java
@OneToMany(mappedBy = "workerDepartment", fetch = FetchType.LAZY)
private Set<EmployeeEntity> workers;
```

In EmployeeEntity:
* The @ManyToOne annotation maps the relationship on the other side.
* A JoinTable (or JoinColumn) is used to define the foreign key column linking an employee to their department.

```java
@ManyToOne(cascade = CascadeType.ALL)
@JoinTable(name = "worker_department_mapping")
@JsonIgnore
private DepartmentEntity workerDepartment;
```
Key Characteristics:
* The workers field in DepartmentEntity holds all employees in a department.
* The workerDepartment field in EmployeeEntity specifies which department an employee belongs to.

## Many-to-Many Mapping: 🔄 (A network with multiple interconnected nodes)

Explanation:
* A Many-to-Many relationship occurs when multiple entities of one type can be associated with multiple entities of another type.
* In our case, DepartmentEntity can have many freelance employees (freelancers), and each EmployeeEntity can work for multiple departments as a freelancer. 

### Implementation Details:

In DepartmentEntity:
* @ManyToMany(mappedBy = "freelanceDepartments") defines the relationship.
* The mappedBy attribute points to the freelanceDepartments field in EmployeeEntity.

```java
@ManyToMany(mappedBy = "freelanceDepartments")
private Set<EmployeeEntity> freelancers;
```

In EmployeeEntity:
* @ManyToMany defines the mapping with a JoinTable to establish the association table.
* The joinColumns attribute specifies the foreign key from the EmployeeEntity side, while inverseJoinColumns specifies the foreign key from the DepartmentEntity side.

```java
@ManyToMany
@JoinTable(name = "freelancer_department_mapping",
        joinColumns = @JoinColumn(name = "employee_id"),
        inverseJoinColumns = @JoinColumn(name = "department_id")
)
@JsonIgnore
private Set<DepartmentEntity> freelanceDepartments;
```

### Key Characteristics:
* A freelancer_department_mapping join table is created with:
  * employee_id: References EmployeeEntity.
  * department_id: References DepartmentEntity.