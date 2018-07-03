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
import java.util.ArrayList;

public class BoggleSolver {
    private final MyTrieST<Integer> dicST;
    private SET<String> wordSET;

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
        wordSET = new SET<String>();
        MyGraph boardGraph = new MyGraph(board);
        for (MyGraph.Node node : boardGraph.nodes) {
            dfs(node, "");
        }
        return wordSET;
    }

    // depth-first search
    private void dfs(MyGraph.Node node, String word) {
        if (node.marked) return;

        char letter = node.letter;
        if (letter == 'Q') word += "QU";
        else word += letter;

        if (!dicST.hasKeysWithPrefix(word)) return;

        if (word.length() > 2 && dicST.contains(word)) wordSET.add(word);

         // mark as visited only after dictionary has words with this prefix
        node.marked = true;

        for (MyGraph.Node adjNode: node.adjacentNodes) {
            dfs(adjNode, word);
        }

        // change to not visited for another path
        node.marked = false;
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

    private class MyGraph {
        private final Node[] nodes;
    
        private class Node {
            private char letter;
            private ArrayList<Node> adjacentNodes;
            private boolean marked;
        }
    
        public MyGraph(BoggleBoard board) {
            int rows = board.rows();
            int cols = board.cols();
            nodes = new Node[rows * cols];
    
            // loop board
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    Node node = new Node();
                    node.letter = board.getLetter(i, j);
                    node.adjacentNodes = new ArrayList<Node>();
                    nodes[i * cols + j] = node;
                }
            }

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    int index = i * cols + j;
                    // adjacent dices
                    for (int n = -1; n <= 1; n++) {
                        for (int m = -1; m <= 1; m++) {
                            if (n == 0 && m == 0) {
                                continue;
                            }

                            if ((i + n >= 0) && (i + n < rows) && (j + m >= 0) && (j + m < cols)) {
                                int temp = (i + n) * cols + j + m;
                                if (!nodes[index].adjacentNodes.contains(nodes[temp])) {
                                    nodes[index].adjacentNodes.add(nodes[temp]);
                                    nodes[temp].adjacentNodes.add(nodes[index]);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // test
    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleBoard board = new BoggleBoard();
        BoggleSolver solver = new BoggleSolver(dictionary);
        int score = 0, count = 0;
        Stopwatch stopwatch = new Stopwatch();
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
            count++;
        }
        StdOut.println("Score = " + score + " count: " + count);
        StdOut.println("Time for solver: " + Double.toString(stopwatch.elapsedTime()));
    }
}