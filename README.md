# Delivery Backend API

This project provides a robust backend API for managing delivery services, including client management, delivery tracking, and user authentication. Built with Spring Boot, it offers a secure and scalable solution for logistics operations.

## Features

-   **Client Management**: CRUD operations for client entities.
-   **Delivery Management**: Create and retrieve delivery records.
-   **User Authentication**: Secure user registration and login using JSON Web Tokens (JWT).
-   **Data Persistence**: Uses Spring Data JPA for efficient data storage and retrieval.
-   **H2 Database**: In-memory database for easy local development and testing.

## Technologies Used

-   **Java**: Version 17
-   **Spring Boot**:
    -   `spring-boot-starter-web`: For building RESTful APIs.
    -   `spring-boot-starter-data-jpa`: For database interaction.
    -   `spring-boot-starter-security`: For authentication and authorization.
    -   `spring-boot-starter-test`: For testing.
-   **Maven**: Dependency management and build automation.
-   **H2 Database**: Lightweight, in-memory relational database.
-   **JJWT**: JSON Web Token implementation for Java.

## Setup and Installation

To get this project up and running on your local machine, follow these steps:

### Prerequisites

-   Java Development Kit (JDK) 17 or newer
-   Apache Maven 3.6 or newer

### Steps

1.  **Clone the repository:**
    ```bash
    git clone <repository-url>
    cd delivery-backend
    ```
2.  **Build the project:**
    ```bash
    mvn clean install
    ```
    This command compiles the code, runs tests, and packages the application into a JAR file.

## How to Run

You can run the application in a couple of ways:

1.  **Using Maven Spring Boot Plugin:**
    ```bash
    mvn spring-boot:run
    ```
    This will start the application directly.

2.  **Running the JAR file:**
    After building the project, navigate to the `target` directory and run the JAR file:
    ```bash
        java -jar target/delivery-backend-0.0.1-SNAPSHOT.jar
    ```
    
    The application will start on `http://localhost:8080` by default.
    
    3.  **Using Docker:**
        To build and run the application using Docker, follow these steps:
    
        a.  **Build the Docker image:**
            ```bash
            docker build -t delivery-backend .
            ```
    
        b.  **Run the Docker container:**
            ```bash
            docker run -p 8080:8080 delivery-backend
            ```
            The application will be accessible at `http://localhost:8080`.
    
## API Endpoints

The API provides the following main endpoints:

### User Management (`/api/users`)

-   `POST /api/users/register`: Register a new user.
    -   **Request Body**:
        ```json
        {
          "username": "newuser",
          "password": "password123"
        }
        ```
    -   **Response**: Returns the registered user details.
-   `POST /api/users/authenticate`: Authenticate a user and receive a JWT token.
    -   **Request Body**:
        ```json
        {
          "username": "existinguser",
          "password": "password123"
        }
        ```
    -   **Response**:
        ```json
        {
          "jwt": "your-jwt-token-here"
        }
        ```

### Client Management (`/api/clients`)

*Authentication required for all client endpoints.*

-   `GET /api/clients`: Retrieve a list of all clients.
    -   **Response**: An array of client objects.
-   `POST /api/clients`: Create a new client.
    -   **Request Body**:
        ```json
        {
          "name": "Client Name",
          "address": "Client Address"
        }
        ```
    -   **Response**: The newly created client object.
-   `PUT /api/clients/{id}`: Update an existing client by ID.
    -   **Request Body**:
        ```json
        {
          "name": "Updated Client Name",
          "address": "Updated Client Address"
        }
        ```
    -   **Response**: The updated client object.
-   `DELETE /api/clients/{id}`: Delete a client by ID.
    -   **Response**: No content (204).

### Delivery Management (`/api/deliveries`)

*Authentication required for all delivery endpoints.*

-   `GET /api/deliveries`: Retrieve a list of all deliveries.
    -   **Response**: An array of delivery objects.
-   `POST /api/deliveries`: Create a new delivery.
    -   **Request Body**:
        ```json
        {
          "client_id": 1,
          "pickup_location_latitude": 34.0522,
          "pickup_location_longitude": -118.2437,
          "delivery_location_latitude": 34.0000,
          "delivery_location_longitude": -118.0000,
          "status": "PENDING"
        }
        ```
    -   **Response**: The newly created delivery object.