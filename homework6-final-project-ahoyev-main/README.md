!!!!!!!!!!!!!!!!!!!!!!!!!!

Good evening!
The code is in Final.zip file, plesae use Import Eclipse feature to download the Final project with all dependencies. 

All Java Project files (e.g. Book.java) are duplicated in "scr" subfolder.

!!!!!!!!!!!!!!!!!!!!!!!!!!

[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/SrQkRDOx)
# 📚 Library Management System using ArrayLists

**Due Date:** 5/11/2025  
**Total Points:** 100

---

## ✨ Assignment Overview

In this assignment, you will create a **Library Management System** using **Java ArrayLists**.  
You will design a system that allows adding, removing, viewing, and searching for books by title or author.  
This will help you practice **object-oriented programming** and **ArrayList operations**.

---

## 💻 Learning Objectives

- ✅ Use `ArrayList` to manage a collection of objects  
- ✅ Apply object-oriented concepts by creating classes and objects  
- ✅ Implement operations like add, remove, search, and display  
- ✅ Practice writing clean, modular, and well-documented code

---

## 📄 Requirements

1. **Create a `Book` class**
    - Fields:
        - `title` (String)
        - `author` (String)
        - `isbn` (String)
        - `isAvailable` (boolean)
    - Methods:
        - Constructor to initialize fields
        - `toString()` to display book details
        - Getters and setters as needed

2. **Create a `Library` class**
    - Field:
        - `ArrayList<Book> books`
    - Methods:
        - `addBook(Book book)` → Add a new book  
        - `removeBook(String isbn)` → Remove book by ISBN  
        - `displayAllBooks()` → Print all books  
        - `searchByTitle(String title)` → Find books by title  
        - `searchByAuthor(String author)` → Find books by author  
        - `checkOutBook(String isbn)` → Mark book as checked out  
        - `returnBook(String isbn)` → Mark book as available

3. **Create a `Main` class with a menu**
    ```
    1. Add Book
    2. Remove Book
    3. Display All Books
    4. Search by Title
    5. Search by Author
    6. Check Out Book
    7. Return Book
    8. Exit
    ```

4. **Handle input and errors gracefully**
    - Prevent adding duplicate ISBNs
    - Show user-friendly messages for invalid actions

---


## ✅ Grading Rubric

| Criteria                              | Points  |
|---------------------------------------|---------|
| Book and Library classes implemented  | 20      |
| All required methods working          | 25      |
| Console menu functional and clear     | 25      |
| Error handling and input validation   | 15      |
| Code readability and documentation    | 15      |
| **Total**                             | **100** |

---

## 💬 Questions?

If you have questions, post them on class Teams channel or email me or our class Learning Assistant.
