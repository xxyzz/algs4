# 1.1 Basic Programming Model

## EXERCISES

- 1.1.1 Give the value of each of the following expressions:

    a. ( 0 + 15 ) / 2 = 7
    b. 2.0e-6 * 100000000.1 = 200.0000002
    c. true && false || true && true = true

- 1.1.2 Give the type and value of each of the following expressions:

    a. (1 + 2.236)/2: double, 1.618
    b. 1 + 2 + 3 + 4.0: double, 10.0
    c. 4.1 >= 4: boolean, true
    d. 1 + 2 + "3": String, 33

- 1.1.3 Write a program that takes three integer command-line arguments and prints `equal` if all three are equal, and `not equal` otherwise.

```java
public class Equal
{
    public static void main(String[] args) {
        if (args.length > 2 && args[0].equals(args[1]) && args[1].equals(args[2]))
            System.out.println("equal");
        else
            System.out.println("not equal");
    }
}
```

- 1.1.4 What (if anything) is wrong with each of the following statements?

    a. if (a > b) ~~then~~ c = 0;
    b. if a > b { c = 0; } --> if (a > b) { c = 0; }
    c. if (a > b) c = 0;
    d. if (a > b) c = 0 else b = 0; --> if (a > b) c = 0; else b = 0;

- 1.1.5 Write a code fragment that prints `true` if the `double` variables `x` and `y` are both strictly between `0` and `1` and `false` otherwise.

```java
if (x > 0 && x < 1 && y > 0 && y < 1)
    System.out.println(true);
else
    System.out.println(false);
```

- 1.1.6 What does the following program print?

```java
int f = 0;
int g = 1;
for (int i = 0; i <= 15; i++)
{
    StdOut.println(f);
    f = f + g;
    g = f - g;
}
```

```
0
1
1
2
3
5
8
13
21
34
55
89
144
233
377
610
```

- 1.1.7 Give the value printed by each of the following code fragments:

```java
double t = 9.0;
while (Math.abs(t - 9.0/t) > .001)
    t = (9.0/t + t) / 2.0;
StdOut.printf("%.5f\n", t);

// 3.00009
```

```java
int sum = 0;
for (int i = 1; i < 1000; i++)
    for (int j = 0; j < i; j++)
        sum++;
StdOut.println(sum);

// (999 + 1) * 999 / 2 = 499500
```

```java
int sum = 0;
for (int i = 1; i < 1000; i *= 2)
    for (int j = 0; j < i; j++)
        sum++;
StdOut.println(sum);

// 1 * (1 - 2^10) / (1 - 2) = 1023
```

- 1.1.8 What does each of the following print?

```java 
System.out.println('b');

// b 
```

```java
System.out.println('b' + 'c');

// 98 + 99 = 197
```

```java
System.out.println((char) ('a' + 4));

// e
```

- 1.1.9 Write a code fragment that puts the binary representation of a positive integer `N`
into a `String s`.

*Solution*: Java has a built-in method `Integer.toBinaryString(N)` for this job, but the point of the exercise is to see how such a method might be implemented. Here is a particularly concise solution:

```java
String s = "";
for (int n = N; n > 0; n /= 2)
    s = (n % 2) + s;
```

- 1.1.10 What is wrong with the following code fragment?

```java
int[] a;
for (int i = 0; i < 10; i++)
    a[i] = i * i;
```

*Solution*: It does not allocate memory for `a[]` with `new`. This code results in a
`variable a might not have been initialized `compile-time error.

- 1.1.11 Write a code fragment that prints the contents of a two-dimensional boolean array, using * to represent `true` and a space to represent `false`. Include row and column numbers.

```java
boolean[][] arr = new boolean[N][N];

for (int i = 0; i < N; i++)
    for (int j = 0; j < N; j++)
        if (arr[i][j])
            System.out.printf('*, row: %d, column: %d\n', i , j);
        else
            System.out.printf(' , row: %d, column: %d\n', i , j);
```

- 1.1.12 What does the following code fragment print?

```java
int[] a = new int[10];
for (int i = 0; i < 10; i++)
    a[i] = 9 - i;
for (int i = 0; i < 10; i++)
    a[i] = a[a[i]];
for (int i = 0; i < 10; i++)
    System.out.println(i);

1
2
3
4
5
6
7
8
9
```

- 1.1.13 Write a code fragment to print the *transposition* (rows and columns changed) of a two-dimensional array with *M* rows and *N* columns

```java
int[][] matrix = new int[M][N];

void printMatrix() {
    for (int i = 0; i < N; i++)
        for (int j = 0; j < N; j++)
            System.out.println(matrix[i][j]);
}

void transposition() {
    for (int i = 0; i < N; i++)
        for (int j = i + 1; j < N; j++) {
            int temp = matrix[i][j];
            matrix[i][j] = matrix[j][i];
            matrix[j][i] = temp;
        }
}
```

- 1.1.14 Write a static method `lg()` that takes an `int` value `N` as argument and returns the largest `int` not larger than the base-2 logarithm of `N`. Do *not* use `Math`.

```java
public class Logarithm
{
    public static int lg(int N) {
        int answer = 0;
        int exponents = 1;
        while (exponents < N) {
            answer++;
            for (int i = 0; i < answer; i++)
                exponents *= answer;
        }
        return answer;
    }

    public static void main(String[] args) {
        System.out.println(lg(8));
    }
}
```

- 1.1.15 Write a static method `histogram()` that takes an array `a[]` of `int` values and an integer `M` as arguments and returns an array of length `M` whose ith entry is the number of times the integer `i` appeared in the argument array. If the values in `a[]` are all between `0` and `M–1`, the sum of the values in the returned array should be equal to `a.length`.

```java
import java.util.Arrays;

public class Histogram
{
    public static int[] histogram(int[] a, int M) {
        int[] newArr = new int[M];
        for (int i = 0; i < a.length; i++)
            newArr[a[i]]++;
        return newArr;
    }

    public static void main(String[] args) {
        int[] a = {1, 1, 2, 3};
        System.out.println(Arrays.toString(histogram(a, 4)));
    }
}
```

- 1.1.16 Give the value of `exR1(6)`:

```java
public static String exR1(int n)
{
    if (n <= 0) return "";
    return exR1(n-3) + n + exR1(n-2) + n;
}

// 311361142246
```

- 1.1.17 Criticize the following recursive function:

```java
public static String exR2(int n)
{
    String s = exR2(n-3) + n + exR2(n-2) + n;
    if (n <= 0) return "";
    return s;
}
```

*Answer* : The base case will never be reached. A call to `exR2(3)` will result in calls to
`exR2(0)`, `exR2(-3)`, `exR3(-6)`, and so forth until a `StackOverflowError` occurs.

- 1.1.18 Consider the following recursive function:

```java
public static int mystery(int a, int b)
{
    if (b == 0)     return 0;
    if (b % 2 == 0) return mystery(a+a, b/2);
    return mystery(a+a, b/2) + a;
}
```

What are the values of `mystery(2, 25)` and `mystery(3, 11)`? Given positive integers `a` and `b` describe what value `mystery(a, b)` computes. Answer the same question, but replace `+` with `*` and replace `return 0` with `return 1`.

`mystery(2, 25)`: 50, `mystery(3, 11)`: 33.

Replce: 33554432, 177147

- 1.1.19 Run the following program on your computer:

```java
public class Fibonacci
{
    public static long F(int N)
    {
        if (N == 0) return 0;
        if (N == 1) return 1;
        return F(N-1) + F(N-2);
    }
    public static void main(String[] args)
    {
        for (int N = 0; N < 100; N++)
            StdOut.println(N + " " + F(N));
    }
}
```

What is the largest value of N for which this program takes less 1 hour to compute the value of F(N)? Develop a better implementation of F(N) that saves computed values in an array.

43 433494437.

```java
public class Fibonacci
{
    public static long F(int N, int[] arr)
    {
        if (N == 0) return 0;
        if (N == 1) return 1;
        if (arr[N] == 0)
            arr[N] = arr[N-1] + arr[N-2];
        return arr[N-1] + arr[N-2];
    }

    public static void main(String[] args)
    {
        int[] arr = new int[100];
        arr[0] = 0;
        arr[1] = 1;
        
        for (int N = 0; N < 100; N++)
            System.out.println(N + " " + F(N, arr));
    }
}
```

- 1.1.20 Write a recursive static method that computes the value of `ln(N!)`

logarithm product rule: log<sub>b</sub>(xy) = log<sub>b</sub>x + log<sub>b</sub>y

proof:

m = log<sub>b</sub>x -> x = b<sup>m</sup>

n = log<sub>b</sub>y -> y = b<sup>n</sup>

log<sub>b</sub>(b<sup>m</sup> * b<sup>n</sup>) = log<sub>b</sub>(b<sup>m+n</sup>) = m + n = log<sub>b</sub>x + log<sub>b</sub>y

```java
import java.lang.Math;

public class Log
{
    public static double ln(int N)
    {
        if (N == 1) return 0;
        return Math.log(N) + ln(N-1);
    }

    public static void main(String[] args)
    {
        System.out.println(ln(4));
    }
}
```

- 1.1.21 Write a program that reads in lines from standard input with each line containing a name and two integers and then uses `printf()` to print a table with a column of the names, the integers, and the result of dividing the first by the second, accurate to three decimal places. You could use a program like this to tabulate batting averages for baseball players or grades for students.

```java
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class PrintTable {
    public static void main(String[] args) {
        In in = new In(args[0]);
        while (in.hasNextLine()) {
            String name = in.readString();
            int a = in.readInt();
            int b = in.readInt();
            StdOut.printf("%s  %d  %d  %.3f\n", name, a, b, (float)a/b);
        }
    }
}
```

- 1.1.22 Write a version of `BinarySearch` that uses the recursive `rank()` given on page 25 and traces the method calls. Each time the recursive method is called, print the argument values `lo` and `hi`, indented by the depth of the recursion. *Hint*: Add an argument to the recursive method that keeps track of the depth.

```java
import edu.princeton.cs.algs4.StdOut;

public class BinarySearch {
    public static int rank(int key, int[] a)
    {  return rank(key, a, 0, a.length - 1, 0);  }

    public static int rank(int key, int[] a, int lo, int hi, int depth)
    {   // Index of key in a[], if present, is not smaller than lo
        //                                  and not larger than hi.
        for (int i = 0; i < depth; i++)
            StdOut.printf("\t");

        StdOut.printf("lo: %d, hi: %d\n", lo, hi);
        depth++;

        if (lo > hi) return -1;
        int mid = lo + (hi - lo) / 2;
        if      (key < a[mid]) return rank(key, a, lo, mid - 1, depth);
        else if (key > a[mid]) return rank(key, a, mid + 1, hi, depth);
        else                   return mid;
    }

    public static void main(String[] args) {
        rank(1, new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
    }
}
```

- 1.1.23 Add to the `BinarySearch` test client the ability to respond to a second argument: `+` to print numbers from standard input that are not in the whitelist, `-` to print numbers that are in the whitelist.

```java
import edu.princeton.cs.algs4.StdOut;

public class BinarySearch {
    public static int rank(int key, int[] a, boolean plus)
    {  return rank(key, a, 0, a.length - 1, plus);  }

    public static int rank(int key, int[] a, int lo, int hi, boolean plus)
    {   // Index of key in a[], if present, is not smaller than lo
        //                                  and not larger than hi.
        if (plus) {
            for (int i = 0; i < a.length; i++)
                if (i < lo || i > hi)
                    StdOut.printf("%d ", a[i]);
        }
        else {
            for (int j = lo; j <= hi; j++)
                StdOut.printf("%d ", a[j]);
        }

        StdOut.println();

        if (lo > hi) return -1;
        int mid = lo + (hi - lo) / 2;
        if      (key < a[mid]) return rank(key, a, lo, mid - 1, plus);
        else if (key > a[mid]) return rank(key, a, mid + 1, hi, plus);
        else                   return mid;
    }

    public static void main(String[] args) {
        boolean plus = false;
        if (args[0].equals("+"))
            plus = true;
        rank(1, new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, plus);
    }
}
```

- 1.1.24 Give the sequence of values of `p` and `q` that are computed when Euclid’s algorithm is used to compute the greatest common divisor of 105 and 24. Extend the code given on page 4 to develop a program Euclid that takes two integers from the command line and computes their greatest common divisor, printing out the two arguments for each call on the recursive method. Use your program to compute the greatest common divisor of 1111111 and 1234567.

```java
import edu.princeton.cs.algs4.StdOut;

public class GCD {
    public static int gcd(int p, int q)
    {
        StdOut.printf("%d %d\n", p, q);
        if (q == 0) return p;
        int r = p % q;
        return gcd(q, r);
    }

    public static void main(String[] args) {
        if (args.length >= 2)
            StdOut.println(gcd(Integer.parseInt(args[0]), Integer.parseInt(args[1])));
    }
}
```

```
$ java-algs4 GCD 1111111 1234567
1111111 1234567
1234567 1111111
1111111 123456
123456 7
7 4
4 3
3 1
1 0
1
```

- 1.1.25 Use mathematical induction to prove that Euclid’s algorithm computes the greatest common divisor of any pair of nonnegative integers `p` and `q`.

[Euclidean algorithm - Wikipedia](https://en.wikipedia.org/wiki/Euclidean_algorithm#Proof_of_validity)
