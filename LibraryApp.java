import java.util.*;

/**
 * LibraryApp — Simple Java console program to practice Data Structures & Algorithms.
 * Author: Bryan Gomez Esparza
 * Date: Spring 2025
 */
public class LibraryApp {

    static class Book {
        int id;
        String title;
        String author;
        int year;

        Book(int id, String title, String author, int year) {
            this.id = id;
            this.title = title;
            this.author = author;
            this.year = year;
        }

        @Override
        public String toString() {
            return String.format("[%d] %s — %s (%d)", id, title, author, year);
        }
    }

    static class BST {
        static class Node {
            Book book;
            Node left, right;
            Node(Book b) { this.book = b; }
        }
        private Node root;
        public void insert(Book b) { root = insertRec(root, b); }
        private Node insertRec(Node cur, Book b) {
            if (cur == null) return new Node(b);
            if (b.title.compareToIgnoreCase(cur.book.title) < 0)
                cur.left = insertRec(cur.left, b);
            else cur.right = insertRec(cur.right, b);
            return cur;
        }
        public void inorder(List<Book> out) { inorderRec(root, out); }
        private void inorderRec(Node n, List<Book> out) {
            if (n == null) return;
            inorderRec(n.left, out);
            out.add(n.book);
            inorderRec(n.right, out);
        }
    }

    static class Algorithms {
        public static List<String> mergeSort(List<String> data) {
            if (data.size() <= 1) return new ArrayList<>(data);
            int mid = data.size() / 2;
            List<String> left = mergeSort(data.subList(0, mid));
            List<String> right = mergeSort(data.subList(mid, data.size()));
            return merge(left, right);
        }
        private static List<String> merge(List<String> a, List<String> b) {
            List<String> out = new ArrayList<>();
            int i = 0, j = 0;
            while (i < a.size() && j < b.size()) {
                if (a.get(i).compareToIgnoreCase(b.get(j)) <= 0) out.add(a.get(i++));
                else out.add(b.get(j++));
            }
            while (i < a.size()) out.add(a.get(i++));
            while (j < b.size()) out.add(b.get(j++));
            return out;
        }
        public static int binarySearch(List<String> sorted, String key) {
            int lo = 0, hi = sorted.size() - 1;
            while (lo <= hi) {
                int mid = (lo + hi) / 2;
                int cmp = sorted.get(mid).compareToIgnoreCase(key);
                if (cmp == 0) return mid;
                if (cmp < 0) lo = mid + 1; else hi = mid - 1;
            }
            return -1;
        }
    }

    static class BookCatalog {
        private final List<Book> books = new ArrayList<>();
        private final BST byTitleBST = new BST();

        public void add(Book b) {
            books.add(b);
            byTitleBST.insert(b);
        }
        public List<Book> listInOrder() {
            List<Book> out = new ArrayList<>();
            byTitleBST.inorder(out);
            return out;
        }
        public List<String> titlesSorted() {
            List<String> titles = new ArrayList<>();
            for (Book b : books) titles.add(b.title);
            return Algorithms.mergeSort(titles);
        }
        public int searchTitle(String title) {
            return Algorithms.binarySearch(titlesSorted(), title);
        }
    }

    public static void main(String[] args) {
        BookCatalog catalog = new BookCatalog();
        catalog.add(new Book(1, "Algorithms Unlocked", "Cormen", 2013));
        catalog.add(new Book(2, "Clean Code", "Robert C. Martin", 2008));
        catalog.add(new Book(3, "The Pragmatic Programmer", "Hunt & Thomas", 1999));

        System.out.println("Books in order by title:");
        for (Book b : catalog.listInOrder()) System.out.println(b);

        System.out.println("\nSorted titles:");
        for (String t : catalog.titlesSorted()) System.out.println(t);

        String key = "Clean Code";
        System.out.println("\nBinary Search for '" + key + "': " +
            (catalog.searchTitle(key) >= 0 ? "Found" : "Not found"));
    }
}
