/******************************************************************************
 *  Compilation:  javac-algs4 CircularSuffixArray.java
 *  Execution:    java-algs4 CircularSuffixArray
 *  Dependencies:
 *
 *  a sorted array of the n circular suffixes of a string of length n
 *
 ******************************************************************************/
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class CircularSuffixArray {
    // cutoff to insertion sort
    private static final int CUTOFF = 15;
    // length of input string
    private final int n;
    private int[] index;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        // Corner case
        if (s == null) throw new IllegalArgumentException("Argument is null");
        n = s.length();
        index = new int[n];
        for (int i = 0; i < n; i++) {
            index[i] = i;
        }
        sort(s, 0, n - 1, 0);
    }

    private int charAt(String s, int firstIndex, int d) {
        if (d < n && d >= 0) return s.charAt((firstIndex + d) % n);
        else return -1;
    }

    // thyree-way string quicksort
    private void sort(String s, int lo, int hi, int d) {
        // cutoff to insertion sort for small subarrays
        if (hi <= lo + CUTOFF) {
            insertion(s, lo, hi, d); 
            return;
        }

        int lt = lo, gt = hi;
        int v = charAt(s, index[lo], d);
        int i = lo + 1;
        while (i <= gt) {
            int t = charAt(s, index[i], d);
            if (t < v) exch(lt++, i++);
            else if (t > v) exch(i, gt--);
            else i++;
        }
        // a[lo..lt-1] < v = a[lt..gt] < a[gt+1..hi]
        sort(s, lo, lt-1, d);
        if (v >= 0) sort(s, lt, gt, d+1);
        sort(s, gt+1, hi, d);
    }

    // sort from index[lo] to index[hi], starting at the dth character
    private void insertion(String s, int lo, int hi, int d) {
        for (int i = lo; i <= hi; i++)
            for (int j = i; j > lo && less(s, j, j - 1, d); j--)
                exch(j, j - 1);
    }

    // is v less than w, starting at character d
    private boolean less(String s, int v, int w, int d) {
        int indexV = index[v], indexW = index[w];
        for (int i = d; i < n; i++) {
            if (charAt(s, indexV, i) < charAt(s, indexW, i)) return true;
            if (charAt(s, indexV, i) > charAt(s, indexW, i)) return false;
        }
        return false;
    }

    // exchange index[i] and index[j]
    private void exch(int i, int j) {
        int temp = index[i];
        index[i] = index[j];
        index[j] = temp;
    }

    // length of s
    public int length() {
        return n;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        // Corner case
        if (i < 0 || i > n - 1) throw new IllegalArgumentException("Outside string length range");
        return index[i];
    }

    // unit testing (required)
    public static void main(String[] args) {
        String s = BinaryStdIn.readString();
        CircularSuffixArray circularSuffixArray = new CircularSuffixArray(s);
        BinaryStdOut.write("length: " + circularSuffixArray.length() + "\n");
        for (int i = 0; i < circularSuffixArray.length(); i++) {
            BinaryStdOut.write(circularSuffixArray.index(i) + " ");
        }
        BinaryStdOut.close();
    }
}