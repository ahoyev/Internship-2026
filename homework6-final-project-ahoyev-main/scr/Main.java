import java.util.Scanner;

/**
 * Class Main implements the Library Menu options with input validation
 */
public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println(" LIBRARY MENU:");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. Display All Books");
            System.out.println("4. Search by Title");
            System.out.println("5. Search by Author");
            System.out.println("6. Check Out Book");
            System.out.println("7. Return Book");
            System.out.println("8. Exit");
            System.out.print("Enter choice: ");

            while (!sc.hasNextInt()) {
                System.out.print(" Invalid input. Enter a number: ");
                sc.next();
            }
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter author: ");
                    String author = sc.nextLine();
                    System.out.print("Enter ISBN: ");
                    String isbn = sc.nextLine();
                    Book book = new Book(title, author, isbn);
                    library.addBook(book);
                    break;
                case 2:
                    System.out.print("Enter ISBN to remove: ");
                    library.removeBook(sc.nextLine());
                    break;
                case 3:
                    library.displayAllBooks();
                    break;
                case 4:
                    System.out.print("Enter title to search: ");
                    library.searchByTitle(sc.nextLine());
                    break;
                case 5:
                    System.out.print("Enter author to search: ");
                    library.searchByAuthor(sc.nextLine());
                    break;
                case 6:
                    System.out.print("Enter ISBN to check out: ");
                    library.checkOutBook(sc.nextLine());
                    break;
                case 7:
                    System.out.print("Enter ISBN to return: ");
                    library.returnBook(sc.nextLine());
                    break;
                case 8:
                    System.out.println(" Exiting. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 8);

        sc.close();
    }
}
