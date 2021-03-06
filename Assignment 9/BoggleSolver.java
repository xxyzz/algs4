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
// import edu.princeton.cs.algs4.Stopwatch;

public class BoggleSolver {
    private static final int R = 26;        // A - Z
    private Bag<String> wordBag;
    private Bag<Node> addedNodes;
    private boolean[] marked;
    private char[] letters;
    private int cols;
    private int rows;
    private Node root;      // root of trie
    private int lastPrefixL;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        if (dictionary == null) throw new IllegalArgumentException("Argument is null");
        for (int i = 0; i < dictionary.length; i++) {
            put(dictionary[i]);
        }
    }

    // R-way trie node
    private static class Node {
        private int val;
        private Node[] next = new Node[R];
    }

    private int nonrecursiveGet(String key) {
        if (root == null) return 0;
        Node x = nonrecursiveGet(root, key);
        if (x == null) return 0;
        return x.val;
    }

    private Node nonrecursiveGet(Node x, String key) {
        for (int i = 0; i < key.length(); i++) {
            x = x.next[key.charAt(i) - 'A'];
            if (x == null) return null;
        }
        return x;
    }

    private void put(String key) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");
        else root = put(root, key, 0);
    }

    private Node put(Node x, String key, int d) {
        if (x == null) x = new Node();
        if (d == key.length()) {
            x.val = 1;
            return x;
        }
        char c = key.charAt(d);
        x.next[c - 'A'] = put(x.next[c - 'A'], key, d + 1);
        return x;
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        if (board == null) throw new IllegalArgumentException("Argument is null");
        // reset
        wordBag = new Bag<String>();
        addedNodes = new Bag<Node>();
        cols = board.cols();
        rows = board.rows();
        marked = new boolean[cols * rows];
        letters = new char[cols * rows];
        lastPrefixL = 0;

        for (int i = 0; i < cols * rows; i++) {
            letters[i] = board.getLetter(i / cols, i % cols);
        }

        for (int i = 0; i < cols * rows; i++) {
            dfs(root, i, "");
        }

        // reset
        for (Node node : addedNodes) {
            node.val = 1;
        }
        return wordBag;
    }

    // depth-first search
    private void dfs(Node node, int i, String word) {
        if (marked[i]) return;

        char letter = letters[i];
        if (letter == 'Q') word += "QU";
        else word += letter;

        int wordL = word.length();

        if (wordL > 1 && word.substring(wordL - 2).equals("QU")) {
            node = nonrecursiveGet(node, "QU");
        }
        else if (wordL - lastPrefixL == 1) {
            node = node.next[word.charAt(wordL - 1) - 'A'];
        }
        else node = nonrecursiveGet(root, word);

        if (node != null) {
            lastPrefixL = wordL;
        }
        else return;

        if (word.length() > 2 && node.val == 1) {
            wordBag.add(word);
            addedNodes.add(node);
            node.val = 2; // mark added, avoid adding repeated word
        }

        // mark as visited only after dictionary has words with this prefix
        marked[i] = true;

        // down
        if (i / cols < rows - 1) dfs(node, i + cols, word);
        // up
        if (i - cols >= 0) dfs(node, i - cols, word);

        if (i % cols != 0) {
            dfs(node, i - 1, word); // left
            if (i / cols != 0) dfs(node, i - cols - 1, word); // up left
            if (i / cols != rows - 1) dfs(node, i + cols - 1, word); // down left
        }

        if (i % cols < cols - 1) {
            dfs(node, i + 1, word); // right
            if (i / cols != 0) dfs(node, i - cols + 1, word); // up right
            if (i / cols != rows - 1) dfs(node, i + cols + 1, word); // down right
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
        if (nonrecursiveGet(word) != 0) {
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
        // BoggleBoard board;
        // BoggleBoard board2;
        // if (args.length == 3) {
        // board = new BoggleBoard(args[1]);
        // board2 = new BoggleBoard(args[2]);
        // }
        // else board = new BoggleBoard();
        BoggleSolver solver = new BoggleSolver(dictionary);
        // int score = 0, count = 0;
        // // Stopwatch stopwatch = new Stopwatch();
        // for (String word : solver.getAllValidWords(board)) {
        //     StdOut.println(word);
        //     score += solver.scoreOf(word);
        //     count++;
        // }
        // StdOut.println("Score = " + score + " count: " + count);
        StdOut.println("Score of live " + solver.scoreOf("LIVE"));
        // StdOut.println("Time for solver: " + Double.toString(stopwatch.elapsedTime()));
        // count = 0;
        // score = 0;
        // for (String word : solver.getAllValidWords(board2)) {
        //     StdOut.println(word);
        //     score += solver.scoreOf(word);
        //     count++;
        // }
        // StdOut.println("Score = " + score + " count: " + count);
        // StdOut.println("Time for solver: " + Double.toString(stopwatch.elapsedTime()));
    }
}