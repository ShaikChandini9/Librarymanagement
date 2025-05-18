
# 📚 Library Management System

A comprehensive and secure Library Management System built using **Spring Boot 3.4.5** and **Java 21**. Designed to support full-scale library operations, this project demonstrates modular architecture, layered design, robust security, and advanced feature integration suitable for enterprise-grade systems.

---

## 🚀 Core Features & Implementation Details

### 🔐 Authentication & Authorization
- **JWT Token-Based Security**: Implemented a stateless authentication mechanism using JSON Web Tokens (JWT). Tokens are generated upon login and used for every secure API call.
- **Role-Based Access Control**: Defined roles (Admin, Librarian, Member) with fine-grained access permissions to control access at API level.
- **Custom UserDetailsService**: Used to fetch and validate user credentials during authentication.
- **Spring Security Integration**: Integrated with `SecurityConfig` and `JwtTokenProvider` to secure endpoints and manage sessions without server state.

### 📚 Book Management
- **Entity Design**: Books are mapped with fields like title, author, ISBN, language, publication year, and availability.
- **CRUD Operations**: Implemented full Create, Read, Update, and Delete functionality for books.
- **Associations**: Each book is associated with categories and tags for better classification and searchability.

### 🗂️ Category & Tag Management
- **Category Management**: CRUD support for book genres or classifications such as Fiction, Non-fiction, Science, etc.
- **Tagging System**: Tags help in flexible grouping beyond strict categories, allowing multi-dimensional search and filters.
- **Integrated Relationships**: Categories and tags are related to books via entity relationships, ensuring referential integrity and clean design.

### 🔍 Advanced Book Search & Filter
- **Dedicated BookSearchService**: Created a separate service to handle all search and filter logic, isolating it from the core BookService for maintainability.
- **Search Parameters**:
  - Book Title
  - Author Name
  - Category/Genre
  - Availability (In Stock/Out)
  - Language
  - Year of Publication
- **Dynamic Queries**: Designed flexible repository-level queries using Spring Data JPA Specifications or QueryDSL (optional) to support multi-parameter filtering.

### 📖 Borrow & Return System
- **Borrow Entity Design**: Maintains records of borrow actions with fields such as borrowed date, due date, and return date.
- **Issue Book Flow**: Validates availability, assigns due date, updates inventory, and logs the transaction.
- **Return Book Flow**: Updates return status, checks for overdue status, and restores availability.
- **Audit Trails**: Maintains user-wise history of borrow and return operations.

### 📌 Reservation System
- **Reservation Entity**: Keeps track of reserved books when they are currently unavailable.
- **Queue-Based Logic**: Maintains order of reservations and allows users to be served on a first-come, first-served basis.
- **Availability Notification Logic**: Designed a placeholder mechanism to notify users when a reserved book becomes available.

---

## 🧰 Technology Stack

| Technology         | Description                                     |
|--------------------|-------------------------------------------------|
| **Java 21**         | Modern Java language features and performance  |
| **Spring Boot 3.4.5** | Rapid backend development and configuration  |
| **Spring Security** | Role-based access and secure token validation |
| **JWT**             | Stateless authentication mechanism             |
| **Hibernate / JPA** | ORM and persistence abstraction                 |
| **PostgreSQL / H2** | Primary and in-memory databases                 |
| **Maven**           | Dependency and build management                |
| **Lombok**          | Boilerplate code elimination                   |
| **ModelMapper**     | DTO conversion for clean API contracts         |

---

## 🧱 Project Architecture

The application is built using a layered and modular approach:

```
src/
├── config/                # JWT, Security, CORS configurations
├── controller/            # REST API endpoints for all modules
├── dto/                   # Data Transfer Objects for requests and responses
├── entity/                # JPA entity definitions: Book, Category, Tag, User, Borrow, Reservation
├── repository/            # Spring Data JPA Repositories
├── service/               
│   ├── impl/              # Business logic layer implementations
│   ├── BookSearchService  # Independent service for search/filter operations
├── exception/             # Global and custom exception handling
└── LibraryManagementApp   # Spring Boot Application entry point
```

---

## 🧪 Running the Application

### 1. Clone the Repository
```bash
git clone https://github.com/your-username/library-management-system.git
cd library-management-system
```

### 2. Configure Properties
Update `application.properties` or `application.yml` with:
- Database configuration (PostgreSQL or H2)
- JWT Secret and Expiration settings

### 3. Run the Application
```bash
./mvnw spring-boot:run
```

### 4. Test the Endpoints
Use Postman or Swagger UI (if configured) to access secured and unsecured endpoints.

---

## 🔐 User Roles Overview

| Role       | Capabilities                                                                 |
|------------|------------------------------------------------------------------------------|
| **Admin**  | Manage users, books, categories, tags, and system settings                   |
| **Librarian** | Handle borrow/return operations, manage books and reservations            |
| **Member** | Search books, borrow, return, and reserve books                              |

---

## 📌 Future Enhancements

- Email notifications for reserved book availability
- Overdue fine calculation based on return dates
- Admin dashboard with usage analytics
- External metadata integration from Google Books API
- Swagger/OpenAPI for interactive API documentation
- Unit and Integration test coverage with JUnit and Mockito
