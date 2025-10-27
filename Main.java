import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Library library = new Library();
    public static void main (String[] args) {
       
        clearScreen();
        
        // check if an admin exists 

        if(!User.adminExists()){
            System.out.println("======+++==================================");
            System.out.println("      FIRST TIME SETUP -  CREATE ADMIN     ");
            System.out.println("===========================================");
            createFirstAdmin();
        }

        //Main Login Loop

        while(true){
            clearScreen();
            System.out.println("===========================================");
            System.out.println("         LIBRARY MANAGEMENT SYSTEM");
            System.out.println("===========================================");
            System.out.println("1. Login");
            System.out.println("2. Register (New User)");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = getIntInput();

            switch(choice){
                case 1:
                    login();
                    break;
                case 2: 
                    registerUser();
                    break;
                case 3:
                    clearScreen();
                    System.out.println("Thank you for using Library Management System!");
                    DatabaseConnection.closeConnection();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Try");
                    waitForEnter();
                }
            }
        }

        private static void createFirstAdmin(){
            System.out.print("Enter admin username: ");
            String username = scanner.nextLine().trim();

            System.out.print("Enter admin password: ");
            String password = scanner.nextLine().trim();

            System.out.print("Enter admin name: ");
            String name = scanner.nextLine().trim();
            
            if(User.register(username, password, name, true)){
                System.out.println("\nAdmin account created succesfully!");
                System.out.println("\nYou can now login with your credential.");
            }
            else {
                System.out.println("Failed to create admin account!");
            }
            waitForEnter();

        }

        private static void login() {
        clearScreen();
        System.out.println("========================================");
        System.out.println("              LOGIN");
        System.out.println("========================================");
        
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        User user = User.login(username, password);

        if (user != null) {
            System.out.println("\nLogin successful! Welcome, " + user.getName() + "!");
            waitForEnter();
            
            if (user.isAdmin()) {
                adminMenu(user);
            } else {
                userMenu(user);
            }
        } else {
            System.out.println("\nInvalid username or password!");
            waitForEnter();
        }
    }

    private static void registerUser() {
        clearScreen();
        System.out.println("========================================");
        System.out.println("           USER REGISTRATION");
        System.out.println("========================================");
        
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();
        
        System.out.print("Enter your name: ");
        String name = scanner.nextLine().trim();

        if (User.register(username, password, name, false)) {
            System.out.println("\nRegistration successful! You can now login.");
        } else {
            System.out.println("\nRegistration failed! Username might already exist.");
        }
        
        waitForEnter();
    }

     private static void adminMenu(User admin) {
        while (true) {
            clearScreen();
            System.out.println("========================================");
            System.out.println("           ADMIN DASHBOARD");
            System.out.println("========================================");
            System.out.println("Logged in as: " + admin.getName() + " (Admin)");
            System.out.println("========================================");
            System.out.println("1. View All Books");
            System.out.println("2. View Available Books");
            System.out.println("3. View Borrowed Books");
            System.out.println("4. Add New Book");
            System.out.println("5. Edit Book");
            System.out.println("6. Delete Book");
            System.out.println("7. Logout");
            System.out.print("Enter your choice: ");

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    clearScreen();
                    library.showAllBooks();
                    waitForEnter();
                    break;
                case 2:
                    clearScreen();
                    library.showAvailableBooks();
                    waitForEnter();
                    break;
                case 3:
                    clearScreen();
                    library.showBorrowedBooks();
                    waitForEnter();
                    break;
                case 4:
                    clearScreen();
                    addBook();
                    waitForEnter();
                    break;
                case 5:
                    clearScreen();
                    editBook();
                    waitForEnter();
                    break;
                case 6:
                    clearScreen();
                    deleteBook();
                    waitForEnter();
                    break;
                case 7:
                    System.out.println("Logging out...");
                    waitForEnter();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
                    waitForEnter();
            }
        }
    }

    private static void userMenu(User user) {
        while (true) {
            clearScreen();
            System.out.println("========================================");
            System.out.println("            USER DASHBOARD");
            System.out.println("========================================");
            System.out.println("Logged in as: " + user.getName());
            System.out.println("========================================");
            System.out.println("1. View Available Books");
            System.out.println("2. Borrow Book");
            System.out.println("3. Return Book");
            System.out.println("4. My Borrowed Books");
            System.out.println("5. My Borrowing History");
            System.out.println("6. Logout");
            System.out.print("Enter your choice: ");

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    clearScreen();
                    library.showAvailableBooks();
                    waitForEnter();
                    break;
                case 2:
                    clearScreen();
                    borrowBook(user);
                    waitForEnter();
                    break;
                case 3:
                    clearScreen();
                    returnBook(user);
                    waitForEnter();
                    break;
                case 4:
                    clearScreen();
                    library.showUserBorrowedBooks(user.getUserId());
                    waitForEnter();
                    break;
                case 5:
                    clearScreen();
                    library.showUserHistory(user.getUserId());
                    waitForEnter();
                    break;
                case 6:
                    System.out.println("Logging out...");
                    waitForEnter();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
                    waitForEnter();
            }
        }
    }

private static void addBook() {
        System.out.println("========== ADD NEW BOOK ==========");
        System.out.print("Enter book title: ");
        String title = scanner.nextLine().trim();
        
        System.out.print("Enter author name: ");
        String author = scanner.nextLine().trim();

        library.addBook(title, author);
    }

    private static void editBook() {
        System.out.println("========== EDIT BOOK ==========");
        library.showAllBooks();
        
        System.out.print("Enter book ID to edit: ");
        int id = getIntInput();

        Book book = library.findBookById(id);
        if (book != null) {
            System.out.print("Enter new title (current: " + book.getTitle() + "): ");
            String newTitle = scanner.nextLine().trim();
            
            System.out.print("Enter new author (current: " + book.getAuthor() + "): ");
            String newAuthor = scanner.nextLine().trim();

            library.editBook(id, newTitle, newAuthor);
        }
    }

    private static void deleteBook() {
        System.out.println("========== DELETE BOOK ==========");
        library.showAllBooks();
        
        System.out.print("Enter book ID to delete: ");
        int id = getIntInput();

        System.out.print("Are you sure? (yes/no): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (confirm.equals("yes")) {
            library.deleteBook(id);
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    private static void borrowBook(User user) {
        System.out.println("========== BORROW BOOK ==========");
        library.showAvailableBooks();
        
        System.out.print("Enter book ID to borrow: ");
        int id = getIntInput();

        Book book = library.findBookById(id);
        if (book != null) {
            book.borrowBook(user.getUserId());
        }
    }

    private static void returnBook(User user) {
        System.out.println("========== RETURN BOOK ==========");
        library.showUserBorrowedBooks(user.getUserId());
        
        System.out.print("Enter book ID to return: ");
        int id = getIntInput();

        Book book = library.findBookById(id);
        if (book != null) {
            book.returnBook(user.getUserId());
        }
    }

    private static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // If clearing fails, just print some newlines
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }

    private static void waitForEnter() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private static int getIntInput() {
        while (true) {
            try {
                int input = Integer.parseInt(scanner.nextLine().trim());
                return input;
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }

    
}