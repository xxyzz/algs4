# 1.2 Data Abstraction

## EXERCISES

- 1.2.1 Write a `Point2D` client that takes an integer value *N* from the command line, generates *N* random points in the unit square, and computes the distance separating the *closest pair* of points.

```java
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdRandom;

public class Point2DClient {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        Point2D[] points = new Point2D[n];
        double shortestDistance = 0;

        for (int i = 0; i < n; i++)
            points[i] = new Point2D(StdRandom.uniform(), StdRandom.uniform());

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double newDistance = points[i].distanceTo(points[j]);
                if (newDistance < shortestDistance)
                    shortestDistance = newDistance;
            }
        }
    }
}
```

- 1.2.2 Write an `Interval1D` client that takes an `int` value *N* as command-line argument, reads *N* intervals (each defined by a pair of `double` values) from standard input, and prints all pairs that intersect.

```java
import edu.princeton.cs.algs4.Interval1D;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Interval1DClient {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        Interval1D[] intervals = new Interval1D[n];

        for (int i = 0; i < n; i++)
            intervals[i] = new Interval1D(StdIn.readDouble(), StdIn.readDouble());

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (intervals[i].intersects(intervals[j]))
                    StdOut.printf("%s and %d intersects\n", intervals[i], intervals[j]);
            }
        }
    }
}
```

- 1.2.3 Write an `Interval2Dclient` that takes command-line arguments `N`, `min`, and `max` and generates `N` random 2D intervals whose width and height are uniformly distributed between `min` and `max` in the unit square. Draw them on `StdDraw` and print the number of pairs of intervals that intersect and the number of intervals that are contained in one another.

```java
import edu.princeton.cs.algs4.Interval1D;
import edu.princeton.cs.algs4.Interval2D;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

public class Interval2DClient {
    public static void main(String[] args) {
        int                      n = Integer.parseInt(args[0]);
        double                 min = Double.parseDouble(args[1]);
        double                 max = Double.parseDouble(args[2]);
        Interval2D[]   intervals2D = new Interval2D[n];
        Interval1D[][] intervals1D = new Interval1D[n][2];
        int              intersect = 0;
        int                contain = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 2; j++) {
                double randomMin = 0, randomMax = -1;
                while (randomMin > randomMax) {
                    randomMin = StdRandom.uniform(min, max);
                    randomMax = StdRandom.uniform(min, max);
                }
                intervals1D[i][j] = new Interval1D(randomMin, randomMax);
            }

            intervals2D[i] = new Interval2D(intervals1D[i][0], intervals1D[i][1]);
            intervals2D[i].draw();
        }

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (intervals2D[i].intersects(intervals2D[j]))
                    intersect++;
            }
        }
            
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n && j != i; j++) {
                Point2D p1 = new Point2D(intervals1D[j][0].min(), intervals1D[j][1].min());
                Point2D p2 = new Point2D(intervals1D[j][0].max(), intervals1D[j][1].max());

                if (intervals2D[i].contains(p1) && intervals2D[i].contains(p2))
                    contain++;    
            }
        }

        StdOut.printf("Number of intervals intersect: %d\n", intersect);
        StdOut.printf("Number of intervals contain: %d\n", contain);
    }
}
```

- 1.2.4 What does the following code fragment print?

```java
String string1 = "hello";
String string2 = string1;
string1 = "world";
StdOut.println(string1);
StdOut.println(string2);
```

```
world
hello
```

- 1.2.5 What does the following code fragment print?

```java
String s = "Hello World";
s.toUpperCase();
s.substring(6, 11);
StdOut.println(s);
```

*Answer*: "Hello World". `String` objects are immutable—string methods return a new `String` object with the appropriate value (but they do not change the value of the object that was used to invoke them). This code ignores the objects returned and just prints the original string. To print "`WORLD`", use `s = s.toUpperCase()` and `s = s.substring(6, 11)`.

- 1.2.6 A string `s` is a *circular rotation* of a string `t` if it matches when the characters are circularly shifted by any number of positions; e.g., `ACTGACG` is a circular shift of `TGACGAC`, and vice versa. Detecting this condition is important in the study of genomic sequences. Write a program that checks whether two given strings `s` and `t` are circular shifts of one another. *Hint* : The solution is a one-liner with `indexOf()`, `length()`, and string concatenation.

```java
import edu.princeton.cs.algs4.StdOut;

public class CircularShifts {
    public static boolean circularRotation(String s, String t) {
        if (s.length() == t.length()) {
            for (int i = 0; i < s.length() - 1; i++) {
                String newString = s.substring(i + 1, s.length()) + s.substring(0, i + 1);
                if (newString.equals(t))
                    return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        String s = "ACTGACG";
        String t = "TGACGAC";
        StdOut.println(circularRotation(s, t));
    }
}
```

- 1.2.7 What does the following recursive function return?

```java
public static String mystery(String s)
{
    int N = s.length();
    if (N <= 1) return s;
    String a = s.substring(0, N/2);
    String b = s.substring(N/2, N);
    return mystery(b) + mystery(a);
}
```

Return a reversed string.

- 1.2.8 Suppose that `a[]` and `b[]` are each integer arrays consisting of millions of integers. What does the follow code do? Is it reasonably efficient?

```java
int[] t = a; a = b; b = t;
```

*Answer*. It swaps them. It could hardly be more efficient because it does so by copying references, so that it is not necessary to copy millions of elements.

- 1.2.9 Instrument `BinarySearch` (page47) to use a `Counter` to count the total number of keys examined during all searches and then print the total after all searches are complete. *Hint*: Create a Counter in `main()` and pass it as an argument to `rank()`.

```java
import java.util.Arrays;
import edu.princeton.cs.algs4.Counter;
import edu.princeton.cs.algs4.StdOut;

public class BinarySearch
{
    public static int rank(int key, int[] a, Counter counter)
    {   // Array must be sorted.
        int lo  = 0;
        int hi = a.length - 1;
        counter.increment();
        while (lo <= hi)
        {   // Key is in a[lo..hi] or not present.
            int mid = lo + (hi - lo) / 2;
            if      (key < a[mid]) hi = mid - 1;
            else if (key > a[mid]) lo = mid + 1;
            else                   return mid;
        }
        return -1;
    }
    public static void main(String[] args)
    {
        int[] whitelist = In.readInts(args[0]);
        Arrays.sort(whitelist);
        Counter counter = new Counter("examined keys number");
        while (!StdIn.isEmpty())
        {   // Read key, print if not in whitelist.
            int key = StdIn.readInt();
            if (rank(key, whitelist, counter) < 0)
                StdOut.println(key);
        }
        StdOut.printf("Total number of keys examined: %d\n", counter.tally());
    }
}
```

- 1.2.10 Develop a class `VisualCounter` that allows both increment and decrement operations. Take two arguments `N` and `max` in the constructor, where `N` specifies the maximum number of operations and `max` specifies the maximum absolute value for the counter. As a side effect, create a plot showing the value of the counter each time its tally changes.

```java
import edu.princeton.cs.algs4.StdDraw;

public class VisualCounter
{
    private final int maxOperations;
    private final int maxAbsoluteValue;
    private int count = 0;
    private int operations = 0;

    public VisualCounter (int n, int max) {
        maxOperations = n;
        maxAbsoluteValue = max;
    }

    public void increment() {
        if (operations < maxOperations && Math.abs(count + 1) < maxAbsoluteValue) {
            count++;
            operations++;
            StdDraw.point(operations, count);
        }
    }

    public void decrement() {
        if (operations < maxOperations && Math.abs(count - 1) < maxAbsoluteValue) {
            count--;
            operations++;
            StdDraw.point(operations, count);
        }
    }
    
    public static void main(String[] args) {
        VisualCounter visualCounter = new VisualCounter(4, 10);
        StdDraw.setScale(0, 10);
        StdDraw.setPenRadius(0.05);
        visualCounter.increment();
        visualCounter.increment();
        visualCounter.decrement();
    }
}
```

- 1.2.11 Develop an implementation `SmartDate` of our `Date` API that raises an exception if the date is not legal.

[Date.java](https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/Date.java.html)

```java
public class SmartDate {
    private static final int[] DAYS = { 0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

    private final int month;   // month (between 1 and 12)
    private final int day;     // day   (between 1 and DAYS[month]
    private final int year;    // year

    /**
     * Initializes a new date from the month, day, and year.
     * @param month the month (between 1 and 12)
     * @param day the day (between 1 and 28-31, depending on the month)
     * @param year the year
     * @throws IllegalArgumentException if this date is invalid
     */
    public SmartDate(int month, int day, int year) {
        if (!isValid(month, day, year)) throw new IllegalArgumentException("Invalid date");
        this.month = month;
        this.day   = day;
        this.year  = year;
    }

    // is the given date valid?
    private static boolean isValid(int m, int d, int y) {
        if (m < 1 || m > 12)      return false;
        if (d < 1 || d > DAYS[m]) return false;
        if (m == 2 && d == 29 && !isLeapYear(y)) return false;
        return true;
    }

    // is y a leap year?
    private static boolean isLeapYear(int y) {
        if (y % 400 == 0) return true;
        if (y % 100 == 0) return false;
        return y % 4 == 0;
    }
}
```

- 1.2.12 Add a method `dayOfTheWeek()` to `SmartDate` that returns a `String` value `Monday, Tuesday, Wednesday, Thursday, Friday, Saturday`, or `Sunday`, giving the appropriate day of the week for the date. You may assume that the date is in the 21st century.

- 1.2.13 Using our implementation of `Date` as a model (page 91), develop an implementation of `Transaction`.

[Transaction.java](https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/Transaction.java.html)

- 1.2.14 Using our implementation of `equals()` in `Date` as a model (page 103), develop implementations of `equals()` for `Transaction`.
