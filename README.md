<<<<<<< HEAD
# DevConnect – Developer Collaboration Platform

DevConnect is a backend-focused developer collaboration platform built with Java and Spring Boot. It aims to help development teams manage projects, organize tasks, assign responsibilities, and track project progress through REST APIs.

## Tech Stack

- **Language:** Java 21
- **Backend:** Spring Boot 3.5.6
- **Database:** PostgreSQL 18
- **ORM:** Spring Data JPA, Hibernate
- **Security:** Spring Security, JWT
- **Build Tool:** Maven
- **API Testing:** Postman
- **Testing:** JUnit 5, Mockito
- **Containerization:** Docker
- **Version Control:** Git and GitHub

## Planned Features

- User registration and login
- JWT-based authentication
- Project creation and management
- Project member management
- Task creation and assignment
- Task status management
- Project progress tracking
- Role-based authorization
- REST API documentation
- Automated testing
- Docker-based deployment

*Feature availability depends on implementation and testing status.*

## Project Structure

```text
devconnect/
├── src/
│   ├── main/
│   │   ├── java/com/ranajeet/devconnect/
│   │   │   ├── config/
│   │   │   ├── controller/
│   │   │   ├── dto/
│   │   │   ├── entity/
│   │   │   ├── exception/
│   │   │   ├── repository/
│   │   │   ├── security/
│   │   │   ├── service/
│   │   │   └── DevConnectApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/ranajeet/devconnect/
├── Dockerfile
├── .gitignore
├── pom.xml
└── README.md
```

## Prerequisites

Install the following:

- Java JDK 21
- Apache Maven 3.9+
- PostgreSQL 18
- Git
- Docker (optional)
- Postman (for API testing)

## Database Configuration

Create a PostgreSQL database:

```sql
CREATE DATABASE devconnect_db;
```

Configure your database and JWT settings using environment variables or local configuration:

```properties
spring.datasource.url=${SPRING_DATASOURCE_URL}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}

app.jwt.secret=${APP_JWT_SECRET}
```

Never commit real database passwords or JWT secrets.

## Run Locally

Clone the repository:

```bash
git clone YOUR_GITHUB_REPOSITORY_URL
cd devconnect
```

Compile the application:

```bash
mvn clean compile
```

Run tests:

```bash
mvn test
```

Start the application:

```bash
mvn spring-boot:run
```

The backend runs at:

```text
http://localhost:8080
```

## Docker

Build the Docker image:

```bash
docker build -t devconnect:1.0 .
```

Run the container with your database and JWT environment variables configured:

```bash
docker run -p 8080:8080 devconnect:1.0
```

## API Testing

Use Postman to test the REST API endpoints.

Set the request header:

```text
Content-Type: application/json
```

For protected endpoints, provide:

```text
Authorization: Bearer YOUR_JWT_TOKEN
```

## Future Improvements

- WebSocket-based real-time notifications
- Swagger/OpenAPI documentation
- Docker Compose
- CI/CD using GitHub Actions
- Cloud deployment on Google Cloud Platform
- Additional unit and integration tests

## Author

**Ranajeet Bera**

- GitHub: https://github.com/Ranajeet2005
- LinkedIn: https://www.linkedin.com/in/ranajeet-bera-50a066294

---

*DevConnect is an educational software engineering project focused on backend development, REST APIs, database management, authentication, and application deployment.*
=======
# devconnect
>>>>>>> 8fc8d35f57853883887f6e3d5d63861e16a29296
