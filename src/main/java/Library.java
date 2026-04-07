import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Library {

    public static final int LENDING_LIMIT_ = 5;
    private String name;
    private static int libraryCard = 0;

    private List<Reader> readers;
    private HashMap<String, Shelf> shelves;
    private HashMap<Book, Integer> books;

    public Library(String name) {
        this.name = name;

        readers = new ArrayList<>();
        shelves = new HashMap<>();
        books = new HashMap<>();
    }
}
