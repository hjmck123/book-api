/**
 * Created by Harrison McKenzie on 29/11/17.
 */

import java.sql.*;

public class SQLite {
    String urlPath;

    SQLite(String fileName) {
        urlPath = "jdbc:sqlite:./sqlite/db/" + fileName;
        initDatabase();
    }

    /**
     * Initial database creation/connection which includes table creation.
     */
    private void initDatabase() {

        String sql = "CREATE TABLE IF NOT EXISTS books (\n"
                + "	ISBN integer PRIMARY KEY,\n"
                + "	BookName text NOT NULL,\n"
                + " AuthorName text NOT NULL, \n"
                + "	Price integer\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(urlPath);
                Statement stmt = conn.createStatement()) {
            System.out.println("Database connected");
            stmt.execute(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Gets a connections to the database.
     * @return connection object to the db
     */
    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(urlPath);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Inserts a new book into the database
     *
     * @param ISBN is a unique id for each book
     * @param BookName is the title of the book
     * @param AuthorName is the author's name(first and last)
     * @param Price is the cost of the book(to nearest integer value)
     */
    public void insertBook(int ISBN, String BookName, String AuthorName, int Price) {
        String sql = "INSERT INTO books (ISBN, BookName, AuthorName, Price) VALUES(?,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Set values
            pstmt.setInt(1, ISBN);
            pstmt.setString(2, BookName);
            pstmt.setString(3, AuthorName);
            pstmt.setInt(4, Price);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Prints out every book in the database
     */
    public void listBooks() {
        String sql = "SELECT ISBN, BookName, AuthorName, Price FROM books";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Print results
            while(rs.next()) {
                System.out.println(rs.getInt("ISBN") + "\t" +
                                    rs.getString("BookName") + "\t" +
                                    rs.getString("AuthorName") + "\t" +
                                    rs.getInt("Price"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Prints out every book that matches equals given values.
     *
     * @param name is a string of the book name.
     */
    public void listBooksByName(String name) {
        String sql = "SELECT ISBN, BookName, AuthorName, Price " +
                "FROM books " +
                "WHERE BookName = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set values
            pstmt.setString(1, name);

            ResultSet rs = pstmt.executeQuery();

            // Print results
            while(rs.next()) {
                System.out.println(rs.getInt("ISBN") + "\t" +
                        rs.getString("BookName") + "\t" +
                        rs.getString("AuthorName") + "\t" +
                        rs.getInt("Price"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Prints out every book that matches equals given values.
     *
     * @param name is the string name of the author.
     */
    public void listBooksByAuthor(String name) {
        String sql = "SELECT ISBN, BookName, AuthorName, Price " +
                "FROM books " +
                "WHERE AuthorName = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set values
            pstmt.setString(1, name);

            ResultSet rs = pstmt.executeQuery();

            // Print results
            while(rs.next()) {
                System.out.println(rs.getInt("ISBN") + "\t" +
                        rs.getString("BookName") + "\t" +
                        rs.getString("AuthorName") + "\t" +
                        rs.getInt("Price"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Prints out every book that matches equals given values.
     *
     * @param ISBN is the book's isbn value.
     */
    public void listBooksByISBN(int ISBN) {
        String sql = "SELECT ISBN, BookName, AuthorName, Price " +
                "FROM books " +
                "WHERE ISBN = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set values
            pstmt.setInt(1, ISBN);

            ResultSet rs = pstmt.executeQuery();

            // Print results
            while(rs.next()) {
                System.out.println(rs.getInt("ISBN") + "\t" +
                        rs.getString("BookName") + "\t" +
                        rs.getString("AuthorName") + "\t" +
                        rs.getInt("Price"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Prints out every book that matches equals given values.
     *
     * @param price is the book's isbn value.
     */
    public void listBooksByPrice(int price) {
        String sql = "SELECT ISBN, BookName, AuthorName, Price " +
                "FROM books " +
                "WHERE Price = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set values
            pstmt.setInt(1, price);

            ResultSet rs = pstmt.executeQuery();

            // Print results
            while(rs.next()) {
                System.out.println(rs.getInt("ISBN") + "\t" +
                        rs.getString("BookName") + "\t" +
                        rs.getString("AuthorName") + "\t" +
                        rs.getInt("Price"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Deletes a book from the database
     *
     * @param ISBN is the Primary Key of the book to be deleted
     */
    public void removeBook(int ISBN) {
        String sql = "DELETE FROM books WHERE ISBN = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Set value
            pstmt.setInt(1, ISBN);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Update a book's name
     *
     * @param ISBN of the book
     * @param replacement string for field
     */
    public void replaceBookName(int ISBN, String replacement) {
        String sql = "UPDATE books SET BookName = ? WHERE ISBN = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Set values
            pstmt.setString(1, replacement);
            pstmt.setInt(2, ISBN);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Update a book's author
     *
     * @param ISBN of the book
     * @param replacement string for field
     */
    public void replaceAuthor(int ISBN, String replacement) {
        String sql = "UPDATE books SET AuthorName = ? WHERE ISBN = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Set values
            pstmt.setString(1, replacement);
            pstmt.setInt(2, ISBN);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Update a book's Price
     *
     * @param ISBN of the book
     * @param replacement integer for field
     */
    public void replacePrice(int ISBN, int replacement) {
        String sql = "UPDATE books SET Price = ? WHERE ISBN = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Set values
            pstmt.setInt(1, replacement);
            pstmt.setInt(2, ISBN);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
