# CollabHub Backend

A **Discord-inspired real-time collaboration platform backend** built with **Spring Boot**. The project provides secure authentication, channel-based communication, REST APIs, and real-time messaging using WebSockets.

## Features

- JWT Authentication & Authorization
- Secure Spring Security configuration
- Channel creation and management
- Channel membership management
- Real-time messaging using WebSocket (STOMP)
- Message persistence with MySQL
- Role-based access control
- DTO-based API architecture
- Input validation
- Global exception handling
- Pagination support
- Layered architecture following production best practices

---

## Tech Stack

| Technology | Purpose |
|------------|---------|
| Java 21 | Programming Language |
| Spring Boot | Backend Framework |
| Spring Security | Authentication & Authorization |
| JWT | Stateless Authentication |
| Spring Data JPA | Database Access |
| Hibernate | ORM |
| MySQL | Database |
| Spring WebSocket | Real-time Communication |
| STOMP | Messaging Protocol |
| Maven | Build Tool |
| Lombok | Boilerplate Reduction |

---

## Architecture

```
Client (React)
       │
       ▼
REST APIs / WebSocket
       │
       ▼
Controllers
       │
       ▼
Services
       │
       ▼
Repositories
       │
       ▼
MySQL Database
```

---

## Real-Time Messaging Flow

```
React Client
      │
      ▼
STOMP over WebSocket
      │
      ▼
JWT Authentication
      │
      ▼
Message Controller
      │
      ▼
Message Service
      │
      ▼
Database
      │
      ▼
Broadcast
      │
      ▼
Connected Clients
```

---

## Implemented Modules

### Authentication

- User Registration
- User Login
- JWT Token Generation
- JWT Validation
- Password Encryption (BCrypt)

### Channels

- Create Channel
- Update Channel
- Delete Channel
- Search Channels
- Pagination

### Channel Members

- Join Channel
- Leave Channel
- Channel Roles
- Owner Authorization

### Messaging

- Send Messages
- Store Messages
- Retrieve Channel Messages
- Edit Messages
- Delete Messages
- Membership Validation

### WebSocket

- STOMP Configuration
- JWT Authentication during WebSocket Connection
- Real-Time Channel Messaging
- Message Broadcasting

---

## Security

- Stateless JWT Authentication
- Spring Security
- Role-Based Authorization
- Channel Membership Validation
- Owner Permissions
- Password Encryption using BCrypt

---

## Project Structure

```
src
 ├── config
 ├── controller
 ├── dto
 ├── entity
 ├── exception
 ├── mapper
 ├── repository
 ├── security
 ├── service
 └── websocket
```

---

## API Highlights

### Authentication

```
POST /api/auth/register
POST /api/auth/login
```

### Channels

```
POST   /api/channels
GET    /api/channels
PUT    /api/channels/{id}
DELETE /api/channels/{id}
```

### Messages

```
POST /api/channels/{id}/messages
GET  /api/channels/{id}/messages
```

### WebSocket

```
Endpoint:
/ws

Publish:
/app/chat.send

Subscribe:
/topic/channels/{channelId}
```

---

## Key Learning Outcomes

This project demonstrates practical experience with:

- Spring Boot Application Development
- REST API Design
- Spring Security
- JWT Authentication
- Spring Data JPA
- Entity Relationships
- DTO Mapping
- WebSocket Communication
- STOMP Messaging
- Authentication over WebSockets
- Real-Time Systems
- Production-style Backend Architecture

---

## Future Enhancements

- Private Messaging
- Typing Indicators
- Online/Offline Presence
- Message Read Receipts
- File Sharing
- Notifications
- GraphQL API
- Redis Caching
- Docker Deployment
- CI/CD Pipeline

---

## Author

**S. Rishi Praneeth Reddy**

Built as a learning project to explore modern backend development using Spring Boot and real-time communication technologies.
