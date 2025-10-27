import java.sql.*;


public class Library {

    public Library(){
        DatabaseConnection.getConnection();
    }
    

    public void addBook(Book book) {
        String sql = "INSERT INTO books (title, author, is_borrowed) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setBoolean(3, false);
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        System.out.println("Book added with ID: " + generatedKeys.getInt(1) + " - " + book.getTitle());
                    }
                }
            }
        } 
        catch (SQLException e) {
            System.err.println("Error adding book: " + e.getMessage());
        }
    }

    public void showAllBooks(){
        String sql = "SELECT * FROM books ORDER BY id";

        try (Connection conn = DatabaseConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)){
            System.out.println("\n ==== ALL BOOKS =====");
            while (rs.next()){
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                boolean isBorrowed = rs.getBoolean("is_borrowed");

                Book book = new Book(id, title, author, isBorrowed);
                book.displayInfo();

            }
        }
        catch (SQLException e) {
            System.err.println("Error retrieving books: " + e.getMessage());
        }
    }

    public Book findBookById(int id) {
        String sql = "SELECT * FROM books WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()){
                    String title = rs.getString("title");
                    String author = rs.getString("author");
                    boolean isBorrowed = rs.getBoolean("is_borrowed");

                    return new Book(id, title, author, isBorrowed);
                }
                else {
                    System.out.println("Book not found!");
                    return null;
                }

            }
        catch(SQLException e){
            System.err.println("Error finding book: " + e.getMessage());
            return null;
        }
    }
    public void deleteBook(int id){
        String sql = "DELETE FROM books WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();

            if(affectedRows > 0){
                System.out.println("Book deleted succesfully!");
            }
            else {
                System.out.println("Book not found");
            }
        }

        catch (SQLException e) {
            System.err.println("Error deleting book: " + e.getMessage());
        }
    }

}