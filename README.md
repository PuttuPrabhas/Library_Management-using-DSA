Library Management System
Project Overview
This is a command-line based Library Management System developed in Java. The application provides a dual-interface system for both librarians and users (students) to manage library resources efficiently. Librarians have administrative privileges to manage the book inventory, while users can issue and return books. The system uses file handling to persist data, ensuring that book lists and transaction logs are saved between sessions.

Technologies and Data Structures Used
Programming Language: Java

Core Data Structures:

Binary Search Tree (BST): Used to store and manage the list of book titles. The BST ensures that books are always kept in alphabetical order, allowing for efficient searching, insertion, and deletion operations.

HashMap: Implemented to create a link between a book's name (String) and its corresponding inventory data (like total and available quantity) stored in an array. This allows for quick lookups of book details without searching the entire inventory.

Arrays: Used to store student objects and to manage the quantities of books.

File I/O: Standard Java java.io classes (FileReader, FileWriter, BufferedReader, BufferedWriter) are used for data persistence. The system saves book names, quantities, and student transaction logs to .txt files.

Features
The application is divided into two main modules:

1. Librarian Panel
The librarian panel provides functionalities for managing the library's book collection.

Add Book: Add a new book to the library with its name and quantity.

Delete Book: Remove a book from the library's collection.

Update Book: Add more copies to an existing book's inventory.

View Book Details: Display a list of all books along with their total and currently available quantities.

Print Books In-order: List all books in alphabetical order.

Print Tree: Visualize the structure of the Binary Search Tree where the books are stored.

2. User/Student Panel
The user panel allows students to interact with the library for borrowing and returning books.

Issue Book: Students can issue a book by providing their ID and the book's name. The system checks for book availability and enforces a two-book limit per student.

Return Book: Students can return a book they have borrowed. The system also calculates and displays any late fees if a book is returned after the due date.

System Flowcharts
Librarian Workflow
This flowchart illustrates the processes available to the librarian, from logging in to managing the book inventory.
<img width="1024" height="1024" alt="Gemini_Generated_Image_s0qcz5s0qcz5s0qc" src="https://github.com/user-attachments/assets/464e4cd3-e1bf-418c-8c4f-94e5fa3c21e9" />


User Workflow
This flowchart shows the step-by-step process for a user issuing or returning a book.
<img width="896" height="1152" alt="Gemini_Generated_Image_xuszi4xuszi4xusz" src="https://github.com/user-attachments/assets/e6681bd3-e886-4675-be10-2bcffa73c894" />

How to Run the Project
Prerequisites:

Make sure you have Java Development Kit (JDK) installed on your system.

Compilation:

Open a terminal or command prompt.

Navigate to the directory where the library_management.java file is saved.

Compile the Java code using the following command:

javac library_management.java

Execution:

After successful compilation, run the program with this command:

java library_management

The application will start, and you can interact with it through the command line by following the on-screen menus.
