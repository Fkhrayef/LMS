# Library Management System (LMS)

This repository is a practical learning journey into **Spring Boot**, following the Learning by Doing principle. The project is structured into weekly modules, each focusing on a specific topic, progressively building skills in Spring Boot development.

## Background

Before starting this project, I took the following courses to build a strong foundation in Spring Boot:
1. [Spring Boot for Beginners by Amigoscode](https://www.amigoscode.com/courses/spring-boot)  
   This course provided a comprehensive introduction to Spring Boot, covering key topics like REST APIs, dependency injection, and Spring Data JPA.  

2. [Spring 6 and Spring Boot Tutorial for Beginners by Telusko](https://youtube.com/playlist?list=PLsyeobzWxl7qbKoSgR5ub6jolI8-ocxCF&si=DswzPGXQiNmfLn24)  
   This tutorial series gave me additional insights into building robust applications with Spring Framework and Spring Boot.

---

## **Week 1: Spring Boot Essentials & Basic API Development**

### **Description:**
In this week, the focus was on setting up the Spring Boot environment and building a simple CRUD API. The goal was to understand the foundational concepts of Spring Boot, the basic structure of a Spring Boot application, and how to expose endpoints to handle CRUD operations.

### **Key Implementations:**
- **Spring Boot Setup:**
  - Created a Spring Boot project using Spring Initializr.
  - Set up basic dependencies such as **Spring Web** and **Lombok**.
  - Configured the `application.properties` to handle application settings.

- **CRUD API Development:**
  - Developed a basic API to handle `Book` data with CRUD operations.
  - Created a `BookController` to manage REST endpoints for creating, retrieving, updating, and deleting users.
  - Implemented a `BookService` to handle the business logic.

- **Testing:**
  - Verified CRUD operations by testing API endpoints using **Postman**.
  
### **Key Learnings:**
- Understanding Spring Boot's core architecture.
- Learned how to expose RESTful endpoints for CRUD operations.
- Gained basic knowledge of Spring Boot application configuration.

---

## **Week 2: Database Integration with PostgreSQL**

### **Description:**
The second week focused on integrating PostgreSQL into the Spring Boot application. The goal was to connect the Spring Boot project to a relational database and learn how to interact with it using Spring Data JPA.

### **Key Implementations:**
- **PostgreSQL Setup:**
  - Installed and configured PostgreSQL to work with the Spring Boot application.
  - Configured the `application.properties` file to establish a connection to PostgreSQL.
  - Used **Spring Data JPA** for database integration.

- **Database Integration:**
  - Implemented entity classes, such as `Book` and `Author`, to map to database tables.
  - Mapped Author to Book using 1:M relationship.
  - Set up a `BookRepository` to to manage interactions with the database on the `Book` table.

- **Testing:**
  - Tested the database CRUD operations to ensure that data was being saved, updated, and retrieved correctly.

### **Key Learnings:**
- Gained experience in connecting Spring Boot applications to a relational database (PostgreSQL).
- Learned how to use **Spring Data JPA** for object-relational mapping (ORM).
- Understood how to configure database properties and perform database migrations.
- Learned how to implement relations (1:1, 1:M, M:M)

---

## **Week 3: Data Validation and Error Handling**

### **Description:**
Week 3's focus was on validating data received in API requests and handling errors. This week aimed to ensure that the API only accepts valid data and provides meaningful error messages to users when something goes wrong.

### **Key Implementations:**
- **Data Validation:**
  - Used **Java Bean Validation API** annotations (e.g., `@NotNull`, `@Email`, `@Size`) for validating incoming data.
  - Applied validation to the `BookDto` and `AuthorDto` to ensure that user data adheres to the required format.

- **Error Handling:**
  - Implemented global exception handling using `@ControllerAdvice`.
  - Created custom exceptions to return appropriate HTTP status codes and error messages.
  - Developed clear error messages for validation errors and unexpected exceptions.

- **Testing:**
  - Validated the correct handling of invalid data through **Postman**.
  - Ensured that the application returned proper error messages when invalid data was submitted.

### **Key Learnings:**
- Learned how to implement data validation in Spring Boot using the **Java Bean Validation API**.
- Understood the importance of clear and structured error handling.
- Gained knowledge in customizing error responses to enhance the user experience.

---

## **Week 4: Security and Authentication**

### **Description:**
This week was focused on implementing **security** and **authentication** mechanisms to secure the API. The goal was to protect endpoints and ensure that users are authenticated via JWT (JSON Web Tokens).
Note: I also learnt Basic Authentication and In Memory Authentication Provider prior to JWT.

### **Key Implementations:**
- **Spring Security Setup:**
  - Integrated **Spring Security** to handle user authentication and authorization.
  - Configured basic Spring Security for login and signup functionality.
  
- **JWT Authentication:**
  - Implemented JWT-based authentication for secure login.
  - Created the `JwtUtils` class for generating and validating JWT tokens.
  - Set up endpoints (`/signin`, `/signup`) to handle user authentication via JWT.

- **User Management:**
  - Created the `User` entity, which holds user data including roles.
  - Set up user roles (e.g., `USER`, `ADMIN`) for authorization purposes.
  - Implemented a service to load user details and authenticate using JWT.

- **Testing:**
  - Validated the JWT authentication system using **Postman**.
  - Tested endpoints with valid and invalid tokens to ensure the correct handling of authorized and unauthorized users.

### **Key Learnings:**
- Understood the filter chain and implemented custom filters.
- Gained hands-on experience with **Spring Security** and **JWT**.
- Learned how to secure an API and manage user roles and permissions.
- Understood how to generate and validate JWT tokens for stateless authentication.

### **Resources:**
- [Spring Security 6 with ReactJS, OAuth2, JWT | Spring Boot](https://www.udemy.com/course/spring-security-6-with-reactjs-oauth2-jwt-multifactor-authentication/) by Faisal Memon. You can find part of the course for free [here](https://youtu.be/Kzx8MKA7Q0Y?si=OOfZHqsOv4ei6Uho)
  
