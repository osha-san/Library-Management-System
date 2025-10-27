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

    public void borrowBoook() {
        if (!isBorrowed) {
            isBorrowed = true;
            updateBorrowStatus(true);
            System.out.println(title + "has been borrowed.");
            
        }
        else
        {
            System.out.println(title + " is already borroed.");
        }
    }

    public void returnBook(){
        if (isBorrowed) {
            isBorrowed = false;
            updateBorrowStatus(false);
            System.out.println(title + " has been returned.");

        }
        else {
            System.out.println(title + " was not borrowed.");
        }
    }

    private void updateBorrowStatus(boolean status){
        String sql = "UPDATE books SET is_borrowed = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
                pstmt.setBoolean(1, status);
                pstmt.setInt(2, id);
                pstmt.executeUpdate();
            }
            catch (SQLException e){
                System.err.println("Error updating borrow status: " + e.getMessage());
            }
    }

    public void displayInfo(){
        System.out.println("ID: " + id + " | Title: " + title + " | Author: " + author + " | Available: " + (!isBorrowed));
    }
}