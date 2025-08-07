# 🎟️ Event Booking API

This is a Spring Boot-based RESTful API where authenticated users can create events, register for events, and view their own created events and registrations.

---

## 🚀 Getting Started

### ✅ Prerequisites

Ensure you have the following installed:

- Java 17+
- Maven 3.8+
- PostgreSQL
- Git

---

### ⚙️ Installation

1. **Clone the repository**:

git clone https://github.com/human-codes/event-booking.git
cd event-booking

2.Configure your database and environment:

Open the application.properties file and set your database credentials and other configs:

spring.datasource.url=jdbc:postgresql://localhost:5432/eventbooking
spring.datasource.username=postgres
spring.datasource.password=root123
spring.datasource.driverClassName=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

spring.application.name=Event booking API

jwt.secret=mysupersecretkey0123456789012345

⚠️ Note: Make sure the database eventbooking exists in your PostgreSQL.

🧱 Technologies Used
Java 17

Spring Boot
Spring Security (JWT)
Spring Data JPA
PostgreSQL
Swagger (springdoc-openapi)
Maven

🔐 Authentication
All protected routes require a valid JWT token passed in the request header:

makefile
Copy
Edit
Authorization: Bearer <your_token_here>

<img width="1008" height="554" alt="image" src="https://github.com/user-attachments/assets/2b42cbd5-aff2-464e-b842-96f9ec5a8c52" />

📁 Project Structure
src
├── controller
├── dto
│   ├── request
│   └── response
├── entity
├── repository
├── service
│   └── impl
├── config
└── exception


if you want to check project using POSTMAN, you can import the file that I already have shared in project folder named >> OpenAPI definition.postman_collection.json
