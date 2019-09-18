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
