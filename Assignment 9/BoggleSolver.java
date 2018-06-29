/******************************************************************************
 *  Compilation:  javac-algs4 BoggleSolver.java
 *  Execution:    java-algs4 BoggleSolver dictionary-algs4.txt board4x4.txt
 *  Dependencies: In.java StdOut.java MyTrieST.java SET.java
 *
 *  Find all valid words in a given Boggle board, using a given dictionary.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stopwatch;

public class BoggleSolver {
    private final MyTrieST<Integer> dicST;
    private SET<String> wordST;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        if (dictionary == null) throw new IllegalArgumentException("Argument is null");
        dicST = new MyTrieST<Integer>();
        for (int i = 0; i < dictionary.length; i++) {
            dicST.put(dictionary[i], i);
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        if (board == null) throw new IllegalArgumentException("Argument is null");
        wordST = new SET<String>();
        for (int i = 0; i < board.rows(); i++) {
            for (int j = 0; j < board.cols(); j++) {
                boolean[] marked = new boolean[board.rows() * board.cols()];
                dfs(board, i, j, marked, "");
            }
        }
        return wordST;
    }

    // depth-first search
    private void dfs(BoggleBoard board, int row, int col, boolean[] marked, String word) {
        int index = row * board.cols() + col;
        if (marked[index]) return;

        char letter = board.getLetter(row, col);
        if (letter == 'Q') word += "QU";
        else word += letter;

        if (!dicST.hasKeysWithPrefix(word)) return;

        if (word.length() > 2 && dicST.contains(word) && !wordST.contains(word)) wordST.add(word);

        // mark as visited only after dictionary has words with this prefix
        marked[index] = true;

        // search adjacent dices
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }

                if ((row + i >= 0) && (row + i < board.rows()) && (col + j >= 0) && (col + j < board.cols())) {
                    dfs(board, row + i, col + j, marked, word);
                }
            }
        }

        // change to not visited for another path
        marked[index] = false;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (word == null) throw new IllegalArgumentException("Argument is null");
        int wordLength = word.length();
        if (wordLength < 3) return 0;
        if (dicST.contains(word)) {
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
        Stopwatch stopwatch = new Stopwatch();
        BoggleSolver solver = new BoggleSolver(dictionary);
        int score = 0;
        Iterable<String> words = solver.getAllValidWords(board);
        StdOut.println("Time for solver: " + Double.toString(stopwatch.elapsedTime()));
        for (String word : words) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}