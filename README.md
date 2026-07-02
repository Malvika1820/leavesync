# LeaveSync — Student Leave Management System

A Java-based Student Leave Management System built with **MVC architecture** and **JDBC**, backed by a **MySQL** database. Students can apply for leave; admins can review, approve, or reject requests, with automatic leave balance tracking.

## Tech Stack
- Java (Core, JDBC)
- MySQL
- MVC design pattern

## Project Structure
```
LeaveSync/
├── src/
│   ├── model/          # Student, LeaveRequest (data models)
│   ├── dao/             # DBConnection, StudentDAO, LeaveRequestDAO (JDBC layer)
│   ├── controller/       # LeaveController (business logic: balance checks, approvals)
│   └── view/             # MainMenu (console UI, entry point)
├── sql/
│   └── schema.sql       # Database schema + seed data
└── README.md
```

## Features
- Register students with an initial leave balance
- Apply for leave with automatic date validation and balance checks
- Admin review: approve/reject pending requests
- Leave balance auto-deducted on approval
- View full leave history per student or across all students

## Setup Instructions

### 1. Prerequisites
- JDK 8 or later
- MySQL Server running locally
- MySQL Connector/J (JDBC driver) — [download here](https://dev.mysql.com/downloads/connector/j/)

### 2. Create the Database
Run the schema file in MySQL:
```bash
mysql -u root -p < sql/schema.sql
```

### 3. Configure Connection
Open `src/dao/DBConnection.java` and update:
```java
private static final String PASSWORD = "your_mysql_password";
```
with your actual MySQL root password.

### 4. Compile and Run
```bash
# From the project root
javac -d out -cp "path/to/mysql-connector-j-8.x.x.jar" src/model/*.java src/dao/*.java src/controller/*.java src/view/*.java

java -cp "out:path/to/mysql-connector-j-8.x.x.jar" view.MainMenu
```
(On Windows, replace `:` with `;` in the classpath.)

### 5. Usage
Run the app and use the console menu to register students, apply for leave, and (as admin) approve/reject pending requests.

## Sample Workflow
1. Register a student (or use the seeded sample students)
2. Apply for leave — the system checks the requested days against available balance
3. Admin reviews pending requests and approves/rejects
4. On approval, the student's leave balance is automatically updated

## Author
Malvika Doddamani — B.Tech CSE (Data Science), Presidency University, Bengaluru
