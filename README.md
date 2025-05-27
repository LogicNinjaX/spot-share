---
# 🚗 Spot Share – Peer-to-Peer Parking Space Sharing <img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Smilies/Partying%20Face.png" alt="Partying Face" width="25" height="25" />

Spot Share is a Spring Boot-based backend application that enables parking space owners to list their available spots and allows vehicle owners to search, book, and pay for them seamlessly. It features JWT-based authentication, role-based access, and intuitive booking management.

---

## 🔑 Key Features

### 👤 For Parking Space Owners
- Register / Login
- List parking spot with:
  - Address
  - Availability (date/time)
  - Price per hour
  - Allowed vehicle types
  - Image upload (upcoming)
- View bookings for their spots

### 🚘 For Vehicle Owners
- Search parking spots by location, time/date, and price
- Book and pay for parking 
- Get booking confirmation email
- Leave ratings & reviews

### 🧾 General Features
- Booking history
- Ratings and reviews
- Email/SMS notifications (upcoming)
- Admin panel for user, booking, and dispute management

---

## 🛠️ Tech Stack

| Layer        | Technology                       |
|--------------|----------------------------------|
| Backend      | Spring Boot                      |
| Security     | Spring Security + JWT            |
| Data Access  | Spring Data JPA                  |
| Database     | MySQL / PostgreSQL               |
| Caching      | Redis (for future scalability)   |

---

## 👥 Roles & Permissions

| Role           | Key Permissions |
|----------------|-----------------|
| **Vehicle Owner** | Search, Book, Pay, Review |
| **Parking Owner** | List spot, Set availability & price, View bookings, Earn money |
| **Admin**         | Manage users, Approve listings, Handle disputes, View analytics |

---

## 🌐 API Base URI

```
/api/v1/
```

---

## 🔐 Authentication & User Management

| Method | Endpoint       | Description               | Access     |
|--------|----------------|---------------------------|------------|
| POST   | `/owners/register` | Register a new owner        | Public     |
| POST   | `/parkers/register` | Register a new parker        | Public     |
| POST   | `/owners/login`    | Login and receive JWT      | Public     |
| POST   | `/parkers/login` | Register a new owner        | Public     |

---

## 🅿️ Parking Spot Management

| Method | Endpoint                    | Description               | Access         |
|--------|-----------------------------|---------------------------|----------------|
| POST   | `/parking-spots`            | Add new parking spot      | Parking Owner  |
| GET    | `/parking-spots/my`         | List my parking spots     | Parking Owner  |
| PUT    | `/parking-spots/{parking-id}`       | Update a parking spot     | Parking Owner  |
| DELETE | `/parking-spots/{parking-id}`       | Delete a parking spot     | Parking Owner  |
| GET    | `/parking-spots`            | Search available spots    | Vehicle Owner  |

---

## 📅 Booking Management

| Method | Endpoint                          | Description                         | Access         |
|--------|-----------------------------------|-------------------------------------|----------------|
| POST   | `/bookings`                       | Create a booking                    | Vehicle Owner  |
| GET    | `/bookings/my`                    | View my bookings (as customer)      | Vehicle Owner  |
| PUT    | `/bookings/{booking-id}/cancel`           | Cancel a booking                    | Vehicle Owner  |
| GET    | `/bookings/{booking-id}`                  | Get booking details                 | Owner/Customer |

---

## ⭐ Ratings & Reviews

| Method | Endpoint                                | Description                         | Access         |
|--------|-----------------------------------------|-------------------------------------|----------------|
| POST   | `/reviews`                              | Add a review                        | Vehicle Owner  |
| GET    | `/reviews/parking-spot/{id}`            | Get reviews for a parking spot      | All Roles      |

---

## 🔗ER DIAGRAM <img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Smilies/Sleeping%20Face.png" alt="Sleeping Face" width="25" height="25" />
![er_diagram](/src/main/resources/images/spot_share_er.png)

---

## 🧩 Project Dependencies (Maven)

```xml
<dependencies>
    <!-- Core Spring Boot -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <!-- MySQL Driver -->
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <scope>runtime</scope>
    </dependency>

    <!-- Security & JWT -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-api</artifactId>
        <version>0.12.6</version>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-impl</artifactId>
        <version>0.12.6</version>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-jackson</artifactId>
        <version>0.12.6</version>
        <scope>runtime</scope>
    </dependency>

    <!-- Redis -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-redis</artifactId>
    </dependency>

    <!-- Model Mapper -->
    <dependency>
        <groupId>org.modelmapper</groupId>
        <artifactId>modelmapper</artifactId>
        <version>3.2.1</version>
    </dependency>

    <!-- Testing -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```
---

## 🚀 Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/LogicNinjaX/spot-share.git
   ```

   ---

## 📂 Folder Structure (Suggested)

```
src/
├── main/
│   ├── java/com/example/spot_share/
|   |   ├── config
│   │   ├── controller/
│   │   ├── entity/
│   │   ├── enums/
│   │   ├── repository/
│   │   ├── security/
│   │   ├── service/
│   │   └── util/
│   └── resources/
│       ├── application.yml
│       └── images/spot_share_er.png

```

2. Set up MySQL/PostgreSQL and configure your `application.yml`.

```xml
spring:
  application:
     name: spot-share

  datasource:
    url: ${db-url} # database url
    username: ${db-uname} # database name
    password: ${db-pass} # password

  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        format_sql: true
        highlight_sql: true
    show-sql: true

  cache:
    type: redis
  data:
    redis:
      host: ${redis-host} # host url
      password: ${redis-pass} # redis password
      port: ${redis-port} # port
      timeout: 60000
```

4. Build and run the project:
   ```bash
   mvn spring-boot:run
   ```
5. Access API at `http://localhost:8080/api/v1/`

---

## 📧 Contact

For questions or contributions, reach out via [GitHub Issues](https://github.com/LogicNinjaX/spot-share/issues).
