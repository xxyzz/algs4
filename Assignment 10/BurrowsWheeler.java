/******************************************************************************
 *  Compilation:  javac-algs4 BurrowsWheeler.java
 *  Execution:    java-algs4 BurrowsWheeler - < burrows/abra.txt | java-algs4 BurrowsWheeler +
 *      java-algs4 BurrowsWheeler - < burrows/abra.txt | java-algs4 edu.princeton.cs.algs4.HexDump 16
 *  Dependencies: BinaryStdIn, BinaryStdOut, CircularSuffixArray
 *
 *  
 *
 ******************************************************************************/
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {
    private static final int R = 256;    // radix

    // apply Burrows-Wheeler transform, reading from standard input and writing to standard output
    public static void transform() {
        String s = BinaryStdIn.readString();
        CircularSuffixArray csa = new CircularSuffixArray(s);
        int n = csa.length();
        // first
        for (int i = 0; i < n; i++) {
            if (csa.index(i) == 0) {
                BinaryStdOut.write(i);
                break;
            }
        }

        for (int i = 0; i < n; i++) {
            BinaryStdOut.write(s.charAt((csa.index(i) + n - 1) % n));
        }
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform, reading from standard input and writing to standard output
    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();
        String tSTring = BinaryStdIn.readString();
        int n = tSTring.length();
        int[] next = new int[n], count = new int[R + 1];

        // key-indexed counting
        // Compute frequency counts.
        for (int i = 0; i < n; i++)
            count[tSTring.charAt(i) + 1]++;
        // Transform counts to indices.
        for (int r = 0; r < R; r++)
            count[r + 1] += count[r];
        // Distribute the records.
        for (int i = 0; i < n; i++)
            next[count[tSTring.charAt(i)]++] = i;
        // restore the string
        for (int i = next[first], j = 0; j < n; j++, i = next[i]) {
            BinaryStdOut.write(tSTring.charAt(i));
        }
        BinaryStdOut.close();
    }

    // if args[0] is '-', apply Burrows-Wheeler transform
    // if args[0] is '+', apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) transform();
        if (args[0].equals("+")) inverseTransform();
    }
}