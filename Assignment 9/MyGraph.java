/******************************************************************************
 *  Compilation:  javac MyGraph.java
 *  Execution:
 *  Dependencies: StdIn.java
 *
 *  the Boggle graph
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.Bag;

public class MyGraph {
    public Node[] nodes;
    public int rows;
    public int cols;

    public static class Node {
        public char letter;
        public Bag<Node> adjacentNodes;
        public boolean marked;
    }

    public MyGraph(BoggleBoard board) {
        if (board == null) throw new IllegalArgumentException("Argument is null");
        rows = board.rows();
        cols = board.cols();
        nodes = new Node[rows * cols];

        // loop board
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Node node = new Node();
                node.letter = board.getLetter(i, j);
                node.adjacentNodes = new Bag<Node>();
                node.marked = false;
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
                            nodes[index].adjacentNodes.add(nodes[(i + n) * cols + j + m]);
                        }
                    }
                }
            }
        }
    }
}