# Library Management System

A Java-based library management system with PostgreSQL database integration.

## Features
- View all books
- Add new books
- Borrow books
- Return books
- Delete books
- PostgreSQL database persistence

## Prerequisites
- Java JDK 11 or higher
- PostgreSQL
- PostgreSQL JDBC Driver

## Setup Instructions

1. **Clone the repository**
```bash
   git clone https://github.com/osha-san/LibraryManagementSystem.git
   cd LibraryManagementSystem
```

2. **Set up PostgreSQL Database**
   - Create database: `library_db`
   - Run the SQL script in `database_setup.sql`

3. **Configure Database Connection**
   - Copy `DatabaseConnection.example.java` to `DatabaseConnection.java`
   - Update with your PostgreSQL credentials

4. **Download PostgreSQL JDBC Driver**
   - Download from: https://jdbc.postgresql.org/download/
   - Place in `lib/` folder

5. **Compile and Run**
```bash
   javac -cp ".;lib/postgresql-42.7.1.jar" *.java
   java -cp ".;lib/postgresql-42.7.1.jar" Main
```

## Project Structure
```
LibraryManagementSystem/
├── Book.java
├── Library.java
├── Main.java
├── User.java
├── DatabaseConnection.java (not tracked)
├── DatabaseConnection.example.java
├── database_setup.sql
├── lib/
│   └── postgresql-42.7.1.jar (not tracked)
└── README.md
```

## License
MIT License