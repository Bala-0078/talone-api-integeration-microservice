# User Service API Documentation

## Register User

**POST** `/users`

Request Body:
```json
{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "securePassword123"
}
```

Response:
```json
{
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "createdAt": "2024-06-01T12:00:00",
  "updatedAt": "2024-06-01T12:00:00"
}
```

---

## Login

**POST** `/users/login`

Request Body:
```json
{
  "usernameOrEmail": "john_doe",
  "password": "securePassword123"
}
```

Response:
```json
{
  "token": "dummy-jwt-token-for-john_doe",
  "username": "john_doe",
  "email": "john@example.com"
}
```

---

## Get User

**GET** `/users/{id}`

Response:
```json
{
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "createdAt": "2024-06-01T12:00:00",
  "updatedAt": "2024-06-01T12:00:00"
}
```

---

## Update User

**PUT** `/users/{id}`

Request Body:
```json
{
  "username": "johnny_doe",
  "email": "johnny@example.com",
  "password": "newPassword456"
}
```

Response:
```json
{
  "id": 1,
  "username": "johnny_doe",
  "email": "johnny@example.com",
  "createdAt": "2024-06-01T12:00:00",
  "updatedAt": "2024-06-01T12:10:00"
}
```

---

## Error Response Example

```json
{
  "error": "Username already exists"
}
```
