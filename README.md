# FixFlow

**Issue Tracking and Resolution Platform**

FixFlow is a full-stack issue management platform for reporting, assigning, tracking, and resolving issues through a centralized workflow.

**Live Demo:** [https://fixflow-backend-kq7z.onrender.com/](https://fixflow-backend-kq7z.onrender.com/)

## Overview

FixFlow provides a structured workflow for managing issues from creation through resolution. The application includes **JWT authentication**, **role-based access control**, issue assignment, status management, and a dashboard for monitoring issue activity.

The frontend is built with **React** and the backend with **Spring Boot**, with the production frontend served directly through the Spring Boot application.

## Features

- **JWT-based authentication**
- **Role-Based Access Control (RBAC)**
- Issue creation and reporting
- Issue assignment to agents
- Issue status updates
- Issue resolution and deletion
- Dashboard with issue statistics
- Detailed issue views
- **PostgreSQL** persistence
- RESTful API architecture
- Single-URL production deployment

## User Roles

**User**
- Register and log in
- Create issues
- View reported issues
- Track issue status

**Agent**
- View assigned issues
- Update issue status
- Work on issue resolution

**Admin**
- Access administrative operations
- Manage privileged operations through role-based authorization

## Dashboard

The dashboard provides an overview of:

- **Total Issues**
- **Open Issues**
- **In Progress Issues**
- **Resolved Issues**

## Tech Stack

**Frontend:** React, JavaScript, HTML, CSS

**Backend:** Java, Spring Boot, Spring Security, REST APIs, JWT

**Database:** PostgreSQL

**Tools & Deployment:** Git, GitHub, Maven, Postman, Render

## Architecture

```text
React Frontend
      |
      | REST API
      v
Spring Boot Backend
      |
      v
PostgreSQL Database


-------------------------------------------------------------------------------------------------------------------------------------------------------------------
In production, the React build is served by the Spring Boot application, allowing the frontend and backend to operate through a single application URL.


Create Issue
     |
     v
Issue Reported
     |
     v
Assignment
     |
     v
Open
     |
     v
In Progress
     |
     v
Resolved
------------------------------------------------------------------------------------------------------------------------------------------------------------------

**PROJECT STRUCTURE**
FixFlow_Project/
├── backend/
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/fixflow/backend/
│   │       └── resources/
│   │           └── static/
│   ├── pom.xml
│   └── mvnw
│
├── frontend/
│   ├── src/
│   │   ├── pages/
│   │   ├── App.jsx
│   │   └── main.jsx
│   ├── package.json
│   └── vite.config.js
│
└── README.md

-------------------------------------------------------------------------------------------------------------------------------------------------------------------

**Local Setup**
Prerequisites
Java 21+
Node.js
PostgreSQL
Git
--------------------------------------------------------------------------------------------------------------------------------------------------------------------
Clone the repository
git clone https://github.com/saikarthik2204/FixFlow_Project.git
cd FixFlow_Project
-------------------------------------------------------------------------------------------------------------------------------------------------------------------

**Configure PostgreSQL**

Create a database named:

fixflow

**Configure the following backend environment variables:
**
DB_URL=jdbc:postgresql://localhost:5432/fixflow
DB_USERNAME=postgres
DB_PASSWORD=your_password

**Run the backend**
cd backend

**Windows:**

.\mvnw.cmd spring-boot:run

**The backend runs on:**

http://localhost:8080
--------------------------------------------------------------------------------------------------------------------------------------------------------------------

**Run the frontend**

Open another terminal:

cd frontend
npm install
npm run dev

-------------------------------------------------------------------------------------------------------------------------------------------------------------------

**API Endpoints**
Method	Endpoint	Description
POST	/api/auth/register	Register a new user
POST	/api/auth/login	Authenticate a user
GET	/api/issues	Retrieve issues
POST	/api/issues	Create an issue
GET	/api/issues/{id}	View issue details
PUT	/api/issues/{id}	Update an issue
DELETE	/api/issues/{id}	Delete an issue
PATCH	/api/issues/{id}/status	Update issue status

Protected endpoints require JWT authentication.
--------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Security**

FixFlow uses Spring Security with:

JWT-based authentication
Stateless session management
BCrypt password hashing
Role-based authorization
Protected REST endpoints
Separate authorization rules for administrative and agent operations
--------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Deployment**

The application is deployed on Render.

The production React build is served directly through the Spring Boot backend, allowing the complete application to be accessed from a single URL.

Live Application: https://fixflow-backend-kq7z.onrender.com/

Future Improvements
SLA monitoring and automated escalation
Email notifications
Advanced issue filtering and search
Admin analytics
File attachments
Automated testing and CI/CD
--------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Author**
M V S V S Karthik
