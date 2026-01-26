import java.util.ArrayList;

/**
 * Class Library to represent the library functionality
 */
public class Library {
    /**
     * Implementation of the books collection 
     */
    private ArrayList<Book> books = new ArrayList<>();

    /**
     * @param book: Add a new book
     */
    public void addBook(Book book) {
        for (Book b : books) {
            if (b.getIsbn().equals(book.getIsbn())) {
                System.out.println("❌ Book with this ISBN already exists.");
                return;
            }
        }
        books.add(book);
        System.out.println("✅ Book added successfully.");
    }

    /**
     * @param isbn: Remove book by ISBN
     */
    public void removeBook(String isbn) {
        Book toRemove = null;
        for (Book b : books) {
            if (b.getIsbn().equals(isbn)) {
                toRemove = b;
                break;
            }
        }
        if (toRemove != null) {
            books.remove(toRemove);
            System.out.println("✅ Book removed.");
        } else {
            System.out.println("❌ Book not found.");
        }
    }

    /**
     * Method to Print all books
     */
    public void displayAllBooks() {
        if (books.isEmpty()) {
            System.out.println("📂 No books in the library.");
        } else {
            for (Book b : books) {
                System.out.println(b);
            }
        }
    }

    /**
     * @param title: method to find books by title
     */
    public void searchByTitle(String title) {
        boolean found = false;
        for (Book b : books) {
            if (b.getTitle().toLowerCase().contains(title.toLowerCase())) {
                System.out.println(b);
                found = true;
            }
        }
        if (!found) System.out.println("🔍 No books found with that title.");
    }

    /**
     * @param author: method to Find books by author
     */
    public void searchByAuthor(String author) {
        boolean found = false;
        for (Book b : books) {
            if (b.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                System.out.println(b);
                found = true;
            }
        }
        if (!found) System.out.println("🔍 No books found by that author.");
    }

    /**
     * @param isbn: method to Mark book as checked out
     */
    public void checkOutBook(String isbn) {
        for (Book b : books) {
            if (b.getIsbn().equals(isbn)) {
                if (b.isAvailable()) {
                    b.setAvailable(false);
                    System.out.println("📕 Book checked out.");
                } else {
                    System.out.println("❌ Book is already checked out.");
                }
                return;
            }
        }
        System.out.println("❌ Book not found.");
    }

    /**
     * @param isbn: method to Mark book as available
     */
    public void returnBook(String isbn) {
        for (Book b : books) {
            if (b.getIsbn().equals(isbn)) {
                if (!b.isAvailable()) {
                    b.setAvailable(true);
                    System.out.println("📗 Book returned.");
                } else {
                    System.out.println("❌ Book was not checked out.");
                }
                return;
            }
        }
        System.out.println("❌ Book not found.");
    }
}