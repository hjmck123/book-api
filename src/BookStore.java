/**
 * Created by Harrison McKenzie on 29/11/17.
 */
public interface BookStore {

    void addBook(int ISBN, String BookName, String Author, int Price);

    void listBooks();
    void listBooks(String fieldName, String field);
    void listBooks(String fieldName, int field);

    void removeBook(int ISBN);

    void renameField(int ISBN, String fieldName, String field);
    void renameField(int ISBN, String fieldName, int field);
}
