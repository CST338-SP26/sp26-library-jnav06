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

}
