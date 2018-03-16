/******************************************************************************
 *  Compilation:  javac-algs4 Percolation.java
 *  Execution:    java-algs4 Percolation n row col
 *  Dependencies: 
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
    private boolean[][] opened;
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF ufCheckFull;
    private final int n;
    private int openedNumber;
    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        uf = new WeightedQuickUnionUF((n + 1) * (n + 1));
        ufCheckFull = new WeightedQuickUnionUF((n + 1) * (n + 1));
        opened = new boolean[n + 1][n + 1];
        openedNumber = 0;
    }
    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        checkIndex(row, col);
        if (isOpen(row, col)) {
            return;
        }
        opened[row][col] = true;
        openedNumber++;

        if (row == 1) {
            // Connect to top
            uf.union(0, row * (n + 1) + col);
            ufCheckFull.union(0, row * (n + 1) + col);
        }
        if (row == n) {
            // Connect to bottom
            uf.union(1, row * (n + 1) + col);
        }
        if (row != 1 && isOpen(row - 1, col)) {
            // up
            uf.union(row * (n + 1) + col, (row - 1) * (n + 1) + col);
            ufCheckFull.union(row * (n + 1) + col, (row - 1) * (n + 1) + col);
        }
        if (row != n && isOpen(row + 1, col)) {
            // down
            uf.union(row * (n + 1) + col, (row + 1) * (n + 1) + col);
            ufCheckFull.union(row * (n + 1) + col, (row + 1) * (n + 1) + col);
        }
        if (col != 1 && isOpen(row, col - 1)) {
            // left
            uf.union(row * (n + 1) + col, row * (n + 1) + col - 1);
            ufCheckFull.union(row * (n + 1) + col, row * (n + 1) + col - 1);
        }
        if (col != n && isOpen(row, col + 1)) {
            // right
            uf.union(row * (n + 1) + col, row * (n + 1) + col + 1);
            ufCheckFull.union(row * (n + 1) + col, row * (n + 1) + col + 1);
        }
    }
    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkIndex(row, col);
        return opened[row][col];
    }
    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        checkIndex(row, col);
        return ufCheckFull.connected(0, row * (n + 1) + col);
    }
    // number of open sites
    public int numberOfOpenSites() {
        return openedNumber;
    }
    // does the system percolate?
    public boolean percolates() {
        return uf.connected(0, 1);
    }
    // test client (optional)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int row = Integer.parseInt(args[1]);
        int col = Integer.parseInt(args[2]);
        Percolation percolation = new Percolation(n);
        percolation.open(row, col);
    }

    private void checkIndex(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException();
        }
    }
}