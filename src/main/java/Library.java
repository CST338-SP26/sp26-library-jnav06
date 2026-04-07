import Utilities.Code;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import java.time.LocalDate;

/**
 * @author Josue Nava - Jimenez
 * Library.java is the real meat and potatoes of the Library project! If you probably hate fun, then this file is the core of the entire library system.
 * This amazing library keeps track of books, readers, and shelves. Some technologies like HashMaps are utilized.
 */


public class Library {

    public static final int LENDING_LIMIT = 5;

    private String name;
    private static int libraryCard = 0;

    private List<Reader> readers;
    private HashMap<String, Shelf> shelves;
    private HashMap<Book, Integer> books;

    /**
     * the constructor. initializes name (input btw), readers, shelves, and books
     */
    public Library(String name) {
        this.name = name;

        this.readers = new ArrayList<>();
        this.shelves = new HashMap<>();
        this.books = new HashMap<>();
    }

    /**
     * adds a book to the library (self-explanatory)
     */
    public Code addBook(Book newBook) {
        if (books.containsKey(newBook)) {
            books.put(newBook, books.get(newBook) + 1);

            System.out.println(books.get(newBook) + " copies of " + newBook.getTitle() + " in the stacks");
        }
        else {
            books.put(newBook, 1);
            System.out.println(newBook.getTitle() + " added to the stacks.");
        }

        if (shelves.get(newBook.getSubject()) == null) {
            System.out.println("No shelf for " + newBook.getSubject() + " books");
            return Code.SHELF_EXISTS_ERROR;
        }

        return shelves.get(newBook.getSubject()).addBook(newBook);
    }

    /**
     * adds a reader to the library (in terms of users)
     */
    public Code addReader(Reader reader) {
        if (readers.contains(reader)) {
            System.out.println(reader.getName() + " already has an account!");
            return Code.READER_ALREADY_EXISTS_ERROR;
        }

        for (Reader current : readers) {
            if (current.getCardNumber() == reader.getCardNumber()) {
                System.out.println(current.getName() + " and " + reader.getName() + " have the same card number!");
                return Code.READER_CARD_NUMBER_ERROR;
            }
        }

        readers.add(reader);
        System.out.println(reader.getName() + " added to the library!");

        if (reader.getCardNumber() > libraryCard) {
            libraryCard = reader.getCardNumber();
        }

        return Code.SUCCESS;
    }

    /**
     * adds a shelf to the library and places matching books onto it
     */
    public Code addShelf(Shelf shelf) {
        if (shelves.containsKey(shelf.getSubject())) {
            System.out.println("ERROR: Shelf already exists " + shelf);
            return Code.SHELF_EXISTS_ERROR;
        }

        shelves.put(shelf.getSubject(), shelf);

        for (Book current : books.keySet()) {
            if (current.getSubject().equals(shelf.getSubject())) {
                for (int i = 0; i < books.get(current); i++) {
                    shelf.addBook(current);
                }
            }
        }

        return Code.SUCCESS;
    }

    /**
     * adds a shelf to the library based on subject
     */
    public Code addShelf(String subject) {
        return addShelf(new Shelf(shelves.size() + 1, subject));
    }

    /**
     * checks out a book to a reader
     */
    public Code checkOutBook(Reader reader, Book book) { // this one was really scary. never again
        Code output; // i need this for the bottom 2 methods

        if (!readers.contains(reader)) {
            System.out.println(reader.getName() + " doesn't have an account here");
            return Code.READER_NOT_IN_LIBRARY_ERROR;
        }

        if (reader.getBookCount() >= LENDING_LIMIT) {
            System.out.println(reader.getName() + " has reached the lending limit, (" + LENDING_LIMIT + ")");
            return Code.BOOK_LIMIT_REACHED_ERROR;
        }

        if (!books.containsKey(book)) {
            System.out.println("ERROR: could not find " + book);
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        }

        if (shelves.get(book.getSubject()) == null) {
            System.out.println("no shelf for " + book.getSubject() + " books!");
            return Code.SHELF_EXISTS_ERROR;
        }

        if (shelves.get(book.getSubject()).getBookCount(book) < 1) {
            System.out.println("ERROR: no copies of " + book + " remain");
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        }

        output = reader.addBook(book);
        if (output != Code.SUCCESS) {
            System.out.println("Couldn't checkout " + book);
            return output;
        }

        output = shelves.get(book.getSubject()).removeBook(book);
        if (output == Code.SUCCESS) {
            System.out.println(book + " checked out successfully");
        }

        return output;
    }

    /**
     * converts a date from String to LocalDate
     */
    public LocalDate convertDate(String date, Code code) {
        try {
            return LocalDate.parse(date);
        } catch(Exception e) {
            return null;
        }
    }

    /**
     * converts a String to an integer
     */
    public int convertInt(String recordCountString, Code code) {
        try {
            return Integer.parseInt(recordCountString);
        } catch(Exception e) {
            return code.getCode();
        }
    }

    /**
     * returns a specific book from an isbn
     */
    public Book getBookByISBN(String isbn) {
        for (Book current : books.keySet()) {
            if (current.getISBN().equals(isbn)) {
                return current;
            }
        }

        return null;
    }

    /**
     * returns a reader's library card number
     */
    public static int getLibraryCardNumber() {
        return libraryCard + 1;
    }

    /**
     * returns the name of the library. does this make sense? not to me! i'm tired
     */
    public String getName() {
        return name;
    }

    /**
     * returns a specific reader from their card
     */
    public Reader getReaderByCard(int cardNumber) { // i just wanna say thank god foreach loops exist they make my life so easy
        for (Reader current : readers) {
            if (current.getCardNumber() == cardNumber) {
                return current;
            }
        }

        System.out.println("Could not find a reader with card #" + cardNumber);
        return null;
    }

    /**
     * checks the validity of a shelf (i.e. does it exist?) and returns it based on its subject
     */
    public Shelf getShelf(String subject) {
        if (shelves.get(subject) == null) {
            System.out.println("No shelf for " + subject + " books");
        }

        return shelves.get(subject);
    }

    /**
     * returns a shelf based on its shelf number
     */
    public Shelf getShelf(Integer shelfNumber) {
        for (Shelf current : shelves.values()) {
            if (current.getShelfNumber() == shelfNumber) {
                return current;
            }
        }

        System.out.println("No shelf number " + shelfNumber + " found");
        return null;
    }

    /**
     * hahaha... initializes the entire library from a file (String path)
     */
    public Code init(String filename) {
        try {
            Scanner sc = new Scanner(new FileReader(filename));

            // i really hate to say it but typically i don't like creating many variables
            // especially those that redundant. but unfortunately making 3 variables (book, shelf, reader count) fixed my issue. i made like 6 variables here
            // and i hate it. but i need that sweet 21 out of 21 tests passed message. that is the sole thing that is keeping me going right now

            // it is 2am help me

            int bookCount = convertInt(sc.nextLine(), Code.BOOK_COUNT_ERROR);
            if (bookCount < 0) {
                return Code.BOOK_COUNT_ERROR;
            }

            Code bookResult = initBooks(bookCount, sc);
            if (bookResult != Code.SUCCESS) {
                return bookResult;
            }

            int shelfCount = convertInt(sc.nextLine(), Code.SHELF_COUNT_ERROR);
            if (shelfCount < 0) {
                return Code.SHELF_COUNT_ERROR;
            }

            Code shelfResult = initShelves(shelfCount, sc);
            if (shelfResult != Code.SUCCESS) {
                return shelfResult;
            }

            int readerCount = convertInt(sc.nextLine(), Code.READER_COUNT_ERROR);
            if (readerCount < 0) {
                return Code.READER_COUNT_ERROR;
            }

            // used to be way less simple, but thank you intellij for suggesting i simply this a little :)
            // intellij: you're welcome
            return initReader(readerCount, sc);

        } catch (Exception e) {
            return Code.FILE_NOT_FOUND_ERROR;
        }
    }

    /**
     * initializes all books from a file
     */
    public Code initBooks(int count, Scanner sc) {
        for (int i = 0; i < count; i++) {
            String[] snippets = sc.nextLine().split(",");

            if (snippets.length <= Book.DUE_DATE_) {
                return Code.BOOK_RECORD_COUNT_ERROR;
            }

            addBook(new Book(snippets[Book.ISBN_], snippets[Book.TITLE_], snippets[Book.SUBJECT_], convertInt(snippets[Book.PAGE_COUNT_],
                    Code.PAGE_COUNT_ERROR), snippets[Book.AUTHOR_], convertDate(snippets[Book.DUE_DATE_], Code.DATE_CONVERSION_ERROR)));
        }

        return Code.SUCCESS;
    }

    /**
     * initializes users/readers from a file
     */
    public Code initReader(int count, Scanner sc) {
        for (int i = 0; i < count; i++) {
            String[] snippets = sc.nextLine().split(",");

            Reader reader = new Reader(convertInt(snippets[Reader.CARD_NUMBER_], Code.READER_CARD_NUMBER_ERROR), snippets[Reader.NAME_], snippets[Reader.PHONE_]);
            addReader(reader);

            for (int j = 0; j < convertInt(snippets[Reader.BOOK_COUNT_], Code.BOOK_COUNT_ERROR); j++) {
                if ((getBookByISBN(snippets[Reader.BOOK_START_ + j])) != null) {
                    reader.addBook(getBookByISBN(snippets[Reader.BOOK_START_ + j]));
                }
            }
        }

        return Code.SUCCESS;
    }

    /**
     * initializes shelves from a file
     */
    public Code initShelves(int count, Scanner sc) { // i had to rewrite this as well. i really hated this
        for (int i = 0; i < count; i++) {
            String[] snippets = sc.nextLine().split(",");

            if ((convertInt(snippets[Shelf.SHELF_NUMBER_], Code.SHELF_NUMBER_PARSE_ERROR)) < 0) {
                return Code.SHELF_NUMBER_PARSE_ERROR; // bro i do not know what the heck i did but i fixed it
            }

            addShelf(new Shelf((convertInt(snippets[Shelf.SHELF_NUMBER_], Code.SHELF_NUMBER_PARSE_ERROR)), snippets[Shelf.SUBJECT_]));
        }

        return Code.SUCCESS;
    }

    /**
     * lists all books and the total count :)
     */
    public int listBooks() {
        int total = 0;

        for (Book current : books.keySet()) {
            total += books.get(current);

            System.out.println(books.get(current) + " copies of " + current);
        }

        return total;
    }

    /**
     * lists all users/readers (optional to show their books)
     */
    public int listReaders(boolean showBooks) {
        for (Reader current : readers) {
            if (showBooks) {
                System.out.println(current.getName() + " (#" + current.getCardNumber() + ") has the following books:");
                System.out.println(current.getBooks());
            }
            else {
                System.out.println(current);
            }
        }

        return readers.size();
    }

    /**
     * returns a list of all the readers
     */
    public int listReaders() {
        return listReaders(false);
    }

    /**
     * lists all shelves (optional to show their books)
     */
    public int listShelves(boolean showBooks) {
        for (Shelf current : shelves.values()) {
            if (showBooks) {
                System.out.print(current.listBooks());
            }
            else {
                System.out.println(current);
            }
        }

        return shelves.size();
    }

    /**
     * returns a list of all the shelves
     */
    public int listShelves() {
        return listShelves(false);
    }

    /**
     * removes a reader from the library system
     */
    public Code removeReader(Reader reader) {
        if (!readers.contains(reader)) {
            System.out.println(reader + " is not part of this Library");
            return Code.READER_NOT_IN_LIBRARY_ERROR;
        }

        if (reader.getBookCount() > 0) {
            System.out.println(reader.getName() + " must return all books!");
            return Code.READER_STILL_HAS_BOOKS_ERROR;
        }

        readers.remove(reader);
        return Code.SUCCESS;
    }

    /**
     * system to return a single book
     */
    public Code returnBook(Reader reader, Book book) {
        if (!reader.hasBook(book)) {
            System.out.println(reader.getName() + " doesn't have " + book.getTitle() + " checked out");
            return Code.READER_DOESNT_HAVE_BOOK_ERROR;
        }

        if (!books.containsKey(book)) {
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        }

        System.out.println(reader.getName() + " is returning " + book);

        if (reader.removeBook(book) != Code.SUCCESS) {
            System.out.println("Could not return " + book);
            return reader.removeBook(book);
        }

        return returnBook(book);
    }

    /**
     * returns a book to its shelf
     */
    public Code returnBook(Book book) {
        if (shelves.get(book.getSubject()) == null) {
            System.out.println("No shelf for " + book);
            return Code.SHELF_EXISTS_ERROR;
        }

        return shelves.get(book.getSubject()).addBook(book);
    }

}
