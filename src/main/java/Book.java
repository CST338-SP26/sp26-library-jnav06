import java.time.LocalDate;

/**
 * @author Josue Nava - Jimenez
 * Book.java simply represents a book object.
 * My favorite book is Animal Farm by George Orwell.
 */

public class Book {

    // yummy array index stuff

    public static final int ISBN_ = 0;
    public static final int TITLE_ = 1;
    public static final int SUBJECT_ = 2;
    public static final int PAGE_COUNT_ = 3;
    public static final int AUTHOR_ = 4;
    public static final int DUE_DATE_ = 5;

    private String author;
    private LocalDate dueDate;
    private String isbn;
    private int pageCount;
    private String subject;
    private String title;

    public Book(String author, LocalDate dueDate, String isbn, int pageCount, String subject, String title) {
        this.author = author;
        this.dueDate = dueDate;
        this.isbn = isbn;
        this.pageCount = pageCount;
        this.subject = subject;
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getPageCount() {
        return pageCount;
    }

    public String getSubject() {
        return subject;
    }

    public String getTitle() {
        return title;
    }

    public int hashCode() {
        return 0; // placeholder for now
    }

    public void setAuthor (String author) {
        this.author = author;
    }

    public void setDueDate (LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setISBN (String isbn) {
        this.isbn = isbn;
    }

    public void setPageCount (int pageCount) {
        this.pageCount = pageCount;
    }

    public void setSubject (String subject) {
        this.subject = subject;
    }

    public void setTitle (String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "idk";
    }

}
