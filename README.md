# 🎬 ShowTime Backend

A scalable backend system for a movie ticket booking platform inspired by BookMyShow.  
This project provides RESTful APIs for managing movies, theaters, shows, and ticket bookings.

---

## 🚀 Features

- 🏙️ **City Management** — Manage cities across India
- 🎬 **Movie Catalog** — Full movie listing with genre, language, rating, poster
- 🏛️ **Theater Management** — Multiple theaters per city
- 🎥 **Screen Management** — Multiple screens per theater (4DX, IMAX, Dolby Atmos)
- 💺 **Seat Management** — REGULAR / PREMIUM / VIP seat types
- 🎟️ **Show Scheduling** — Multiple shows per day per screen
- 👤 **User Registration & Login**
- 📱 **Booking System** — Book multiple seats in one booking
- ❌ **Booking Cancellation**
- ✅ **Available Seats API** — Real-time seat availability per show

---

## 🛠️ Tech Stack

- **Backend:** Java, Spring Boot
- **Frameworks:** Spring MVC, Spring Data JPA
- **Database:** MySQL
- **Build Tool:** Maven
- **API Testing:** Postman 
- **Other:** Lombok, Hibernate

---

## 📁 Project Structure

```
ShowTime/
├── src/
│   └── main/
│       ├── java/com/cfs/ShowTime/
│       │   ├── ShowTimeApplication.java          # Main entry point
│       │   │
│       │   ├── Config/
│       │   │   └── CorsConfig.java          # CORS configuration
│       │   │
│       │   ├── Controller/
│       │   │   ├── BookingController.java
│       │   │   ├── CityController.java
│       │   │   ├── MovieController.java
│       │   │   ├── ScreenController.java
│       │   │   ├── SeatController.java
│       │   │   ├── ShowController.java
│       │   │   ├── TheaterController.java
│       │   │   └── UserController.java
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
│       │   │   └── SeatType.java            # REGULAR, PREMIUM, VIP
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
│       │   └── Service/
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

### Entity Relationships

| Relationship | Type |
|---|---|
| City → Theaters | One-to-Many |
| Theater → Screens | One-to-Many |
| Screen → Seats | One-to-Many |
| Screen → Shows | One-to-Many |
| Movie → Shows | One-to-Many |
| User → Bookings | One-to-Many |
| Show → Bookings | One-to-Many |
| Booking ↔ Seats | Many-to-Many |

---

## 📡 API Reference

> **Base URL:** `http://localhost:8080/api`

---
## ▶️ Running the App

### Using Maven
```bash
mvn spring-boot:run
```

### Using JAR
```bash
mvn clean package
java -jar target/ShowTime-0.0.1-SNAPSHOT.jar
```

### Using IntelliJ IDEA
```
Run → ShowTimeApplication.java → Run
```

### Verify it's running
```bash
curl http://localhost:8080/api/movies
```

## 🖥️ Frontend

This is backend is connected to index.html fronted

## 🛠️ Tech Stack

* HTML5
* CSS3
* JavaScript (Vanilla JS)

---

## 📁 Project Structure

```
FRONTED/
│── css/        # Stylesheets
│── js/         # JavaScript files
│── pages/      # Additional HTML pages
│── index.html  # Main entry point
```

---


**Run frontend:**
```
1. Open project
Navigate to the frontend folder:
cd FRONTED
2. Run the project
Open index.html in your browser

OR

Use VS Code Live Server:
Right-click on index.html
Click Open with Live Server

# Open at http://10.138.1.122:60305/index.html
```

## 🙏 Acknowledgements

- Inspired by [BookMyShow](https://in.bookmyshow.com/) — India's largest entertainment ticketing platform
- Built as a full-stack learning project with Spring Boot + HTML + CSS and Javasctrept
-- with the Help of code for success  platform 
---

<div align="center">

**⭐ Star this repo if you found it helpful!**

Made By Aabid ❤️ 

</div>



