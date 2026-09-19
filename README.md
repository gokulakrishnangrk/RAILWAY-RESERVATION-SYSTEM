Railway Reservation System

Project Description

Railway Reservation System is a Java-based application developed to manage railway ticket reservations efficiently.

The system allows users to reserve train tickets, view reservation details, and manage passenger information. The application uses JDBC for database connectivity and follows a Layered Architecture for better organization and maintainability.

Features

- Passenger Registration
- Train Details Management
- Ticket Reservation
- View Reservation Details
- Search Reservation
- Update Reservation Details
- Cancel Reservation
- Seat Number Management
- Database Connectivity

Technologies Used

- Java
- JDBC
- MySQL
- Eclipse IDE

Architecture

The project follows a Layered Architecture:

- Model Layer – Represents passengers, trains, and reservation details.
- Repository Layer – Handles database operations.
- Service Layer – Contains application and business logic.
- Controller Layer – Handles user requests and connects different layers.

Architecture Flow

User
  ↓
Controller
  ↓
Service
  ↓
Repository
  ↓
JDBC
  ↓
MySQL Database

Database

MySQL is used to store railway reservation-related information.

Main Tables

- Reservation
- Passenger
- Train

How to Run

1. Install Java JDK.
2. Install MySQL.
3. Create the required database and tables.
4. Configure the database username and password.
5. Open the project in Eclipse.
6. Add MySQL Connector/J.
7. Run the main Java class.

Project Outcome

The Railway Reservation System provides a simple and efficient platform for managing train reservations and passenger details using Java, JDBC, MySQL, and Layered Architecture.

Author

Gokulakrishnan.R
