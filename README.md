# 📚 Library Management System API Documentation

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


## 📘 Book Control System

### ➕ Create Book
**POST** `/api/books/add`

**Request Body**
```json
{
  "booktitle": "1984",
  "authorname": "George Orwell",
  "isbn": "9780451524934",
  "categoryId": 1,
  "tagIds": [1, 2]
}
```

**Response**
```json
{
  "bookid": 1,
  "booktitle": "1984",
  "authorname": "George Orwell",
  "isbn": "9780451524934",
  "category": {
    "id": 1,
    "name": "Dystopian"
  },
  "tags": [
    { "id": 1, "name": "Classic" },
    { "id": 2, "name": "Political" }
  ]
}
```

---

### 🛠️ Update Book
**PUT** `/api/books/update/{id}`

**Request Body**
```json
{
  "booktitle": "1984 - Updated",
  "authorname": "George Orwell",
  "isbn": "9780451524934",
  "categoryId": 1,
  "tagIds": [1, 3]
}
```

---

### ❌ Delete Book
**DELETE** `/api/books/delete-by/{id}`

---

### 📖 Get All Books
**GET** `/api/books/get-all`

---

### 📖 Get Book By ID
**GET** `/api/books/{id}`

---

## 🏷️ Tag Endpoints

### ➕ Add Tag
**POST** `/api/tags/add-tag`

**Request Body**
```json
{
  "name": "Romantic"
}
```

**Response**
```json
{
  "id": 8,
  "name": "Romantic"
}
```

---

### 🛠️ Update Tag
**PUT** `/api/tags/{id}`

**Request Body**
```json
{
  "name": "Sci-Fi"
}
```

---

### ❌ Delete Tag
**DELETE** `/api/tags/{id}`

---

### 📖 Get All Tags
**GET** `/api/tags/get-all`

---

### 📖 Get Tag By ID
**GET** `/api/tags/{id}`

---

## 🗂️ Category Endpoints

### ➕ Create Category
**POST** `/api/categories/create`

**Request Body**
```json
{
  "name": "Mystery"
}
```

---

### 🛠️ Update Category
**PUT** `/api/categories/{id}`

**Request Body**
```json
{
  "name": "Historical Fiction"
}
```

---

### ❌ Delete Category
**DELETE** `/api/categories/{id}`

---

### 📖 Get All Categories
**GET** `/api/categories/get-all`

---

### 📖 Get Category By ID
**GET** `/api/categories/{id}`

---

## 🔄 Sample Book JSONs

### 📘 Book JSON Example
```json
{
  "booktitle": "Pride and Prejudice",
  "authorname": "Jane Austen",
  "isbn": "9781503290563",
  "categoryId": 2,
  "tagIds": [4, 5]
}
```

```json
{
  "booktitle": "The Notebook",
  "authorname": "Nicholas Sparks",
  "isbn": "9780446605236",
  "categoryId": 5,
  "tagIds": [8]
}
```
