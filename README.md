# Contact Manager API

## Overview
The **Contact Manager API** is a Spring Boot-based backend application designed to manage users and their contacts. It provides features such as user authentication, role-based access control, contact management, and image uploads using Cloudinary. The API is secured with JWT (JSON Web Tokens) and supports CRUD operations for users and contacts.

---

## Features
- **User Management**:
  - Create, update, delete, and retrieve user details.
  - Role-based access control (e.g., Admin, User).
  - Password encryption using BCrypt.
  - JWT-based authentication and authorization.

- **Contact Management**:
  - Create, update, delete, and retrieve contacts.
  - Associate contacts with specific users.
  - Upload contact images to Cloudinary.

- **Image Upload**:
  - Upload user and contact images to Cloudinary.
  - Generate transformed image URLs for avatars.

- **Security**:
  - JWT-based authentication and token validation.
  - Role-based access control for endpoints.
  - Custom authentication entry points for unauthorized access.

---

## Technologies Used
- **Backend**: Spring Boot 3.4.4
- **Database**: MySQL
- **Security**: Spring Security, JWT
- **Image Upload**: Cloudinary
- **Build Tool**: Maven
- **Language**: Java 21

---

## Installation and Setup

### Prerequisites
- Java 21 or higher
- Maven
- MySQL
- Cloudinary account (for image uploads)

### Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/your-repo/contact_manager_api.git
   cd contact_manager_api
   ```

2. Configure the database:
   - Update the `application.properties` file with your MySQL credentials:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/contact_manager
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     ```

3. Configure Cloudinary:
   - Add your Cloudinary credentials to the `application.properties` file:
     ```properties
     config.cloudinary.cloud.cloudinaryName=your_cloudinary_name
     config.cloudinary.cloud.key=your_cloudinary_api_key
     config.cloudinary.cloud.secret=your_cloudinary_api_secret
     ```

4. Build and run the application:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

5. Access the API at:
   ```
   http://localhost:8080/api/v1
   ```

---

## API Endpoints

### User Endpoints
- **Create User**: `POST /api/v1/users`
- **Get All Users**: `GET /api/v1/users`
- **Get User by ID**: `GET /api/v1/users/{userId}`
- **Update User**: `POST /api/v1/users/{userId}`
- **Delete User**: `DELETE /api/v1/users/{userId}`

### Contact Endpoints
- **Create Contact**: `POST /api/v1/contacts`
- **Get All Contacts**: `GET /api/v1/contacts`
- **Get Contact by ID**: `GET /api/v1/contacts/{contactId}`
- **Update Contact**: `POST /api/v1/contacts/{contactId}`
- **Delete Contact**: `DELETE /api/v1/contacts/{contactId}`

### Authentication Endpoints
- **Login**: `POST /auth/login`
- **Refresh Token**: `POST /auth/refresh-token`

### Image Upload Endpoints
- **Upload User Image**: `POST /api/v1/users/{userId}/upload`
- **Upload Contact Image**: `POST /api/v1/contacts/{contactId}/upload`

---

## Database Schema
### Tables
1. **Users**
   - `id`: Primary key (UUID)
   - `name`, `email`, `password`, `phoneNumber`, etc.

2. **Contacts**
   - `id`: Primary key (UUID)
   - `name`, `email`, `phoneNumber`, `user_id` (foreign key), etc.

3. **Roles**
   - `id`: Primary key
   - `role`: Role name (e.g., `ROLE_ADMIN`, `ROLE_USER`)

4. **User_Roles**
   - `user_id`: Foreign key
   - `role_id`: Foreign key

---

## Security
- **JWT Authentication**:
  - Access tokens expire in 15 minutes.
  - Refresh tokens expire in 48 hours.
- **Role-Based Access Control**:
  - Admins can create roles and manage users.
  - Users can manage their own contacts.

---

## Known Issues
- Any user can upload an image for another user if they know the user ID.
- Old images are not deleted from Cloudinary when updated.

---

## Future Enhancements
- Add stricter access control for image uploads.
- Implement soft delete for users and contacts.
- Add pagination and sorting for user and contact lists.

---

## Project Structure
The project follows a standard Maven-based structure:

```
scm_backend/
├── .env                      # Environment variables (not included in version control)
├── endpoints.txt             # API endpoint details
├── HELP.md                   # Additional help documentation
├── mvnw, mvnw.cmd            # Maven wrapper scripts
├── pom.xml                   # Maven project configuration
├── scm_backend.sql           # Database schema
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── scmbackend/
│   │   │           ├── ScmBackendApplication.java       # Main application class
│   │   │           ├── configuration/                  # Configuration files
│   │   │           ├── controllers/                    # REST controllers
│   │   │           ├── dto/                            # Data Transfer Objects
│   │   │           ├── entities/                       # JPA entities
│   │   │           ├── events/                         # Event handlers
│   │   │           ├── exceptions/                     # Custom exceptions
│   │   │           ├── payloads/                       # Request/response payloads
│   │   │           ├── repositories/                   # Spring Data JPA repositories
│   │   │           ├── services/                       # Service layer
│   │   │           └── utilities/                      # Utility classes
│   │   └── resources/
│   │       ├── application.properties                  # Application configuration
│   │       ├── static/                                 # Static resources
│   │       └── templates/                              # Template files
│   └── test/
│       └── java/
│           └── com/
│               └── scmbackend/
│                   └── ScmBackendApplicationTests.java # Test class
└── target/                   # Compiled bytecode and build artifacts
```

---