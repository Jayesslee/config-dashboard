# Demo Project: User & Config Service with Angular Frontend

## Overview
This project is a microservices-based application with a **Spring Boot backend** (User Service & Config Service) and an **Angular frontend**. It features **JWT authentication**, **configuration management**, and a responsive UI built with **Angular Material**.

## Architecture
- **Backend (Spring Boot 3) - User Service & Config Service**
- **Frontend (Angular 18) - Authentication & Config Management Dashboard**
- **Database (MySQL) - User Authentication & Configuration Storage**
- **Redis - Caching Mechanism**
- **Docker Compose** for easy deployment

---

## Backend - Spring Boot 3
### **1. User Service (Authentication & Authorization)**
- **Endpoints:**
  - `POST /register` → Register a new user
  - `POST /login` → Authenticate user & return JWT
- **Security:**
  - Use **Spring Security** & **JWT Authentication**
  - CORS configuration enabled

### **2. Config Service (Configuration Management)**
- **Endpoints:**
  - `GET /config/get/all` → Fetch all configuration parameters
  - `GET /config/get` → Fetch one configuration parameter
  - `POST /config/save` → Add or update a  config
  - `DELETE /config/{key}` → Delete a config (Only 'ROLE_ADMIN' can perform delete operation.)
- **Security:**
  - Only authenticated users can manage configurations
  - **Spring Security** is used to secure API endpoints

### **Database (MySQL)**
- **Tables:**
  - `user` → Stores registered users
  - `role` → Stores pre-defined roles
  - `user_role` → Stores user-role relations
  - `config` → Stores config key-values
  

---

## Frontend - Angular 18
### **1. Authentication**
- Login and register page with JWT authentication
- Store JWT token in `localStorage`
- Attach JWT token in HTTP headers for authenticated requests

### **2. Configuration Management Dashboard**
- **CRUD operations** for configuration parameters
- Only authenticated users can modify configurations
- **Angular Material Design** for UI components
  - Table for listing configurations (with pagination)
  - Forms for adding/editing configurations
  - Buttons for managing configurations

### **3. Routing & Navigation**
- **Angular Routing** to switch between pages
- **Route Guards** to protect authenticated routes

### **4. UX/UI Enhancements**
- **Angular Material Table** for displaying configurations
- **Responsive Design** for mobile & desktop views

---

## Deployment & Setup
### **Run with Docker Compose**
```bash
# Clone the repository
git clone https://github.com/Jayesslee/config-dashboard

# Start backend services (MySQL, Redis, User & Config Service), all SQL shall be automatically excuted.
cd backend

cd user-service
gradlew build

cd config-service
gradlew build

cd backend
docker-compose up -d

# Retart backend services (All data will be clean up)
docker-compose down -v
docker-compose up --build

# Start frontend (inside Angular project folder)
cd frontend
npm install
npm run start

# Use pre-defined accoun or register your own
# Only admin can delete configs on dashboard
# UserName: admin
# Password: 123456

Open Chrome, enter url `http://localhost:4200/`
```


---



