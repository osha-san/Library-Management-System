import java.util.Scanner;

public class Main {
    public static void main (String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        //Sample books
    

        int choice;

        do 
        {
            System.out.println("\n==== Library Management System ====");
            System.out.println("1. View All Books");
            System.out.println("2. Borrow Book");
            System.out.println("3. Return Book");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    library.showAllBooks();
                    break;
                case 2:
                    System.out.print("Enter book ID to borrow: ");
                    int borrowId = scanner.nextInt();
                    Book bookToBorrow = library.findBookById(borrowId);
                    if (bookToBorrow != null) bookToBorrow.borrowBoook();
                    break;
                case 3:
                    System.out.print("Enter book ID to return: ");
                    int returnId = scanner.nextInt();
                    Book bookToReturn = library.findBookById(returnId);
                    if (bookToReturn != null) bookToReturn.returnBook();
                    break;
                case 4:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
        while (choice != 4);
        scanner.close();
    }
}