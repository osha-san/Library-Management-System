import java.sql.*;

public class Book {
    private int id;
    private String title;
    private String author;
    private Boolean isBorrowed;


    public Book(int id, String title, String author, Boolean isBorrowed){
        this.id = id;
        this.title = title;
        this.author = author;
        this.isBorrowed = isBorrowed;
    }


    public int getId() { 
        return id;
    }
    public String getTitle(){
        return title;
    }
    public String getAuthor() {
        return author;
    }

    public boolean isBorrowed(){
        return isBorrowed;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    //Borrrow book with user tracking
    public boolean borrowBook(int userId){
        if(!isBorrowed){
            String updateBook = "UPDATE books SET is_borrowed = ? WHERE id = ?";
            String insertRecord ="INSERT INTO borrowing_records (book_id, user_id, is_returned) VALUES (?, ?, ?)";

            try (Connection conn = DatabaseConnection.getConnection()){
                conn.setAutoCommit(false);

                try(PreparedStatement pstmt1 = conn.prepareStatement(updateBook);
                    PreparedStatement pstmt2 = conn.prepareStatement(insertRecord)){
                        //Update book status
                        pstmt1.setBoolean(1, true);
                        pstmt1.setInt(2, id);
                        pstmt1.executeUpdate();

                        // Insert borrowing record
                        pstmt2.setInt(1,id);
                        pstmt2.setInt(2, userId);
                        pstmt2.setBoolean(3, false);
                        pstmt2.executeUpdate();

                        conn.commit();
                        isBorrowed = true;
                        System.out.println(title + " has been borrowed succesfully!");
                        return true;
                    }
                catch (SQLException e){
                    conn.rollback();
                    throw e;
                }    
            }
            catch (SQLException e){
                System.err.println("Error borrowing book: " + e.getMessage());
                return false;
            }
        }
        else {
            System.out.println(title + " is already borrowed");
            return false;
        }
    }

    // return book

    public boolean returnBook(int userId){
        if (isBorrowed) {
            String updateBook = "UPDATE books SET is_borrowed = ? WHERE id = ?";
            String updateRecord = "UPDATE borrowing_records SET is_returned = ?, return_date = CURRENT_TIMESTAMP WHERE book_id = ? AND user_id = ? AND is_returned = false";

            try(Connection conn = DatabaseConnection.getConnection()){
                conn.setAutoCommit(false);

                try (PreparedStatement pstmt1 = conn.prepareStatement(updateBook);
                    PreparedStatement pstmt2 = conn.prepareStatement(updateRecord)) {

                        // Update book status
                        pstmt1.setBoolean(1, false);
                        pstmt1.setInt(2, id);
                        pstmt1.executeUpdate();

                        // Update borrowing record
                        pstmt2.setBoolean(1, true);
                        pstmt2.setInt(2, id);
                        pstmt2.setInt(3, userId);
                        int rowsAffected = pstmt2.executeUpdate();

                        if (rowsAffected > 0 ) {
                            conn.commit();
                            isBorrowed = false;
                            System.out.println(title + " has been returned successfully!");
                            return true;
                        }
                        else {
                            conn.rollback();
                            System.out.println("You didn't borrow this book.");
                            return false;
                        }
                    }
                catch (SQLException e){
                    conn.rollback();
                    throw e;
                }

            }
            catch (SQLException e){
                System.err.println("Error returning book: " + e.getMessage());
                return false;
            }
        }
        else {
            System.out.println(title + " is not currently borrowed.");
            return false;
        }
    }

    public void displayInfo(){
        System.out.println("ID: " + id + " | Title : " + title + " | Author: " + author + " | Status: " + (isBorrowed ? "Borrowed" : "Available"));
    }

    public void displayDetailedInfo(){
         System.out.println("=====================================");
        System.out.println("Book ID: " + id);
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Status: " + (isBorrowed ? "Borrowed" : "Available"));
        System.out.println("=====================================");
    }
}