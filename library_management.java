import java.util.Date;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

/**
 * The Student class is a simple data structure (a blueprint) to hold information
 * about each student. It acts like a container for student details.
 */
class Student {
    String name;
    int id_no;
    String Stream;
    String book1, book2; // Stores the names of the books the student has issued.
    int book_no; // A counter for the number of books currently issued.
    int issuedbook; // This variable seems declared but not used in the logic.

    /**
     * Constructor for the Student class.
     * It's called when a new Student object is created and initializes its properties.
     * @param name The student's name.
     * @param id_no The student's unique ID number.
     * @param Stream The student's academic stream (e.g., "B.Tech-ICT").
     */
    Student(String name, int id_no, String Stream) {
        this.name = name;
        this.id_no = id_no;
        this.Stream = Stream;
    }
}

/**
 * This is the main class for the Library Management System.
 * It contains the core logic for managing books (using a Binary Search Tree),
 * handling student data, and processing user interactions.
 */
public class library_management {

    /**
     * Sorts an array of Student objects based on their ID number in ascending order.
     * This uses the Selection Sort algorithm.
     * @param arr The array of Student objects to be sorted.
     */
    static void Selectionsort(Student arr[]) {
        int n = arr.length;

        // Iterate through the array to find the minimum element in the unsorted part.
        for (int i = 0; i < n - 1; i++) {
            // Assume the current element is the minimum.
            int min_idx = i;
            for (int j = i + 1; j < n; j++)
                // If we find a student with a smaller ID, update the minimum index.
                if (arr[j].id_no < arr[min_idx].id_no)
                    min_idx = j;

            // Swap the found minimum element with the first element of the unsorted part.
            // We swap the entire Student object to keep all details (name, ID, etc.) consistent.
            Student temp = arr[min_idx];
            arr[min_idx] = arr[i];
            arr[i] = temp;
        }
    }

    /**
     * Displays the details of all students in the provided array.
     * @param arr The array of Student objects.
     */
    static void display(Student arr[]) {
        for (int i = 0; i < arr.length; i++) {
            System.out.println("\nName of Student:" + arr[i].name);
            System.out.println("Id of Student:" + arr[i].id_no);
            System.out.println("Stream of Student:" + arr[i].Stream);
        }
    }

    /**
     * The Node class represents a single node in the Binary Search Tree.
     * Each node holds a book's name (the key) and references to its left and right children.
     */
    class Node {
        String key; // The key is the name of the book.
        Node left, right;

        public Node(String item) {
            key = item;
            left = null;
            right = null;
        }
    }

    // 'root' is the starting point of the Binary Search Tree for books.
    Node root;
    private static Scanner input;

    /**
     * Constructor for the library_management class.
     * Initializes the tree by setting the root to null, indicating an empty tree.
     */
    library_management() {
        root = null;
    }

    /**
     * Public method to insert a new book into the tree.
     * This is the method that's called from outside the class.
     * @param key The name of the book to add.
     */
    void insert(String key) {
        root = insertRec(root, key);
    }

    /**
     * A recursive helper method to find the correct position and insert a new node (book).
     * It ensures the tree remains sorted alphabetically (case-insensitive).
     * @param root The current node in the traversal.
     * @param key The name of the book to insert.
     * @return The node after insertion, which helps in linking the tree back together.
     */
    Node insertRec(Node root, String key) {
        // If the tree is empty or we've found an empty spot, create a new node here.
        if (root == null) {
            root = new Node(key);
            return root;
        }

        // Compare the new book's name with the current node's name (case-insensitive).
        int cmp = key.compareToIgnoreCase(root.key);

        // If the new book's name comes before the current node's name alphabetically, go left.
        if (cmp < 0)
            root.left = insertRec(root.left, key);
            // If it comes after, go right.
        else if (cmp > 0)
            root.right = insertRec(root.right, key);

        // If cmp is 0, the book already exists, so we do nothing.
        return root;
    }

    /**
     * Updates a book's name by deleting the old one and inserting the new one.
     * Note: This only changes the name in the tree, not associated quantity data.
     * @param key The current name of the book.
     * @param key1 The new name for the book.
     */
    void update(String key, String key1) {
        deleteKey(key);
        insert(key1);
    }

    /**
     * Public method to check if a book exists in the tree.
     * @param value The name of the book to search for.
     * @return true if the book is found, false otherwise.
     */
    public boolean containsNode(String value) {
        return containsNodeRecursive(root, value);
    }

    /**
     * A recursive helper method to search for a book in the tree.
     * @param current The current node being checked.
     * @param key The name of the book to find.
     * @return true if the book is found, false otherwise.
     */
    private boolean containsNodeRecursive(Node current, String key) {
        // If we reach a null node, the book isn't in this branch.
        if (current == null) {
            return false;
        }
        // If the names match (case-insensitive), we found it!
        if (key.equalsIgnoreCase(current.key)) {
            return true;
        }

        // Decide whether to search in the left or right subtree based on alphabetical order.
        int cmp = key.compareToIgnoreCase(current.key);
        return cmp < 0
                ? containsNodeRecursive(current.left, key)
                : containsNodeRecursive(current.right, key);
    }

    /**
     * Public method to print a 2D representation of the tree (rotated 90 degrees).
     */
    void printTree() {
        printTreeRec(root, 0);
    }

    /**
     * A recursive helper method to print the tree structure.
     * It performs a reverse in-order traversal to display the tree visually.
     * @param t The current node to print.
     * @param space The amount of space to print for indentation, creating the tree structure.
     */
    void printTreeRec(Node t, int space) {
        if (t == null)
            return;

        // Increase distance between levels
        space += 5;

        // Process the right child first (since we're printing rotated).
        printTreeRec(t.right, space);

        System.out.println();
        for (int i = 5; i < space; i++)
            System.out.print(" ");
        System.out.print("[" + t.key + "]");

        // Process the left child.
        printTreeRec(t.left, space);
    }

    /**
     * Public method to delete a book from the tree.
     * @param key The name of the book to delete.
     */
    void deleteKey(String key) {
        root = deleteRec(root, key);
    }

    /**
     * A recursive helper method to find and delete a book node.
     * It handles three cases: node is a leaf, node has one child, or node has two children.
     * @param root The current node in the traversal.
     * @param key The name of the book to delete.
     * @return The modified node after deletion.
     */
    Node deleteRec(Node root, String key) {
        if (root == null) return root;

        int cmp = key.compareToIgnoreCase(root.key);

        // Traverse the tree to find the node to delete.
        if (cmp < 0)
            root.left = deleteRec(root.left, key);
        else if (cmp > 0)
            root.right = deleteRec(root.right, key);
        else {
            // Case 1 & 2: Node with only one child or no child.
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;

            // Case 3: Node with two children.
            // Find the in-order successor (smallest node in the right subtree).
            root.key = minValue(root.right);
            // Delete the in-order successor from its original position.
            root.right = deleteRec(root.right, root.key);
        }

        return root;
    }

    /**
     * Finds the smallest key (book name) in a given subtree.
     * This is used to find the in-order successor when deleting a node with two children.
     * @param root The root of the subtree to search.
     * @return The smallest key value.
     */
    String minValue(Node root) {
        String minv = root.key;
        while (root.left != null) {
            minv = root.left.key;
            root = root.left;
        }
        return minv;
    }

    /**
     * Prints all the books in alphabetical order (in-order traversal).
     * @param node The current node.
     */
    void printInorder(Node node) {
        if (node == null)
            return;

        printInorder(node.left);
        System.out.print(node.key + "      ");
        printInorder(node.right);
    }

    // Wrapper for the printInorder method.
    void printInorder() {
        printInorder(root);
    }

    /**
     * The main method, which is the entry point of the program.
     * It sets up the initial data and handles the main menu logic.
     */
    public static void main(String[] args) throws Exception {

        // Initialize scanner for user input.
        input = new Scanner(System.in);
        // Create an instance of our library management system.
        library_management tree = new library_management();
        // A HashMap to map a book's name (String) to an index (Integer) in the 'arr' array.
        // This allows quick lookup of a book's quantity data.
        HashMap<String, Integer> hashmapping = new HashMap<>();
        // Formatter for displaying dates and times in a readable format.
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();

        // Create an array to hold 3 Student objects for demonstration.
        Student[] array = {
         new Student("Prabhas", 3373, "B.Tech-CSM");
        }

        // A 2D array to store book quantities.
        // arr[i][0] = total quantity of book 'i'.
        // arr[i][1] = available quantity of book 'i'.
        int[][] arr = new int[100][2];

        // Set up FileWriters and BufferedWriters to save data to files.
        // Using multiple writers for the same file ('append.txt') isn't standard practice,
        // but the code is kept as is. It's better to use one writer object.
        FileWriter fr = new FileWriter("append.txt", true);
        BufferedWriter br = new BufferedWriter(fr); // For student transaction logs.

        // These writers are for saving book data. It's more efficient to have one writer per file.
        FileWriter fr1 = new FileWriter("x.txt", true); // For book names.
        BufferedWriter br1 = new BufferedWriter(fr1);
        FileWriter fr2 = new FileWriter("y.txt", true); // For total book quantities.
        BufferedWriter br2 = new BufferedWriter(fr2);
        FileWriter fr3 = new FileWriter("z.txt", true); // For available book quantities.
        BufferedWriter br3 = new BufferedWriter(fr3);

        // BufferedReader objects to load existing book data from files at startup.
        BufferedReader reader = null; // Reads book names from x.txt
        BufferedReader reader2 = null; // Reads total quantities from y.txt
        BufferedReader reader3 = null; // Reads available quantities from z.txt

        // Safely open files only if they exist to prevent errors.
        try {
            File fx = new File("x.txt");
            if (fx.exists()) reader = new BufferedReader(new FileReader(fx));
            File fy = new File("y.txt");
            if (fy.exists()) reader2 = new BufferedReader(new FileReader(fy));
            File fz = new File("z.txt");
            if (fz.exists()) reader3 = new BufferedReader(new FileReader(fz));
        } catch (Exception e) {
            System.out.println("Error opening book data files. Starting with an empty library.");
        }

        // Variables to manage dates for issuing and returning books.
        Date Rday1 = null, Rday2 = null, Cday = null;
        boolean e1 = false; // Controls the main application loop.

        int i = 0; // A counter used as an index for new books.

        // Display login credentials for the admin/librarian.
        System.out.println("====================================");
        System.out.println(" Admin Login Credentials:");
        System.out.println(" ID: Admin@123");
        System.out.println(" Password: abc123");
        System.out.println("====================================");

        // Main application loop.
        while (e1 == false) {
            System.out.println("\n.....................................");
            System.out.println("1. Librarian Login. ");
            System.out.println("2. User Login. ");
            System.out.println("3. Exit. ");
            System.out.println("\n.....................................");

            System.out.println("\nEnter Your choice:");
            int ch1 = input.nextInt();

            switch (ch1) {
                case 1: // Librarian Login
                    String pwd1 = "abc123";
                    String id1 = "Admin@123";

                    System.out.println("\nEnter UserId:");
                    String id2 = input.next();

                    System.out.println("\nEnter Password:");
                    String pwd2 = input.next();

                    // Check credentials.
                    if (!id1.equals(id2) || !pwd1.equals(pwd2)) {
                        System.out.println("Invalid Userid or Password.");
                    } else {
                        System.out.println("Login successful.");
                        boolean e2 = false; // Loop control for librarian menu.
                        while (e2 == false) {
                            System.out.println("\n.....................................");
                            System.out.println("1. Add book. ");
                            System.out.println("2. Delete book. ");
                            System.out.println("3. Update book. ");
                            System.out.println("4. Print Books Details. ");
                            System.out.println("5. Print Books in-order. ");
                            System.out.println("6. Print tree ");
                            System.out.println("7. Exit");
                            System.out.println("\n.....................................");

                            System.out.println("\nEnter Your choice:");
                            int ch2 = input.nextInt();

                            switch (ch2) {
                                case 1: // Add a book
                                    // First, load any existing books from files into memory.
                                    String line;
                                    if (reader != null) {
                                        while ((line = reader.readLine()) != null) {
                                            tree.insert(line);
                                            hashmapping.put(line, i);
                                            i++;
                                        }
                                    }
                                    // Load total quantities.
                                    if (reader2 != null) {
                                        String number;
                                        int o = 0;
                                        while ((number = reader2.readLine()) != null) {
                                            arr[o][0] = Integer.parseInt(number.trim());
                                            o++;
                                        }
                                    }
                                    // Load available quantities.
                                    if (reader3 != null) {
                                        String number1;
                                        int pq = 0;
                                        while ((number1 = reader3.readLine()) != null) {
                                            arr[pq][1] = Integer.parseInt(number1.trim());
                                            pq++;
                                        }
                                    }

                                    System.out.println("\nEnter name of book:");
                                    String name = input.next();
                                    boolean z1 = tree.containsNode(name);

                                    if (z1 == true) {
                                        System.out.println("\nThis book already exists.");
                                    } else {
                                        System.out.println("\nEnter quantity of book:");
                                        int quantity = input.nextInt();

                                        // Write new book data to the files for persistence.
                                        br1.write(name);
                                        br1.newLine();
                                        br2.write(String.valueOf(quantity));
                                        br2.newLine();
                                        br3.write(String.valueOf(quantity));
                                        br3.newLine();

                                        // Add book to the in-memory data structures.
                                        tree.insert(name);
                                        hashmapping.put(name, i);
                                        arr[i][0] = quantity; // Total quantity
                                        arr[i][1] = quantity; // Available quantity
                                        i++;
                                    }
                                    break;

                                case 2: // Delete a book
                                    System.out.println("\nEnter name of book:");
                                    String b1 = input.next();

                                    if (tree.containsNode(b1)) {
                                        tree.deleteKey(b1);
                                        hashmapping.remove(b1);
                                        // Note: The quantity data in 'arr' is now orphaned but will be overwritten
                                        // if a new book is added at this index. A better design would remove it properly.
                                        System.out.println("Book '" + b1 + "' deleted.");
                                    } else {
                                        System.out.println("Book not found.");
                                    }
                                    break;

                                case 3: // Update book quantity
                                    System.out.println("\nEnter name of book:");
                                    String b2 = input.next();

                                    if (tree.containsNode(b2)) {
                                        // Get the index for this book from the HashMap.
                                        Integer a = hashmapping.get(b2);
                                        if (a != null) {
                                            System.out.println("\nEnter quantity of books to add:");
                                            int q = input.nextInt();
                                            arr[a][0] += q; // Increase total quantity.
                                            arr[a][1] += q; // Increase available quantity.
                                            System.out.println("Quantity updated.");
                                        } else {
                                            System.out.println("Book mapping error.");
                                        }
                                    } else {
                                        System.out.println("Book not found.");
                                    }
                                    break;

                                case 4: // Print Books Details
                                    System.out.println("\n--- Book Inventory ---");
                                    Set<Entry<String, Integer>> setOfEntries = hashmapping.entrySet();

                                    for (Entry<String, Integer> entry : setOfEntries) {
                                        int r = entry.getValue(); // Get the index from HashMap.
                                        System.out.println("Name: " + entry.getKey());
                                        System.out.println("Total Quantity: " + arr[r][0]);
                                        System.out.println("Available Quantity: " + arr[r][1]);
                                        System.out.println();
                                    }
                                    break;

                                case 5: // Print Books in-order
                                    System.out.println("\n--- Books in Alphabetical Order ---");
                                    tree.printInorder();
                                    System.out.println();
                                    break;

                                case 6: // Print binary search tree structure
                                    System.out.println("\n--- Book Tree Structure ---");
                                    tree.printTree();
                                    break;

                                case 7: // Exit librarian menu
                                    e2 = true;
                                    break;
                            }
                        }
                    }
                    break;

                case 2: // For User Login
                    boolean e3 = false; // Loop control for user menu.
                    while (e3 == false) {
                        System.out.println("\n.....................................");
                        System.out.println("1. Issue book. ");
                        System.out.println("2. Return book. ");
                        System.out.println("3. Exit");
                        System.out.println("\n.....................................");

                        System.out.println("\nEnter Your choice:");
                        int ch3 = input.nextInt();

                        switch (ch3) {
                            case 1: // Issue a book
                                int index = -1;
                                System.out.println("\nEnter your student id:");
                                int id = input.nextInt();

                                // Find the student with the matching ID.
                                for (int k = 0; k < 3; k++) {
                                    if (array[k].id_no == id)
                                        index = k;
                                }

                                if (index != -1) { // If student found.
                                    // Check if the student has already issued the maximum number of books (2).
                                    if (array[index].book_no == 2) {
                                        System.out.println("\nYou can't issue more than two books.");
                                    } else {
                                        System.out.println("\nEnter name of book to issue:");
                                        String book = input.next();

                                        if (tree.containsNode(book)) {
                                            Integer a = hashmapping.get(book); // Get book index.
                                            if (a == null) {
                                                System.out.println("Book mapping error.");
                                            } else {
                                                // Check if the book is available.
                                                if (arr[a][1] > 0) {
                                                    // Assign the book to the student's first or second book slot.
                                                    if (array[index].book1 == null)
                                                        array[index].book1 = book;
                                                    else
                                                        array[index].book2 = book;

                                                    System.out.println("Book issued successfully.");
                                                    arr[a][1]--; // Decrement available quantity.
                                                    array[index].book_no++; // Increment student's issued book count.

                                                    // Record the transaction details.
                                                    Cday = cal.getTime();
                                                    System.out.println("Current Date Time : " + formatter.format(Cday));
                                                    // Set a due date (5 seconds from now for demonstration).
                                                    cal.add(Calendar.SECOND, 5);
                                                    Rday1 = cal.getTime();
                                                    System.out.println("Due Date Time: " + formatter.format(Rday1));

                                                    // Write the log to the transaction file.
                                                    br.write("\nStudent name:   " + array[index].name);
                                                    br.write("\nStudent ID  :   " + array[index].id_no);
                                                    br.write("\nIssued Book :   " + book);
                                                    br.write("\nIssued date :   " + formatter.format(Cday));
                                                    br.write("\nReturn date :   " + formatter.format(Rday1));
                                                    br.newLine();
                                                } else {
                                                    System.out.println("Book is currently unavailable. Try again later.");
                                                }
                                            }
                                        } else {
                                            System.out.println("This book is not in our library.");
                                        }
                                    }
                                } else {
                                    System.out.println("Invalid Student ID.");
                                }
                                break;

                            case 2: // Return a book
                                int ind = -1;
                                System.out.println("\nEnter your student id:");
                                int s_id = input.nextInt();
                                System.out.println("\nEnter name of book to return:");
                                String Rbook = input.next();

                                // Find the student and verify they have the book.
                                for (int k = 0; k < 3; k++) {
                                    if (array[k].id_no == s_id) {
                                        boolean hasBook1 = array[k].book1 != null && array[k].book1.equalsIgnoreCase(Rbook);
                                        boolean hasBook2 = array[k].book2 != null && array[k].book2.equalsIgnoreCase(Rbook);
                                        if (hasBook1 || hasBook2) {
                                            ind = k;
                                        }
                                    }
                                }

                                if (ind != -1) { // If student found and has the book.
                                    // Remove the book from the student's record.
                                    if (array[ind].book1 != null && array[ind].book1.equalsIgnoreCase(Rbook))
                                        array[ind].book1 = null;
                                    else if (array[ind].book2 != null && array[ind].book2.equalsIgnoreCase(Rbook))
                                        array[ind].book2 = null;

                                    // Check for late fees.
                                    cal = Calendar.getInstance();
                                    Rday2 = cal.getTime(); // Current time of return.

                                    if (Rday1 != null && Rday2.after(Rday1)) {
                                        System.out.println("Book is overdue.");
                                        long diff = Rday2.getTime() - Rday1.getTime(); // Difference in milliseconds.
                                        int noofseconds = (int) (diff / 1000); // Convert to seconds for demo.
                                        double charge = noofseconds * 5; // Calculate fine (5 Rs per second).
                                        System.out.println("Book is delayed by " + noofseconds + " seconds.");
                                        System.out.println("Your late fee is: " + charge + " Rs.");
                                    } else {
                                        System.out.println("Book returned successfully on time.");
                                    }

                                    // Update the library's inventory.
                                    Integer a = hashmapping.get(Rbook);
                                    if (a != null) {
                                        arr[a][1]++; // Increment available quantity.
                                    }
                                    array[ind].book_no--; // Decrement student's issued book count.

                                } else {
                                    System.out.println("Invalid ID or you have not issued this book.");
                                }
                                break;

                            case 3: // Exit user menu
                                e3 = true;
                                break;
                        }
                    }
                    break;

                case 3: // Exit the application
                    e1 = true;
                    break;
            }
        }
        // Safely close all file writers and readers to ensure data is saved
        // and resources are released.
        try { if (br != null) br.close(); } catch (Exception ex) {}
        try { if (fr != null) fr.close(); } catch (Exception ex) {}
        try { if (br1 != null) br1.close(); } catch (Exception ex) {}
        try { if (fr1 != null) fr1.close(); } catch (Exception ex) {}
        try { if (br2 != null) br2.close(); } catch (Exception ex) {}
        try { if (fr2 != null) fr2.close(); } catch (Exception ex) {}
        try { if (br3 != null) br3.close(); } catch (Exception ex) {}
        try { if (fr3 != null) fr3.close(); } catch (Exception ex) {}
        try { if (reader != null) reader.close(); } catch (Exception ex) {}
        try { if (reader2 != null) reader2.close(); } catch (Exception ex) {}
        try { if (reader3 != null) reader3.close(); } catch (Exception ex) {}
        System.out.println("Library system shut down. Goodbye!");
    }
}
