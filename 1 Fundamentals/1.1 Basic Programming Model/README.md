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
