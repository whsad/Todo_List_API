# Todo List  API

## Project Overview

**Todo_List_API** is a RESTful web application that enables users to manage their to-do lists. It includes functionality for user registration, login, and CRUD operations on to-do items. This project demonstrates user authentication, database integration, and secure API design using Spring Boot, Spring Security, and Redis.

## Environment Requirements

- Java 1.8 or higher
- Maven 3.8 or higher
- Redis 6.0 or higher

## Compilation and Running

1. Clone the project repository:

   ```bash
   git clone https://github.com/whsad/Todo_List_API.git
   cd Todo_List_API
   ```

2. Compile the project using Maven:

   ```bash
   mvn clean install
   ```

3. Run the application:

   ```bash
   mvn spring-boot:run
   ```

4. Start the Redis server (if not already running):

   ```bash
   redis-server
   ```

5. Access the API using a tool like Postman or cURL. The base URL is:
    `http://localhost:8080`

## Specific Features

### User Registration

- **Endpoint**: `POST /register`

- Request Body:

  ```json
  {
    "name": "John Doe",
    "email": "john@doe.com",
    "password": "password"
  }
  ```

- Response:

  ```json
  {
    "token": "<JWT Token>"
  }
  ```

- Passwords are hashed using BCrypt before being stored in the database.

### User Login

- **Endpoint**: `POST /login`

- Request Body:

  ```json
  {
    "email": "john@doe.com",
    "password": "password"
  }
  ```

- Response:

  ```json
  {
    "token": "<JWT Token>"
  }
  ```

- Authenticated users receive a JWT for subsequent API requests.

### Create To-Do Item

- **Endpoint**: `POST /todos`

- Request Headers:

  ```text
  Authorization: Bearer <JWT Token>
  ```

- Request Body:

  ```json
  {
    "title": "Buy groceries",
    "description": "Buy milk, eggs, and bread"
  }
  ```

- Response:

  ```json
  {
    "id": 1,
    "title": "Buy groceries",
    "description": "Buy milk, eggs, and bread"
  }
  ```

### Update To-Do Item

- **Endpoint**: `PUT /todos/{id}`

- Request Headers

  :

  ```text
  Authorization: Bearer <JWT Token>
  ```

- Request Body:

  ```json
  {
    "title": "Buy groceries",
    "description": "Buy milk, eggs, bread, and cheese"
  }
  ```

- Response:

  ```json
  {
    "id": 1,
    "title": "Buy groceries",
    "description": "Buy milk, eggs, bread, and cheese"
  }
  ```

- Users can only update their own to-do items. Unauthorized updates return a 403 error.

### Delete To-Do Item

- **Endpoint**: `DELETE /todos/{id}`

- Request Headers:

  ```text
  Authorization: Bearer <JWT Token>
  ```

- **Response**: Status code: 204 (No Content)

### Get To-Do Items

- **Endpoint**: `GET /todos?page=1&limit=10`

- Request Headers:

  ```text
  Authorization: Bearer <JWT Token>
  ```

- Response:

  ```json
  {
    "data": [
      {
        "id": 1,
        "title": "Buy groceries",
        "description": "Buy milk, eggs, bread"
      },
      {
        "id": 2,
        "title": "Pay bills",
        "description": "Pay electricity and water bills"
      }
    ],
    "page": 1,
    "limit": 10,
    "total": 2
  }
  ```

## Notes

- Before running, modify the `application-pro.properties` configuration file according to your environment.
- The Sql file is in the resources directory

This is a solution to a project challenge in [roadmap.sh](https://roadmap.sh/projects/todo-list-api).
