# Library Management 

A simple RESTful backend built with **Spring Boot 3.x** and **Java 21** that provides JWT-based authentication and role-based access for Admin, Librarian, and Member users.

## Features

- JWT authentication
- Role-based authorization (Admin, Librarian, Member)
- Register and login endpoints
- Secure REST APIs for adding users
- Extensible architecture for book and transaction modules

## Technologies

- Java 21
- Spring Boot 3.4.x
- Spring Security 6.x
- JWT (Nimbus JOSE + JWT)
- MySQL
- Maven

## Getting Started

### Prerequisites

- Java 21
- Maven
- MySQL

### Clone the Repository

```bash
git clone https://github.com/your-username/librarymanagement.git
cd librarymanagement
```

### Configure the Database

Update your `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/librarydb
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### Run the Application

```bash
mvn spring-boot:run
```

### Default Server URL

```
http://localhost:8081
```

## API Endpoints

### Auth

| Method | Endpoint               | Description           | Access |
|--------|------------------------|-----------------------|--------|
| POST   | `/api/users/register`  | Register new user     | Public |
| POST   | `/api/users/login`     | Login and get token   | Public |

### Users

| Method | Endpoint               | Description           | Role   |
|--------|------------------------|-----------------------|--------|
| POST   | `/api/users/add-user`  | Add user (Admin only) | ADMIN  |

### JSON Example: Register

```json
{
  "username": "admin",
  "password": "admin123",
  "role": "ADMIN"
}
```

### JSON Example: Login

```json
{
  "username": "admin",
  "password": "admin123"
}
```

### JSON Example: Add User

```json
{
  "username": "librarian1",
  "password": "password123",
  "role": "LIBRARIAN"
}
```

> Set `Authorization: Bearer <token>` in header for protected APIs.

## JWT Configuration

The secret key must be at least 256 bits:

```java
private static final String SECRET_KEY = "mysupersecretkeythatisverysecure123456";
```

## Common Issues

- 403: Ensure `/register` and `/login` are public and CSRF is disabled.
- 401: Ensure correct token is sent in headers.
- Token generation error: Use a secret with at least 256 bits.

## License

This project is licensed under the MIT License.

