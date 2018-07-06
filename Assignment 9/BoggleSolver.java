/******************************************************************************
 *  Compilation:  javac-algs4 BoggleSolver.java
 *  Execution:    java-algs4 BoggleSolver dictionary-algs4.txt board4x4.txt
 *  Dependencies: In.java StdOut.java
 *
 *  Find all valid words in a given Boggle board, using a given dictionary.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Stopwatch;

public class BoggleSolver {
    private static final int R = 26;        // A - Z
    private Bag<String> wordBag;
    private boolean[] marked;
    private char[] letters;
    private int cols;
    private int rows;
    private Node root;      // root of trie
    private Node lastNode;
    private String lastPrefix;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        if (dictionary == null) throw new IllegalArgumentException("Argument is null");
        String[] copy = dictionary.clone(); // defensive copy
        for (int i = 0; i < copy.length; i++) {
            put(copy[i], 0);
        }
    }

    // R-way trie node
    private static class Node {
        private int val = -1;
        private Node[] next = new Node[R];
    }

    private int nonrecursiveGet(String key) {
        if (root == null) return -1;
        Node x = nonrecursiveGet(root, key);
        if (x == null) return -1;
        return x.val;
    }

    private Node nonrecursiveGet(Node x, String key) {
        for (int i = 0; i < key.length(); i++) {
            x = x.next[key.charAt(i) - 'A'];
            if (x == null) return null;
        }
        return x;
    }

    private void put(String key, int val) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");
        else root = put(root, key, val, 0);
    }

    private Node put(Node x, String key, int val, int d) {
        if (x == null) x = new Node();
        if (d == key.length()) {
            x.val = val;
            return x;
        }
        char c = key.charAt(d);
        x.next[c - 'A'] = put(x.next[c - 'A'], key, val, d + 1);
        return x;
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        if (board == null) throw new IllegalArgumentException("Argument is null");
        // reset
        wordBag = new Bag<String>();
        cols = board.cols();
        rows = board.rows();
        marked = new boolean[cols * rows];
        letters = new char[cols * rows];
        lastNode = root;
        lastPrefix = "";
        for (int i = 0; i < cols * rows; i++) {
            letters[i] = board.getLetter(i / cols, i % cols);
        }

        for (int i = 0; i < cols * rows; i++) {
            dfs(i, "");
        }
        return wordBag;
    }

    // depth-first search
    private void dfs(int i, String word) {
        if (marked[i]) return;

        char letter = letters[i];
        if (letter == 'Q') word += "QU";
        else word += letter;

        int wordL = word.length();
        Node x;

        if (wordL - lastPrefix.length() == 2) {
            x = nonrecursiveGet(lastNode, "QU");
        }
        else if (wordL > 1 && lastPrefix.equals(word.substring(0, wordL - 1))) {
            x = nonrecursiveGet(lastNode, word.substring(wordL - 1));
        }
        else x = nonrecursiveGet(root, word);

        if (x != null) {
            lastNode = x;
            lastPrefix = word;
        }
        else return;

        if (word.length() > 2 && x.val == 0) {
            wordBag.add(word);
            x.val = 1; // mark added, avoid adding repeated word
        }

         // mark as visited only after dictionary has words with this prefix
        marked[i] = true;

        // down
        if (i / cols < rows - 1) dfs(i + cols, word);
        // up
        if (i - cols >= 0) dfs(i - cols, word);

        if (i % cols != 0) {
            dfs(i - 1, word); // left
            if (i / cols != 0) dfs(i - cols - 1, word); // up left
            if (i / cols != rows - 1) dfs(i + cols - 1, word); // down left
        }

        if (i % cols < cols - 1) {
            dfs(i + 1, word); // right
            if (i / cols != 0) dfs(i - cols + 1, word); // up right
            if (i / cols != rows - 1) dfs(i + cols + 1, word); // down right
        }

        // change to not visited for another path
        marked[i] = false;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (word == null) throw new IllegalArgumentException("Argument is null");
        int wordLength = word.length();
        if (wordLength < 3) return 0;
        if (nonrecursiveGet(word) != -1) {
            if (wordLength >= 8) return 11;
            else if (wordLength == 7) return 5;
            else if (wordLength == 6) return 3;
            else if (wordLength == 5) return 2;
            else return 1;
        }
        return 0;
    }

    // test
    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleBoard board = new BoggleBoard(args[1]);
        BoggleSolver solver = new BoggleSolver(dictionary);
        int score = 0, count = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
            count++;
        }
        StdOut.println("Score = " + score + " count: " + count);
        BoggleBoard board2 = new BoggleBoard(args[2]);
        score = 0;
        count = 0;
        for (String word : solver.getAllValidWords(board2)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
            count++;
        }
        StdOut.println("Score = " + score + " count: " + count);
        // In in = new In(args[0]);
        // String[] dictionary = in.readAllStrings();
        // BoggleBoard board;
        // if (args.length == 2) board = new BoggleBoard(args[1]);
        // else board = new BoggleBoard();
        // BoggleSolver solver = new BoggleSolver(dictionary);
        // int score = 0, count = 0;
        // Stopwatch stopwatch = new Stopwatch();
        // for (String word : solver.getAllValidWords(board)) {
        //     StdOut.println(word);
        //     score += solver.scoreOf(word);
        //     count++;
        // }
        // StdOut.println("Score = " + score + " count: " + count);
        // StdOut.println("Time for solver: " + Double.toString(stopwatch.elapsedTime()));
    }
}