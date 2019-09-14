# 1.1 Basic Programming Model

## EXPERIMENTS

- 1.1.35 *Dice simulation*. The following code computes the exact probability distribution for the sum of two dice:

```java
int SIDES = 6;
double[] dist = new double[2*SIDES+1];
for (int i = 1; i <= SIDES; i++)
    for (int j = 1; j <= SIDES; j++)
        dist[i+j] += 1.0;
for (int k = 2; k <= 2*SIDES; k++)
       dist[k] /= 36.0;
```

The value `dist[i]` is the probability that the dice sum to `k`. Run experiments to validate this calculation simulating *N* dice throws, keeping track of the frequencies of occurrence of each value when you compute the sum of two random integers between 1 and 6. How large does *N* have to be before your empirical results match the exact results to three decimal places?

```java
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;

public class DiceSimulation {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);

        int SIDES = 6;
        double[] dist = new double[2*SIDES+1];
        for (int i = 1; i <= SIDES; i++)
            for (int j = 1; j <= SIDES; j++)
                dist[i+j] += 1.0;

        double[] simuDist = new double[2*SIDES+1];
        for (int i = 0; i < n; i++) {
            int a = StdRandom.uniform(1, SIDES + 1);
            int b = StdRandom.uniform(1, SIDES + 1);
            simuDist[a+b] += 1;
        }

        for (int k = 2; k <= 2*SIDES; k++) {
            dist[k] /= 36.0;
            simuDist[k] /= n;
        }

        StdOut.printf("dist: %s\n\n", Arrays.toString(dist));
        StdOut.printf("simuDist: %s\n", Arrays.toString(simuDist));
    }
}
```

```
$ javac-algs4 *.java
$ java-algs4 DiceSimulation 500000
dist: [0.0, 0.0, 0.027777777777777776, 0.05555555555555555, 0.08333333333333333, 0.1111111111111111, 0.1388888888888889, 0.16666666666666666, 0.1388888888888889, 0.1111111111111111, 0.08333333333333333, 0.05555555555555555, 0.027777777777777776]

simuDist: [0.0, 0.0, 0.027556, 0.055452, 0.083672, 0.111062, 0.138312, 0.166586, 0.138906, 0.111528, 0.083482, 0.055484, 0.02796]
```

- 1.1.36 *Empirical shuffle check*. Run computational experiments to check that our shuffling code on page 32 works as advertised. Write a program `ShuffleTest` that takes command-line arguments *M* and *N*, does *N* shuffles of an array of size *M* that is initialized with `a[i] = i` before each shuffle, and prints an `M-by-M` table such that row `i` gives the number of times `i` wound up in position `j` for all `j`. All entries in the array should be close to *N/M*.

```java
import java.util.Arrays;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;;

public class ShuffleTest {
    public static void shuffle(double[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++)  {  // Exchange a[i] with random element in a[i..N-1]
            int r = i + StdRandom.uniform(N-i);
            double temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    public static void main(String[] args) {
        int m = Integer.parseInt(args[0]);
        int n = Integer.parseInt(args[1]);
        double[] arr = new double[m];
        int[][] table = new int[m][m]; 

        for (int i = 0; i < m; i++)
            arr[i] = i;

        for (int i = 0; i < n; i++) {
            shuffle(arr);
            for (int j = 0; j < m; j++)
                table[(int)arr[j]][j] += 1;
        }

        StdOut.println(Arrays.toString(arr));
        StdOut.println(Arrays.deepToString(table));
    }
}
```

- 1.1.37 *Bad shuffling*. Suppose that you choose a random integer between `0` and `N-1` in our shuffling code instead of one between `i` and `N-1`. Show that the resulting order is *not* equally likely to be one of the *N!* possibilities. Run the test of the previous exercise for this version.

- 1.1.38 *Binary search versus brute-force search*. Write a program `BruteForceSearch` that uses the brute-force search method given on page 48 and compare its running time on your computer with that of `BinarySearch` for `largeW.txt` and `largeT.txt`.

- 1.1.39 *Random matches*. Write a `BinarySearch` client that takes an `int` value `T` as command-line argument and runs *T* trials of the following experiment for *N* = 10<sup>3</sup>, 10<sup>4</sup>, 10<sup>5</sup>, and 10<sup>6</sup>: generate two arrays of *N* randomly generated positive six-digit `int` values, and find the number of values that appear in both arrays. Print a table giving the average value of this quantity over the *T* trials for each value of *N*.

```java
import java.util.Arrays;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;;

public class BinarySearch {
    public static int rank(int key, int[] a) {  // Array must be sorted.
        int lo  = 0;
        int hi = a.length - 1;
        while (lo <= hi) {  // Key is in a[lo..hi] or not present.
            int mid = lo + (hi - lo) / 2;
            if      (key < a[mid]) hi = mid - 1;
            else if (key > a[mid]) lo = mid + 1;
            else                   return mid;
        }
        return -1;
    }

    public static int[] generateArray(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++)
            arr[i] = StdRandom.uniform((int)Math.pow(10, 6));
        return arr;
    }

    public static void main(String[] args) {
        int t = Integer.parseInt(args[0]);
        int[] count = new int[4];

        for (int i = 0; i < t; i++) {
            for (int j = 3; j <= 6; j++) {
                int n = (int)Math.pow(10, j);
                int[] a = generateArray(n);
                int[] b = generateArray(n);

                for (int e : a) {
                    if (rank(e, b) != -1)
                        count[j - 3] += 1;
                }
            }
        }

        for (int i = 0; i < 4; i++)
            StdOut.printf("N = 10^%d: %d\n", i + 3, count[i] / t);
    }
}
```
