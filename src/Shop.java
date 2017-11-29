/**
 * Created by Harrison McKenzie on 29/11/17.
 */

public class Shop implements BookStore {

    SQLite db = new SQLite("Shop");

    public void addBook(int ISBN, String BookName, String Author, int Price) {
        db.insertBook(ISBN, BookName, Author, Price);
    }

    public void listBooks() {
        db.listBooks();
    }

    public void listBooks(String fieldName, String field) {
        if(fieldName == "BookName") {
            db.listBooksByName(field);
        } else if(fieldName == "AuthorName") {
            db.listBooksByAuthor(field);
        }
    }

    public void listBooks(String fieldName, int field) {
        if(fieldName == "ISBN") {
            db.listBooksByISBN(field);
        } else if(fieldName == "Price") {
            db.listBooksByPrice(field);
        }
    }

    public void removeBook(int ISBN) {
        db.removeBook(ISBN);
    }

    public void renameField(int ISBN, String fieldName, String field) {
        if(fieldName == "BookName") {
            db.replaceBookName(ISBN, field);
        } else if(fieldName == "AuthorName") {
            db.replaceAuthor(ISBN, field);
        }
    }

    public void renameField(int ISBN, String fieldName, int field) {
        db.replacePrice(ISBN, field);
    }
}
