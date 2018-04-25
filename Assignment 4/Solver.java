/******************************************************************************
 *  Compilation:  javac-algs4 Solver.java
 *  Execution:    java-algs4 Solver
 *  Dependencies: Board.java
 *  
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stack;
import java.util.Comparator;

public class Solver {
    private final SearchNode lastNode;
    private final Comparator<SearchNode> priority = new Priority();

    private class SearchNode {
        private final Board board;
        private final int moves;
        private final SearchNode prev;
        // cache manhattan
        private final int manhattan;
        public SearchNode(Board board, int moves, SearchNode prev, int manhattan) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
            this.manhattan = manhattan;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        // Corner cases
        if (initial == null) {
            throw new IllegalArgumentException("null argument");
        }
        // define MinPQ inside constructor to reduce memory
        MinPQ<SearchNode> pq1 =new MinPQ<SearchNode>(priority);
        MinPQ<SearchNode> pq2 = new MinPQ<SearchNode>(priority);
        pq1.insert(new SearchNode(initial, 0, null, initial.manhattan()));
        pq2.insert(new SearchNode(initial.twin(), 0, null, initial.twin().manhattan()));
        SearchNode node1;
        SearchNode node2;
        while (!pq1.min().board.isGoal() && !pq2.min().board.isGoal()) {
            node1 = pq1.delMin();
            node2 = pq2.delMin();
            for (Board board : node1.board.neighbors()) {
                if (node1.prev == null || !board.equals(node1.prev.board)) {
                    pq1.insert(new SearchNode(board, node1.moves + 1, node1, board.manhattan()));
                }
            }
            for (Board board : node2.board.neighbors()) {
                if (node2.prev == null || !board.equals(node2.prev.board)) {
                    pq2.insert(new SearchNode(board, node2.moves + 1, node2, board.manhattan()));
                }
            }
        }
        // unsolvable
        if (!pq1.min().board.isGoal()) {
            lastNode = null;
        }
        else {
            lastNode = pq1.min();
        }
    }
    // is the initial board solvable?
    public boolean isSolvable() {
        return lastNode != null;
    }
    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) {
            return -1;
        }
        return lastNode.moves;
    }
    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }
        SearchNode node = lastNode;
        Stack<Board> boards = new Stack<Board>();
        while (node != null) {
            boards.push(node.board);
            node = node.prev;
        }
        // last in first out
        return boards;
    }

    private class Priority implements Comparator<SearchNode> {
        public int compare(SearchNode a, SearchNode b) {
            int priority1 = a.manhattan + a.moves;
            int priority2 = b.manhattan + b.moves;
            if (priority1 == priority2) {
                if (a.manhattan == b.manhattan) {
                    return 0;
                }
                else if (a.manhattan < b.manhattan) {
                    return -1;
                }
                else {
                    return 1;
                }
            }
            else if (priority1 < priority2) {
                return -1;
            }
            else {
                return 1;
            }
        }
    }

    // solve a slider puzzle
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
    
        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}