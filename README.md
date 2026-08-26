# 🎬 ShowTime Backend

A scalable backend system for a movie ticket booking platform inspired by BookMyShow.
This project provides RESTful APIs for managing movies, theaters, shows, and ticket bookings.

The backend also implements **JWT-based authentication and authorization** using **Spring Security**, providing secure access to protected APIs and role-based access control.

---

## 🚀 Features

* 🏙️ **City Management** — Manage cities across India
* 🎬 **Movie Catalog** — Full movie listing with genre, language, rating, poster
* 🏛️ **Theater Management** — Multiple theaters per city
* 🎥 **Screen Management** — Multiple screens per theater (4DX, IMAX, Dolby Atmos)
* 💺 **Seat Management** — REGULAR / PREMIUM / VIP seat types
* 🎟️ **Show Scheduling** — Multiple shows per day per screen
* 👤 **User Registration & Login**
* 🔐 **JWT Authentication** — Secure stateless authentication using JSON Web Tokens
* 🛡️ **Role-Based Authorization** — USER and ADMIN roles
* 🔑 **BCrypt Password Hashing** — Passwords are securely hashed before storing
* 📱 **Booking System** — Book multiple seats in one booking
* ❌ **Booking Cancellation**
* ✅ **Available Seats API** — Real-time seat availability per show
* 🔒 **Protected APIs** — Authentication required for private operations
* 👥 **Booking Ownership** — Users can access only their own bookings
* 👨‍💼 **Admin Authorization** — Administrative APIs are restricted to ADMIN users

---

# 🔐 JWT Authentication

ShowTime uses **JSON Web Token (JWT)** authentication with **Spring Security**.

JWT provides a stateless authentication mechanism where the server does not need to maintain a traditional login session.

### Authentication Flow

```text
User
 │
 │ Email + Password
 ▼
POST /api/auth/login
 │
 ▼
AuthenticationManager
 │
 ▼
UserDetailsService
 │
 ▼
UserRepository
 │
 ▼
MySQL
 │
 ▼
Password Verification
 │
 ▼
JWT Token Generated
 │
 ▼
Client
```

For subsequent protected requests:

```text
Client
 │
 │ Authorization: Bearer <JWT>
 ▼
JwtAuthenticationFilter
 │
 ▼
Validate JWT
 │
 ▼
Extract User Information
 │
 ▼
SecurityContext
 │
 ▼
Protected Controller
```

---

## 🔑 JWT Token

After successful login, the backend generates a JWT token.

Example:

```json
{
  "success": true,
  "message": "Login successful",
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "user": {
    "id": 1,
    "name": "Aabid",
    "email": "aabid@gmail.com",
    "role": "USER"
  }
}
```

The token must be sent with protected API requests using the `Authorization` header:

```http
Authorization: Bearer <JWT_TOKEN>
```

### JWT Structure

A JWT consists of three parts:

```text
HEADER.PAYLOAD.SIGNATURE
```

Example:

```text
eyJhbGciOiJIUzI1NiJ9
.
eyJzdWIiOiJhYWJpZEBnbWFpbC5jb20i...
.
SflKxwRJSMeKKF2QT4fwpMeJf36POk6...
```

The JWT contains only necessary authentication information and **never contains the user's password**.

---

# 🛡️ Authorization & Roles

ShowTime supports two roles:

```text
USER
ADMIN
```

### 👤 USER

A normal user can:

* Browse movies
* View theaters
* View shows
* View available seats
* Book tickets
* View their own bookings
* Cancel their own eligible bookings

### 👨‍💼 ADMIN

An administrator can manage:

* Movies
* Cities
* Theaters
* Screens
* Seats
* Shows
* Bookings

Administrative APIs are protected using:

```text
ROLE_ADMIN
```

A normal USER cannot access ADMIN-only APIs.

---

# 🔒 Password Security

ShowTime uses:

```text
BCryptPasswordEncoder
```

Passwords are never stored in plain text.

The registration process works as:

```text
Plain Password
      │
      ▼
BCryptPasswordEncoder
      │
      ▼
Encrypted/Hashed Password
      │
      ▼
MySQL
```

For example, instead of storing:

```text
123456
```

the database stores a BCrypt hash similar to:

```text
$2a$10$xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```

Passwords are also never returned in API responses.

---

## 🛠️ Tech Stack

### Backend

* **Java**
* **Spring Boot**
* **Spring MVC**
* **Spring Data JPA**
* **Spring Security**
* **Hibernate**
* **JWT**
* **BCrypt**
* **MySQL**
* **Maven**
* **Lombok**
* **Postman**

### Frontend

* HTML5
* CSS3
* JavaScript (Vanilla JS)

---

# 📁 Project Structure

```text
ShowTime/
├── src/
│   └── main/
│       ├── java/com/cfs/ShowTime/
│       │   ├── ShowTimeApplication.java
│       │   │
│       │   ├── Config/
│       │   │   ├── CorsConfig.java
│       │   │   └── SecurityConfig.java
│       │   │
│       │   ├── Controller/
│       │   │   ├── BookingController.java
│       │   │   ├── CityController.java
│       │   │   ├── MovieController.java
│       │   │   ├── ScreenController.java
│       │   │   ├── SeatController.java
│       │   │   ├── ShowController.java
│       │   │   ├── TheaterController.java
│       │   │   ├── UserController.java
│       │   │   └── AuthController.java
│       │   │
│       │   ├── Dto/
│       │   │   ├── BookingDto/
│       │   │   │   ├── BookingRequestDto.java
│       │   │   │   └── BookingResponseDTO.java
│       │   │   ├── LogInDto/
│       │   │   │   └── LoginRequestDto.java
│       │   │   ├── ScreenDto/
│       │   │   │   └── ScreenResponseDTO.java
│       │   │   ├── SeatDto/
│       │   │   │   └── SeatResponseDTO.java
│       │   │   ├── ShowDto/
│       │   │   │   └── ShowResponseDTO.java
│       │   │   ├── TheaterDto/
│       │   │   │   └── TheaterResponseDTO.java
│       │   │   └── UserDto/
│       │   │       └── UserRequestDto.java
│       │   │
│       │   ├── Entity/
│       │   │   ├── Booking.java
│       │   │   ├── City.java
│       │   │   ├── Movie.java
│       │   │   ├── Screen.java
│       │   │   ├── Seat.java
│       │   │   ├── Show.java
│       │   │   ├── Theater.java
│       │   │   └── User.java
│       │   │
│       │   ├── Enum/
│       │   │   └── SeatType.java
│       │   │
│       │   ├── Repository/
│       │   │   ├── BookingRepository.java
│       │   │   ├── CityRepository.java
│       │   │   ├── MovieRepository.java
│       │   │   ├── ScreenRepository.java
│       │   │   ├── SeatRepository.java
│       │   │   ├── ShowRepository.java
│       │   │   ├── TheaterRepository.java
│       │   │   └── UserRepository.java
│       │   │
│       │   ├── Security/
│       │   │   ├── JwtService.java
│       │   │   ├── JwtAuthenticationFilter.java
│       │   │   └── CustomUserDetailsService.java
│       │   │
│       │   └── Service/
│       │       ├── AuthService/
│       │       ├── BookingService/
│       │       ├── CityService/
│       │       ├── MoviesService/
│       │       ├── ScreenService/
│       │       ├── SeatService/
│       │       ├── ShowService/
│       │       ├── TheatorService/
│       │       └── UserService/
│       │
│       └── resources/
│           └── application.properties
│
└── pom.xml
```

---

## 🔐 Security Components

| Component                  | Responsibility                                     |
| -------------------------- | -------------------------------------------------- |
| `SecurityConfig`           | Configures Spring Security and protected endpoints |
| `JwtService`               | Generates and validates JWT tokens                 |
| `JwtAuthenticationFilter`  | Reads and validates JWT from requests              |
| `CustomUserDetailsService` | Loads users from MySQL                             |
| `AuthController`           | Handles registration and login                     |
| `AuthService`              | Handles authentication business logic              |
| `BCryptPasswordEncoder`    | Hashes and verifies passwords                      |

---

## 🔗 Entity Relationships

| Relationship      | Type         |
| ----------------- | ------------ |
| City → Theaters   | One-to-Many  |
| Theater → Screens | One-to-Many  |
| Screen → Seats    | One-to-Many  |
| Screen → Shows    | One-to-Many  |
| Movie → Shows     | One-to-Many  |
| User → Bookings   | One-to-Many  |
| Show → Bookings   | One-to-Many  |
| Booking ↔ Seats   | Many-to-Many |

---

# 📡 API Reference

> **Base URL:** `http://localhost:8080/api`

---

## 🔑 Authentication APIs

### Register

```http
POST /api/auth/register
```

Request:

```json
{
  "name": "Aabid",
  "email": "aabid@gmail.com",
  "password": "123456"
}
```

Example response:

```json
{
  "success": true,
  "message": "User registered successfully"
}
```

---

### Login

```http
POST /api/auth/login
```

Request:

```json
{
  "email": "aabid@gmail.com",
  "password": "123456"
}
```

Response:

```json
{
  "success": true,
  "message": "Login successful",
  "token": "JWT_TOKEN",
  "user": {
    "id": 1,
    "name": "Aabid",
    "email": "aabid@gmail.com",
    "role": "USER"
  }
}
```

---

## 🔒 Protected API Requests

After login, copy the JWT token.

For protected APIs, add:

```http
Authorization: Bearer YOUR_JWT_TOKEN
```

Example:

```http
GET /api/bookings
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

---

## 🚫 Authentication Responses

### 401 Unauthorized

Returned when:

* JWT is missing
* JWT is invalid
* JWT is expired
* Login credentials are incorrect

Example:

```json
{
  "success": false,
  "message": "Authentication required"
}
```

### 403 Forbidden

Returned when an authenticated user does not have permission.

Example:

```json
{
  "success": false,
  "message": "Access denied"
}
```

For example:

```text
USER
  │
  └──→ ADMIN API
          │
          ▼
       403 Forbidden
```

---

# 🎟️ Booking Security

Bookings require authentication.

A user can only access their own bookings.

```text
User A
  │
  ├──→ Booking A
  │       ✅ Allowed
  │
  └──→ Booking B
          ❌ Forbidden
```

The ownership check is performed on the backend.

Changing the booking ID in the URL does not allow a user to access another user's booking.

---

# ▶️ Running the App

## Using Maven

```bash
mvn spring-boot:run
```

## Using JAR

```bash
mvn clean package
java -jar target/ShowTime-0.0.1-SNAPSHOT.jar
```

## Using IntelliJ IDEA

```text
Run → ShowTimeApplication.java → Run
```

---

# ⚙️ Configuration

Configure your MySQL database in:

```text
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/showtime
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

JWT configuration:

```properties
jwt.secret=${JWT_SECRET}
jwt.expiration=86400000
```

Set the JWT secret as an environment variable instead of committing it to GitHub.

### Windows PowerShell

```powershell
$env:JWT_SECRET="your-secure-secret-key"
```

### Linux/macOS

```bash
export JWT_SECRET="your-secure-secret-key"
```

---

# 🧪 Testing JWT Authentication with Postman

### 1. Register

```http
POST http://localhost:8080/api/auth/register
```

Body:

```json
{
  "name": "Aabid",
  "email": "aabid@gmail.com",
  "password": "123456"
}
```

### 2. Login

```http
POST http://localhost:8080/api/auth/login
```

Copy the JWT from the response.

### 3. Access Protected API

Add the following header:

```http
Authorization: Bearer YOUR_JWT_TOKEN
```

Then call a protected endpoint.

### 4. Test Without Token

Call a protected endpoint without the Authorization header.

Expected:

```text
401 Unauthorized
```

### 5. Test Invalid Token

```http
Authorization: Bearer invalid-token
```

Expected:

```text
401 Unauthorized
```

### 6. Test Role Authorization

Login as a USER and try to access an ADMIN endpoint.

Expected:

```text
403 Forbidden
```

Login as an ADMIN and access the same endpoint.

Expected:

```text
200 OK
```

---

# 🖥️ Frontend

This backend is connected to the existing HTML frontend.

## 🛠️ Frontend Tech Stack

* HTML5
* CSS3
* JavaScript (Vanilla JS)

---

## 📁 Frontend Project Structure

```text
FRONTED/
│── css/        # Stylesheets
│── js/         # JavaScript files
│── pages/      # Additional HTML pages
│── index.html  # Main entry point
```

---

## ▶️ Run Frontend

### 1. Open Project

Navigate to the frontend folder:

```bash
cd FRONTED
```

### 2. Run the Project

Open:

```text
index.html
```

in your browser.

### OR

Use VS Code Live Server:

```text
Right-click index.html
        ↓
Open with Live Server
```

The frontend will open at the URL provided by Live Server.

---

# 🛡️ Security Best Practices

* Never store plain-text passwords.
* Never commit JWT secrets to GitHub.
* Use BCrypt for password hashing.
* Keep JWT authentication stateless.
* Validate JWT expiration.
* Validate JWT signature.
* Protect ADMIN APIs.
* Validate booking ownership on the backend.
* Never trust frontend role information.
* Never return passwords in API responses.
* Do not put sensitive information inside JWT payloads.

---

# 🔮 Future Improvements

* React frontend
* Refresh token mechanism
* Forgot password
* Email verification
* Google OAuth2 login
* Payment gateway integration
* Email booking confirmation
* QR-code ticket generation
* Redis integration
* Advanced admin dashboard
* Movie recommendations

---

## 🙏 Acknowledgements

* Inspired by [BookMyShow](https://in.bookmyshow.com/) — India's largest entertainment ticketing platform
* Built as a full-stack learning project using Spring Boot, MySQL, HTML, CSS, and JavaScript
* Developed with the help of the **Code for Success** platform

---

<div align="center">

**⭐ Star this repo if you found it helpful!**

Made By **Md Aabid** ❤️

</div>
