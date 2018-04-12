/******************************************************************************
 *  Compilation:  javac-algs4 Board.java
 *  Execution:    java-algs4 Board ./8puzzle/puzzle04.txt
 *  Dependencies: 
 *  
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    private final int n;
    private final char[] board;
    private final int hamming;
    private final int manhattan;
    private int blank;
    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        n = blocks.length;
        board = new char[n * n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i * n + j] = (char) blocks[i][j];
                if (blocks[i][j] == 0) {
                    blank = i * n + j;
                }
            }
        }
        hamming = getHamming(board);
        manhattan = getManhattan(board);
    }

    private Board(char[] board, int n, int hamming, int manhattan, int blank) {
        this.board = board;
        this.n = n;
        this.hamming = hamming;
        this.manhattan = manhattan;
        this.blank = blank;
    }

    // board dimension n
    public int dimension() {
        return n;
    }
    // number of blocks out of place
    public int hamming() {
        return hamming;
    }

    private int getHamming(char[] charBoard) {
        int thisHamming = 0;
        for (int i = 0; i < n * n - 1; i++) {
            if (charBoard[i] != i + 1) {
                thisHamming++;
            }
        }
        return thisHamming;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        return manhattan;
    }

    private int getManhattan(char[] charBoard) {
        int thisManhattan = 0;
        for (int i = 0; i < n * n; i++) {
            if (charBoard[i] != i + 1 && charBoard[i] != 0) {
                // distances of row and col, -1 !!
                thisManhattan += Math.abs((charBoard[i] - 1) / n - i / n) + Math.abs((charBoard[i] - 1) % n - i % n);
            }
        }
        return thisManhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming == 0;
    }
    // a board that is obtained by exchanging any pair of blocks
    // Must return the same board every time when given the same argument, that's imutable board.
    public Board twin() {
        char[] copy = board.clone();
        for (int i = 0; i < n * n - 1; i++) {
            if (copy[i] != 0 && copy[i + 1] != 0) {
                exch(copy, i, i + 1);
                int thisHamming = getHamming(copy);
                int thisManhattan = getManhattan(copy);
                return new Board(copy, n, thisHamming, thisManhattan, blank);
            }
        }
        return null;
    }
    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }
        if (y == this) {
            return true;
        }
        if (y.getClass() != this.getClass()) {
            return false;
        }
        return new String(board).equals(new String(((Board) y).board));
    }
    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> boards = new Stack<>();

        // up
        if (blank / n != 0) {
            boards.push(getNeighbour(blank - n));
        }
        // down
        if (blank / n != n - 1) {
            boards.push(getNeighbour(blank + n));
        }
        // left
        if (blank % n != 0) {
            boards.push(getNeighbour(blank - 1));
        }
        // right
        if (blank % n != n - 1) {
            boards.push(getNeighbour(blank + 1));
        }

        return boards;
    }
    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n * n; i++) {
            s.append(String.format("%2d ", (int) board[i]));
            if (i % n == n - 1) {
                s.append("\n");
            }
        }
        return s.toString();
    }

    private void exch(char[] charBoard, int a, int b) {
        char temp = charBoard[a];
        charBoard[a] = charBoard[b];
        charBoard[b] = temp;
    }

    private Board getNeighbour(int i) {
        char[] copy = board.clone();
        exch(copy, blank, i);
        int thisHamming = getHamming(copy);
        int thisManhattan = getManhattan(copy);
        return new Board(copy, n, thisHamming, thisManhattan, i);
    }

    // unit tests (not graded)
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        // StdOut.println(initial);
        // StdOut.println(initial.dimension());
        // StdOut.println(initial.hamming());
        StdOut.println(initial.manhattan());
        // StdOut.println(initial.twin());
        // StdOut.println(initial.equals(new Board(blocks)));
        // Iterable<Board> result = initial.neighbors();
        // for (Board b : result) {
        //     StdOut.println(b.toString());
        // }
    }
}