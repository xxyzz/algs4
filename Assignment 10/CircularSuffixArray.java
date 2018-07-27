/******************************************************************************
 *  Compilation:  javac-algs4 CircularSuffixArray.java
 *  Execution:    java-algs4 CircularSuffixArray
 *  Dependencies:
 *
 *  a sorted array of the n circular suffixes of a string of length n
 *
 ******************************************************************************/
import edu.princeton.cs.algs4.StdOut;

public class CircularSuffixArray {
    // cutoff to insertion sort
    private static final int CUTOFF = 15;
    // length of input string
    private final int n;
    private int[] index;
    private final String s;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        // Corner case
        if (s == null) throw new IllegalArgumentException("Argument is null");
        this.s = s;
        n = s.length();
        index = new int[n];
        for (int i = 0; i < n; i++) {
            index[i] = i;
        }
        sort(0, n - 1, 0);
    }

    private int charAt(int firstIndex, int d) {
        if (d < n && d >= 0) return s.charAt((firstIndex + d) % n);
        else return -1;
    }

    // thyree-way string quicksort
    private void sort(int lo, int hi, int d) {
        // cutoff to insertion sort for small subarrays
        if (hi <= lo + CUTOFF) {
            insertion(lo, hi, d);
            return;
        }

        int lt = lo, gt = hi;
        int v = charAt(index[lo], d);
        int i = lo + 1;
        while (i <= gt) {
            int t = charAt(index[i], d);
            if (t < v) exch(lt++, i++);
            else if (t > v) exch(i, gt--);
            else i++;
        }
        // a[lo..lt-1] < v = a[lt..gt] < a[gt+1..hi]
        sort(lo, lt-1, d);
        if (v >= 0) sort(lt, gt, d+1);
        sort(gt+1, hi, d);
    }

    // sort from index[lo] to index[hi], starting at the dth character
    private void insertion(int lo, int hi, int d) {
        for (int i = lo; i <= hi; i++)
            for (int j = i; j > lo && less(j, j - 1, d); j--)
                exch(j, j - 1);
    }

    // is v less than w, starting at character d
    private boolean less(int v, int w, int d) {
        for (int i = d; i < n; i++) {
            if (charAt(index(v), d) < charAt(index(w), d)) return true;
            if (charAt(index(v), d) > charAt(index(w), d)) return false;
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
        CircularSuffixArray circularSuffixArray = new CircularSuffixArray("ABRACADABRA!");
        StdOut.println("length: " + circularSuffixArray.length());
        StdOut.println("index(0): " + circularSuffixArray.index(0));
    }
}