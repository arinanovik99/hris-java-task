# HRIS System

HRIS (Human Resource Information System) is a system designed to manage departments and their employees. The project follows a microservices architecture with two main services: `employees` and `departments`.

## Services

- **Employees**: Manages employee data and is backed by a PostgreSQL database.
- **Departments**: Manages department data and is backed by a MongoDB database.

## Technology Stack

- **General**: Java 17, Spring Boot, Gradle, ELK Stack (Elasticsearch, Logstash, Kibana)
- **Employees Service**: PostgreSQL
- **Departments Service**: MongoDB
- **Documentation**: Swagger is integrated for API documentation.
- **Logging**: ELK Stack is used for centralized logging.

## Getting Started

### Prerequisites

- Java 17
- Docker and Docker Compose

### Installation

1. Clone the repository:
    ```bash
    git clone <repository-url>
    cd <repository-directory>
    ```

2. Build the services using Gradle:
    ```bash
   ./gradlew build
    ```

3. Start the databases and ELK stack using Docker Compose:
    ```bash
    docker-compose up
    ```

### API Documentation

Swagger is integrated for API documentation. After running the services, you can access the Swagger UI at:

- Employees service: `http://localhost:<employees-port>/swagger-ui.html`
- Departments service: `http://localhost:<departments-port>/swagger-ui.html`

### Logging

The ELK stack is used for centralized logging. Access Kibana at `http://localhost:5601` to view and analyze logs.
