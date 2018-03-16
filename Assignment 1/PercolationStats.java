/******************************************************************************
 *  Compilation:  javac-algs4 PercolationStats.java
 *  Execution:    java-algs4 PercolationStats 100(n) 100(trials)
 *  Dependencies: Percolation.java
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;
public class PercolationStats {
    private final double mean;
    private final double stddev;
    private final double confidenceLo;
    private final double confidenceHi;
    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n <= 0 or trials <= 0");
        }
        double[] results = new double[trials];
        for (int k = 0; k < trials; k++) {
            int times = 0;
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int i = 1 + StdRandom.uniform(n);
                int j = 1 + StdRandom.uniform(n);
                if (!percolation.isOpen(i, j)) {
                    percolation.open(i, j);
                    times++;
                }
            }
            results[k] = (double) times / (n*n);
        }
        mean = StdStats.mean(results);
        stddev = StdStats.stddev(results);
        confidenceLo = mean - 1.96 * stddev / Math.sqrt(trials);
        confidenceHi = mean + 1.96 * stddev / Math.sqrt(trials);
    }
    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }
    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }
    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return confidenceLo;
    }
    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return confidenceHi;
    }
    // test client (described below)
    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

        String confidence = "[" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]";
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = " + confidence);
    }
}