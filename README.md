# Spring Boot 3 JWT Security

A production-ready Spring Boot 3 application implementing JWT-based authentication and authorization with role-based access control.

## 🚀 Features

- **JWT Authentication**: Secure token-based authentication using JSON Web Tokens
- **User Registration & Login**: Complete user management with encrypted password storage
- **Access & Refresh Tokens**: Dual token system for enhanced security
- **Role-Based Authorization**: Fine-grained access control with custom permissions
- **Token Management**: Automatic token revocation on logout
- **Password Encryption**: BCrypt password hashing
- **JPA Auditing**: Automatic tracking of entity creation and modification
- **OpenAPI Documentation**: Interactive API documentation with Swagger UI
- **PostgreSQL Integration**: Production-grade database support
- **Docker Compose**: Easy setup with containerized PostgreSQL and pgAdmin

## 🛠️ Technologies

| Technology | Version | Purpose |
|------------|---------|---------|
| Spring Boot | 3.3.2 | Application framework |
| Spring Security | 6.x | Authentication & authorization |
| Spring Data JPA | 3.x | Database access |
| JWT (jjwt) | 0.11.5 | Token generation & validation |
| PostgreSQL | Latest | Database |
| Lombok | Latest | Code generation |
| SpringDoc OpenAPI | 2.1.0 | API documentation |
| Maven | 3+ | Build tool |
| Java | 17+ | Programming language |

## 📋 Prerequisites

- **JDK 17** or higher
- **Maven 3.6+**
- **Docker** (optional, for running PostgreSQL)
- **PostgreSQL 14+** (if not using Docker)

## 🔧 Installation & Setup

### 1. Clone the Repository

```bash
git clone https://github.com/ali-bouali/spring-boot-3-jwt-security.git
cd spring-boot-3-jwt-security
```

### 2. Start PostgreSQL (Docker)

The easiest way to get started is using Docker Compose:

```bash
docker-compose up -d
```

This will start:
- **PostgreSQL** on port `5432`
- **pgAdmin** on port `5050` (http://localhost:5050)
  - Email: `pgadmin4@pgadmin.org`
  - Password: `admin`

### 3. Configure Application (Optional)

The default configuration in `application.yml` should work out of the box with Docker Compose. If you need to customize:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/jwt_security
    username: postgres
    password: postgres

application:
  security:
    jwt:
      secret-key: your-secret-key-here
      expiration: 86400000        # 24 hours
      refresh-token:
        expiration: 604800000      # 7 days
```

### 4. Build the Project

```bash
mvn clean install
```

### 5. Run the Application

```bash
mvn spring-boot:run
```

Or run the JAR directly:

```bash
java -jar target/security-0.0.1-SNAPSHOT.jar
```

The application will start on **http://localhost:8080**

## 📚 API Documentation

Once the application is running, access the interactive API documentation:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

## 🔐 API Endpoints

### Authentication Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/v1/auth/register` | Register new user | No |
| POST | `/api/v1/auth/authenticate` | Login and get tokens | No |
| POST | `/api/v1/auth/refresh-token` | Get new access token | Yes (Refresh Token) |
| POST | `/api/v1/auth/logout` | Logout and revoke tokens | Yes |

### User Management

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| PATCH | `/api/v1/users` | Change user password | Yes |

### Book Management (Sample Resource)

| Method | Endpoint | Description | Required Role |
|--------|----------|-------------|---------------|
| GET | `/api/v1/books` | Get all books | Any authenticated user |
| POST | `/api/v1/books` | Create new book | Any authenticated user |

### Demo Endpoints (Role Testing)

| Method | Endpoint | Description | Required Role |
|--------|----------|-------------|---------------|
| GET | `/api/v1/demo-controller` | Public endpoint | Any authenticated user |
| GET | `/api/v1/admin/**` | Admin endpoints | ADMIN |
| GET | `/api/v1/management/**` | Management endpoints | MANAGER |

## 🔑 Usage Examples

### 1. Register a New User

```bash
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "firstname": "John",
    "lastname": "Doe",
    "email": "john.doe@example.com",
    "password": "password123",
    "role": "USER"
  }'
```

**Response:**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiJ9..."
}
```

### 2. Authenticate (Login)

```bash
curl -X POST http://localhost:8080/api/v1/auth/authenticate \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john.doe@example.com",
    "password": "password123"
  }'
```

**Response:**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiJ9..."
}
```

### 3. Access Protected Endpoint (Get All Books)

```bash
curl -X GET http://localhost:8080/api/v1/books \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..."
```

### 4. Create a New Book

```bash
curl -X POST http://localhost:8080/api/v1/books \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..." \
  -H "Content-Type: application/json" \
  -d '{
    "author": "J.K. Rowling",
    "isbn": "978-0-7475-3269-9"
  }'
```

### 5. Refresh Access Token

```bash
curl -X POST http://localhost:8080/api/v1/auth/refresh-token \
  -H "Authorization: Bearer <refresh_token>"
```

### 6. Change Password for current authenticated User

```bash
curl -X PATCH http://localhost:8080/api/v1/users \
  -H "Authorization: Bearer <access_token>" \
  -H "Content-Type: application/json" \
  -d '{
    "currentPassword": "password123",
    "newPassword": "newPassword456",
    "confirmationPassword": "newPassword456"
  }'
```

## 🔒 Security Implementation

### JWT Token Structure

The application uses two types of tokens:

1. **Access Token** (24 hours validity)
   - Used for API authentication
   - Contains user details and authorities
   - Short-lived for security

2. **Refresh Token** (7 days validity)
   - Used to obtain new access tokens
   - Stored in database for revocation
   - Longer-lived for better UX

### Token Management

- All valid tokens are stored in the database
- On logout, all user tokens are revoked
- On new login, previous tokens are invalidated
- Tokens are checked against the database on each request

### Role-Based Access Control

The application supports hierarchical roles with specific permissions:

**Roles:**
- `USER`: Basic user access
- `ADMIN`: Full system access
- `MANAGER`: Management-level access

**Permissions:**
- `ADMIN_READ`, `ADMIN_UPDATE`, `ADMIN_CREATE`, `ADMIN_DELETE`
- `MANAGER_READ`, `MANAGER_UPDATE`, `MANAGER_CREATE`, `MANAGER_DELETE`

### Password Security

- Passwords are hashed using BCrypt with strength 10
- Plain text passwords are never stored
- Password validation on change includes current password verification

## 🏗️ Architecture Highlights

### JWT Authentication Filter

Custom filter (`JwtAuthenticationFilter`) intercepts all requests to:
1. Extract JWT from Authorization header
2. Validate token signature and expiration
3. Load user details from the database
4. Set authentication in Security Context

### Database Schema

**Main Tables:**
- `usr`: Stores user information and credentials
- `token`: Stores JWT tokens with revocation status
- `book`: Sample resource table with auditing fields (created_date, last_modified_date, created_by, last_modified_by)

### Security Configuration

- Stateless session management (no server-side sessions)
- JWT-based authentication
- Method-level security with `@PreAuthorize`
- CORS configuration
- Custom logout handling

## 🧪 Testing

Run tests with:

```bash
mvn test
```

The project includes Spring Security Test support for testing secured endpoints.

## 🐛 Troubleshooting

### Database Connection Issues

If you can't connect to PostgreSQL:
1. Verify Docker containers are running: `docker ps`
2. Check PostgreSQL logs: `docker logs postgres-sql`
3. Ensure port 5432 is not in use by another service

### JWT Token Issues

If tokens are not working:
1. Verify the `secret-key` in `application.yml`
2. Check token expiration times
3. Ensure the token is sent in the Authorization header as `Bearer <token>`
4. Check database for revoked tokens

### Build Issues

If Maven build fails:
1. Ensure JDK 17+ is installed: `java -version`
2. Clear Maven cache: `mvn clean`
3. Update dependencies: `mvn clean install -U`

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👤 Author

Oleksii Morenets - [GitHub](https://github.com/o-morenets)

## 🤝 Contributing

Contributions, issues, and feature requests are welcome!

## ⭐ Show Your Support

Give a ⭐️ if this project helped you!
