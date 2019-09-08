# 1.1 Basic Programming Model

## CREATIVE PROBLEMS

- 1.1.26 *Sorting three numbers*. Suppose that the variables `a`, `b`, `c`, and `t` are all of the same numeric primitive type. Show that the following code puts `a`, `b`, and `c` in ascending order:

```java
if (a > b) { t = a; a = b; b = t; }
if (a > c) { t = a; a = c; c = t; }
if (b > c) { t = b; b = c; c = t; }
```

Move the lowest number to the left(a), than move the next lowest to the second number(b).

- 1.1.27 *Binomial distribution*. Estimate the number of recursive calls that would be used by the code

```java
public static double binomial(int N, int k, double p)
{
    if ((N == 0) || (k < 0)) return 1.0;
    return (1.0 - p)*binomial(N-1, k) + p*binomial(N-1, k-1);
}
```

to compute `binomial(100, 50)`. Develop a better implementation that is based on saving computed values in an array.

N = 0: 1

N = 1: 2

N = 2: 1 + 2 + 4

N = 3: 1 + 2 + 4 + 8

N = 100: 1 + 2 + 4 + ... + 2<sup>100</sup> = 1 * (1 - 2<sup>101</sup>)/(1 - 2)

- 1.1.28 *Remove duplicates*. Modify the test client in `BinarySearch` to remove any duplicate keys in the whitelist after the sort.

```java
import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BinarySearch
{
    public static void main(String[] args)
    {
        int[] whitelist = new In(args[0]).readAllInts();

        Arrays.sort(whitelist);

        int n = whitelist.length;
        int j = 0;
        if (n > 1)
        {
            for (int i = 0; i < n - 1; i++)
                if (whitelist[i] != whitelist[i + 1])
                    whitelist[j++] = whitelist[i];
            whitelist[j++] = whitelist[n - 1];
        }

        for (int i = 0; i < j; i++)
            StdOut.printf("%d  ", whitelist[i]);
        StdOut.println();
    }
}
```

- 1.1.29 *Equal keys*. Add to `BinarySearch` a static method `rank()` that takes a key and a sorted array of `int` values (some of which may be equal) as arguments and returns the number of elements that are smaller than the key and a similar method `count()` that returns the number of elements equal to the key. *Note* : If `i` and `j` are the values returned by `rank(key, a)` and `count(key, a)` respectively, then `a[i..i+j-1]` are the values in the array that are equal to key.

```java
import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BinarySearch {
    public static int rank(int key, int[] a) {
        int smallerCount = 0;
        for (int e : a)
            if (e < key)
                smallerCount++;

        return smallerCount;
    }

    public static int count(int key, int[] a) {
        int equalCount = 0;
        for (int e : a)
            if (e == key)
                equalCount++;

        return equalCount;
    }

    public static void main(String[] args) {
        int[] a = new In(args[0]).readAllInts();
        Arrays.sort(a);

        StdOut.printf("rank(1, a): %d\n", rank(1, a));
        StdOut.printf("count(1, a): %d\n", count(1, a));
    }
}
```

- 1.1.30 *Array exercise*. Write a code fragment that creates an *N*-by-*N* boolean array `a[][]` such that `a[i][j]` is `true` if `i` and `j` are relatively prime (have no common factors), and `false` otherwise.

```java
import java.util.Arrays;
import edu.princeton.cs.algs4.StdOut;

public class PrimeMatrix {
    public static int gcd(int p, int q)
    {
        if (q == 0) return p;
        int r = p % q;
        return gcd(q, r);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        boolean[][] primeMatrix = new boolean[n][n];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                primeMatrix[i][j] = gcd(i, j) == 1;

        StdOut.println(Arrays.deepToString(primeMatrix));
    }
}
```

- 1.1.31 *Random connections*. Write a program that takes as command-line arguments an integer `N` and a double value `p` (between 0 and 1), plots `N` equally spaced dots of size .05 on the circumference of a circle, and then, with probability `p` for each pair of points, draws a gray line connecting them.
