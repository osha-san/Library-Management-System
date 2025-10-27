# Library Management System

A comprehensive Java-based library management system with PostgreSQL database integration, featuring role-based access control (Admin/User), authentication, and complete borrowing tracking.

## Features

### Admin Features
- ✅ View all books with their status (borrowed/available)
- ✅ View only available books
- ✅ View all borrowed books with borrower details and borrow date
- ✅ Add new books to the library
- ✅ Edit existing book details (title, author)
- ✅ Delete books from the system
- ✅ Full control over library inventory

### User Features
- ✅ Register and login with username/password
- ✅ View available books (borrowed books are hidden)
- ✅ Borrow books
- ✅ Return borrowed books
- ✅ View personal borrowed books
- ✅ View complete borrowing history with dates

### System Features
- ✅ Role-based authentication (Admin/User)
- ✅ First-time setup to create admin account
- ✅ Screen clearing after each action for better UX
- ✅ Complete borrowing history tracking
- ✅ PostgreSQL database for persistent storage
- ✅ Transaction management for data integrity

## Prerequisites

- **Java JDK 11 or higher**
- **PostgreSQL 12 or higher**
- **pgAdmin** (recommended for database management)
- **PostgreSQL JDBC Driver** (postgresql-42.7.1.jar or later)

## Setup Instructions

### 1. Clone the Repository
```bash
git clone https://github.com/YOUR_USERNAME/LibraryManagementSystem.git
cd LibraryManagementSystem
```

### 2. Set Up PostgreSQL Database

**Using pgAdmin:**
1. Open pgAdmin
2. Right-click on **Databases** → **Create** → **Database**
3. Name it: `library_db`
4. Right-click on **library_db** → **Query Tool**
5. Copy and paste the contents of `database_setup.sql`
6. Click **Execute** (▶ button or F5)

**Using Command Line:**
```bash
psql -U postgres
CREATE DATABASE library_db;
\c library_db
\i database_setup.sql
```

### 3. Configure Database Connection

1. Copy `DatabaseConnection.example.java` to `DatabaseConnection.java`
   ```bash
   cp DatabaseConnection.example.java DatabaseConnection.java
   ```

2. Edit `DatabaseConnection.java` and update your credentials:
   ```java
   private static final String URL = "jdbc:postgresql://localhost:5432/library_db";
   private static final String USER = "postgres";  // Your PostgreSQL username
   private static final String PASSWORD = "your_password";  // Your password
   ```

### 4. Download PostgreSQL JDBC Driver

1. Download from: https://jdbc.postgresql.org/download/
2. Create a `lib` folder in your project root
3. Place the `.jar` file in the `lib` folder

### 5. Compile and Run

**Windows (PowerShell/CMD):**
```bash
javac -cp ".;lib/postgresql-42.7.1.jar" *.java
java -cp ".;lib/postgresql-42.7.1.jar" Main
```

**Mac/Linux:**
```bash
javac -cp ".:lib/postgresql-42.7.1.jar" *.java
java -cp ".:lib/postgresql-42.7.1.jar" Main
```

**Using VS Code:**
1. Add the JDBC driver to Referenced Libraries:
   - Press `Ctrl+Shift+P`
   - Select "Java: Configure Classpath"
   - Click `+` under Referenced Libraries
   - Select `postgresql-42.7.1.jar`
2. Open `Main.java` and click the Run button

## First Time Setup

On first run, you'll be prompted to create an admin account:
1. Enter admin username
2. Enter admin password
3. Enter admin name
4. Login with your new credentials

## Usage Guide

### Admin Workflow
1. Login with admin credentials
2. Manage books (add, edit, delete)
3. Monitor borrowed books and borrowers
4. View all books and their status

### User Workflow
1. Register a new user account
2. Login with your credentials
3. Browse available books
4. Borrow books
5. Return books when done
6. Check your borrowing history

## Project Structure
```
LibraryManagementSystem/
├── Main.java                           # Application entry point
├── User.java                           # User authentication and management
├── Book.java                           # Book entity and operations
├── Library.java                        # Library operations and queries
├── DatabaseConnection.java             # Database connection (not tracked)
├── DatabaseConnection.example.java     # Database connection template
├── database_setup.sql                  # Database schema
├── lib/
│   └── postgresql-42.7.1.jar          # JDBC driver (not tracked)
├── .gitignore
└── README.md
```

## Database Schema

### Users Table
- `user_id` (Primary Key)
- `username` (Unique)
- `password`
- `name`
- `is_admin`
- `created_at`

### Books Table
- `id` (Primary Key)
- `title`
- `author`
- `is_borrowed`
- `created_at`

### Borrowing Records Table
- `record_id` (Primary Key)
- `book_id` (Foreign Key → books)
- `user_id` (Foreign Key → users)
- `borrow_date`
- `return_date`
- `is_returned`

## Security Notes

⚠️ **Important:** This is an educational project. In production:
- Use password hashing (BCrypt, Argon2)
- Implement prepared statements (already done)
- Add input validation and sanitization
- Use environment variables for credentials
- Implement session management
- Add SSL/TLS for database connections

## Troubleshooting

### "PostgreSQL JDBC Driver not found"
- Make sure the JDBC driver is in the `lib` folder
- Verify the classpath includes the driver
- Check the jar filename matches your command

### "Connection failed"
- Verify PostgreSQL is running
- Check database name, username, and password
- Ensure PostgreSQL is listening on port 5432
- Test connection using pgAdmin

### "Book not found" errors
- Ensure the database has been set up with `database_setup.sql`
- Check if books exist using pgAdmin

## Future Enhancements

- [ ] Password hashing and encryption
- [ ] Email notifications for overdue books
- [ ] Book search and filtering
- [ ] ISBN and category support
- [ ] Fine calculation for late returns
- [ ] Book reservation system
- [ ] Export reports (PDF, Excel)
- [ ] Web interface (Spring Boot + React)

## Contributing

Feel free to fork this project and submit pull requests for improvements!

## License

MIT License

## Author

Joshua - [GitHub Profile](https://github.com/osha-san)