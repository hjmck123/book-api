/**
 * Created by Harrison McKenzie on 29/11/17.
 *
 * Note: Tests should be run one at a time, top to bottom or they will fail.
 */


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class SQLiteTest {

    // Copy the existing output stream, and replace it with a ByteArrayOutputStream,
    // to capture console output.
    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static PrintStream oldOut;

    @BeforeClass
    public static void setUpStreams() {
        oldOut = System.out;
        System.setOut(new PrintStream(outContent));
    }

    @AfterClass
    public static void cleanUpStreams() {
        System.setOut(oldOut);
    }

    SQLite db = new SQLite("testing");

    @Test
    public void testInsertBooks() {
        db.insertBook(1234, "Book", "Author", 34);
        db.insertBook(4321, "Alice in Wonderland", "Alice", 64);
        db.insertBook(5234, "Star Wars", "Darth Vader", 400);
        db.insertBook(8593, "Histories", "Herodotus", 20);
    }

    @Test
    public void testListBooks() {
        db.listBooks();
        assertEquals("Database connected\r\n" +
                "1234\tBook\tAuthor\t34\r\n" +
                "4321\tAlice in Wonderland\tAlice\t64\r\n" +
                "5234\tStar Wars\tDarth Vader\t400\r\n" +
                "8593\tHistories\tHerodotus\t20\r\n", outContent.toString());
    }

    @Test
    public void testListBooksByISBN() {
        db.listBooksByISBN(4321);
        assertEquals("Database connected\r\n" +
                "4321\tAlice in Wonderland\tAlice\t64\r\n", outContent.toString());
    }

    @Test
    public void testListBooksByName() {
        db.listBooksByName("Alice in Wonderland");
        assertEquals("Database connected\r\n" +
                "4321\tAlice in Wonderland\tAlice\t64\r\n", outContent.toString());
    }

    @Test
    public void testListBooksByAuthor() {
        db.listBooksByAuthor("Alice");
        assertEquals("Database connected\r\n" +
                "4321\tAlice in Wonderland\tAlice\t64\r\n", outContent.toString());
    }

    @Test
    public void testListBooksByPrice() {
        db.listBooksByPrice(64);
        assertEquals("Database connected\r\n" +
                "4321\tAlice in Wonderland\tAlice\t64\r\n", outContent.toString());
    }

    @Test
    public void testRemoveBook() {
        db.removeBook(1234);
        db.listBooks();
        assertEquals("Database connected\r\n" +
                "4321\tAlice in Wonderland\tAlice\t64\r\n" +
                "5234\tStar Wars\tDarth Vader\t400\r\n" +
                "8593\tHistories\tHerodotus\t20\r\n", outContent.toString());
    }

    @Test
    public void testReplaceBookName() {
        db.replaceBookName(5234, "Star Trek");
        db.listBooksByISBN(5234);
        assertEquals("Database connected\r\n" +
                "5234\tStar Trek\tDarth Vader\t400\r\n", outContent.toString());
    }

    @Test
    public void testReplaceAuthor() {
        db.replaceAuthor(4321, "Bob");
        db.listBooksByISBN(4321);
        assertEquals("Database connected\r\n" +
                "4321\tAlice in Wonderland\tBob\t64\r\n", outContent.toString());
    }

    @Test
    public void testReplacePrice() {
        db.replacePrice(8593, 77);
        db.listBooksByISBN(8593);
        assertEquals("Database connected\r\n" +
                "8593\tHistories\tHerodotus\t77\r\n", outContent.toString());
    }
}
