import java.sql.*;
import java.util.ArrayList;


public class Library {

    public Library(){
        DatabaseConnection.getConnection();
    }
    
    //Add book (admin)
    public boolean addBook(String title, String author) {
        String sql = "INSERT INTO books (title, author, is_borrowed) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setBoolean(3, false);
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        System.out.println("Book added successfully with ID: " + generatedKeys.getInt(1));
                        return true;
                    }
                }
            }
        } 
        catch (SQLException e) {
            System.err.println("Error adding book: " + e.getMessage());
        }
        return false;
    }

    //Edit book (admin)
    public boolean editBook(int id, String newTitle, String newAuthor){
        String sql = "UDPATE books SET title = ?, author = ? WHERE id = ? ";

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
                pstmt.setString(1, newTitle);
                pstmt.setString(2, newAuthor);
                pstmt.setInt(3, id);

                int affectedRows = pstmt.executeUpdate();

                if(affectedRows > 0){
                    System.out.println("Book updated successfully!");
                    return true;
                }
                else {
                    System.out.println("Book not found!");
                    return false;
                }
            }

        catch (SQLException e){
            System.err.println("Error editing book: " + e.getMessage());
            return false;
        }
    }

    //Delete book (admin)
    public boolean deleteBook(int id){
        String sql = " DELETE FROM books WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

                pstmt.setInt(1, id);
                int affectedRows = pstmt.executeUpdate();

                if(affectedRows>0){
                    System.out.println("Book deleted successfully!");
                    return true;
                }
                else {
                    System.out.println("Book not found!");
                    return false;
                }
            }
        catch (SQLException e){
            System.err.println("Error deletin book: " + e.getMessage());
            return false;
        }

    }

    //show all books
    public void showAllBooks(){
        String sql = "SELECT * FROM books ORDER BY id";

        try (Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){

            System.out.println("\n ==== ALL BOOKS =====");
            boolean hasBooks = false;


            while (rs.next()){
                hasBooks = true;
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                boolean isBorrowed = rs.getBoolean("is_borrowed");

                Book book = new Book(id, title, author, isBorrowed);
                book.displayInfo();

            }
            if (!hasBooks){
                System.out.println("No books in the library");
            }

            System.out.println("========================================\n");
        }
        catch (SQLException e) {
            System.err.println("Error retrieving books: " + e.getMessage());
        }
    }

    //Show available books
     public void showAvailableBooks() {
        String sql = "SELECT * FROM books WHERE is_borrowed = false ORDER BY id";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.println("\n========== AVAILABLE BOOKS ==========");
            boolean hasBooks = false;
            
            while (rs.next()) {
                hasBooks = true;
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                
                Book book = new Book(id, title, author, false);
                book.displayInfo();
            }
            
            if (!hasBooks) {
                System.out.println("No available books at the moment.");
            }
            System.out.println("======================================\n");
            
        } catch (SQLException e) {
            System.err.println("Error retrieving books: " + e.getMessage());
        }
    }

    //Shoq borrowed books (admin)

    public void showBorrowedBooks() {
        String sql = "SELECT b.id, b.title, b.author, u.name, u.username, br.borrow_date " +
                     "FROM books b " +
                     "JOIN borrowing_records br ON b.id = br.book_id " +
                     "JOIN users u ON br.user_id = u.user_id " +
                     "WHERE b.is_borrowed = true AND br.is_returned = false " +
                     "ORDER BY br.borrow_date DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.println("\n========== BORROWED BOOKS ==========");
            boolean hasBooks = false;
            
            while (rs.next()) {
                hasBooks = true;
                System.out.println("Book ID: " + rs.getInt("id"));
                System.out.println("Title: " + rs.getString("title"));
                System.out.println("Author: " + rs.getString("author"));
                System.out.println("Borrowed by: " + rs.getString("name") + " (@" + rs.getString("username") + ")");
                System.out.println("Borrowed on: " + rs.getTimestamp("borrow_date"));
                System.out.println("------------------------------------");
            }
            
            if (!hasBooks) {
                System.out.println("No books are currently borrowed.");
            }
            System.out.println("====================================\n");
            
        } catch (SQLException e) {
            System.err.println("Error retrieving borrowed books: " + e.getMessage());
        }
    }

    // Show user's borrowed books
    public void showUserBorrowedBooks(int userId) {
        String sql = "SELECT b.id, b.title, b.author, br.borrow_date " +
                     "FROM books b " +
                     "JOIN borrowing_records br ON b.id = br.book_id " +
                     "WHERE br.user_id = ? AND br.is_returned = false " +
                     "ORDER BY br.borrow_date DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            System.out.println("\n========== YOUR BORROWED BOOKS ==========");
            boolean hasBooks = false;
            
            while (rs.next()) {
                hasBooks = true;
                System.out.println("Book ID: " + rs.getInt("id"));
                System.out.println("Title: " + rs.getString("title"));
                System.out.println("Author: " + rs.getString("author"));
                System.out.println("Borrowed on: " + rs.getTimestamp("borrow_date"));
                System.out.println("-----------------------------------------");
            }
            
            if (!hasBooks) {
                System.out.println("You haven't borrowed any books.");
            }
            System.out.println("==========================================\n");
            
        } catch (SQLException e) {
            System.err.println("Error retrieving your borrowed books: " + e.getMessage());
        }
    }

    
        // Show user's borrowing history
    public void showUserHistory(int userId) {
        String sql = "SELECT b.title, b.author, br.borrow_date, br.return_date, br.is_returned " +
                     "FROM borrowing_records br " +
                     "JOIN books b ON br.book_id = b.id " +
                     "WHERE br.user_id = ? " +
                     "ORDER BY br.borrow_date DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            System.out.println("\n========== YOUR BORROWING HISTORY ==========");
            boolean hasHistory = false;
            
            while (rs.next()) {
                hasHistory = true;
                System.out.println("Title: " + rs.getString("title"));
                System.out.println("Author: " + rs.getString("author"));
                System.out.println("Borrowed: " + rs.getTimestamp("borrow_date"));
                
                if (rs.getBoolean("is_returned")) {
                    System.out.println("Returned: " + rs.getTimestamp("return_date"));
                } else {
                    System.out.println("Status: Currently Borrowed");
                }
                System.out.println("--------------------------------------------");
            }
            
            if (!hasHistory) {
                System.out.println("No borrowing history found.");
            }
            System.out.println("============================================\n");
            
        } catch (SQLException e) {
            System.err.println("Error retrieving history: " + e.getMessage());
        }
    }

    // Find book by ID
    public Book findBookById(int id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String title = rs.getString("title");
                String author = rs.getString("author");
                boolean isBorrowed = rs.getBoolean("is_borrowed");
                
                return new Book(id, title, author, isBorrowed);
            } else {
                System.out.println("Book not found!");
                return null;
            }
            
        } catch (SQLException e) {
            System.err.println("Error finding book: " + e.getMessage());
            return null;
        }
    }
    

}