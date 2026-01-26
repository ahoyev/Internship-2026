/**
 * Class Book to represent the books in Library
 */
public class Book {
    private String title;
    private String author;
    private String isbn;
    private boolean isAvailable;

    /**
     * Public constructor of Book class
     * @param title
     * @param author
     * @param isbn
     */
    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isAvailable = true;
    }

    /**
     * @return String title
     */
    public String getTitle() { return title; }
    /**
     * @return String author
     */
    public String getAuthor() { return author; }
    /**
     * @return String isbn
     */
    public String getIsbn() { return isbn; }
    /**
     * @return boolean if book available or not
     */
    public boolean isAvailable() { return isAvailable; }

    /**
     * @param set if book is available or not
     */
    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    /**
     * Override toString() method to display book details
     */
    @Override
    public String toString() {
        return "Title: " + title +
               ", Author: " + author +
               ", ISBN: " + isbn +
               ", Available: " + (isAvailable ? "Yes" : "No");
    }
}
