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
        for (Node node : boardGraph.nodes) {
            dfs(node, "");
        }
        return wordSET;
    }

    // depth-first search
    private void dfs(Node node, String word) {
        if (node.marked) return;

        char letter = node.letter;
        if (letter == 'Q') word += "QU";
        else word += letter;

        if (!dicST.hasKeysWithPrefix(word)) return;

        if (word.length() > 2 && dicST.contains(word)) wordSET.add(word);

         // mark as visited only after dictionary has words with this prefix
        node.marked = true;

        for (Node adjNode: node.adjacentNodes) {
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

    private static class Node {
        private char letter;
        private ArrayList<Node> adjacentNodes;
        private boolean marked;
    }

    private static class MyGraph {
        private final Node[] nodes;
    
        public MyGraph(BoggleBoard board) {
            int rows = board.rows();
            int cols = board.cols();
            int v = rows * cols;
            nodes = new Node[v];
    
            // loop board. i / cols = row, i % cols = col
            for (int i = 0; i < v; i++) {
                Node node = new Node();
                node.letter = board.getLetter(i / cols, i % cols);
                node.adjacentNodes = new ArrayList<Node>();
                nodes[i] = node;
            }

            // adjacent dices
            for (int j = 0; j < v; j++) {
                // down
                if (j + cols < v && !nodes[j].adjacentNodes.contains(nodes[j + cols])) addAdjs(j, j + cols);
                // up
                if (j - cols >= 0 && !nodes[j].adjacentNodes.contains(nodes[j - cols])) addAdjs(j, j - cols);
                // left
                if (j % cols != 0 && !nodes[j].adjacentNodes.contains(nodes[j - 1])) addAdjs(j, j - 1);
                // right
                if (j % cols < cols - 1 && !nodes[j].adjacentNodes.contains(nodes[j + 1])) addAdjs(j, j + 1);
                // up left
                if (j % cols != 0 && j / cols != 0 && !nodes[j].adjacentNodes.contains(nodes[j - cols - 1])) addAdjs(j, j - cols - 1);
                // up right
                if (j % cols < cols - 1 && j / cols != 0 && !nodes[j].adjacentNodes.contains(nodes[j - cols + 1])) addAdjs(j, j - cols + 1);
                // down left
                if (j % cols != 0 && j / cols != rows - 1 && !nodes[j].adjacentNodes.contains(nodes[j + cols - 1])) addAdjs(j, j + cols - 1);
                // down right
                if (j % cols < cols - 1 && j / cols != rows - 1 && !nodes[j].adjacentNodes.contains(nodes[j + cols + 1])) addAdjs(j, j + cols + 1);
            }
        }

        private void addAdjs(int i, int j) {
            nodes[i].adjacentNodes.add(nodes[j]);
            nodes[j].adjacentNodes.add(nodes[i]);
        }
    }

    // test
    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleBoard board = new BoggleBoard(args[1]);
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