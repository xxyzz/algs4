# 1.2 Data Abstraction

## CREATIVE PROBLEMS

- 1.2.15 *File input*. Develop a possible implementation of the static `readInts()` method from `In` (which we use for various test clients, such as binary search on page 47) that is based on the `split()` method in `String`.

*Solution*:

```java
public static int[] readInts(String name)
{
    In in = new In(name);
    String input = StdIn.readAll();
    String[] words = input.split("\\s+");
    int[] ints = new int[words.length];
    for int i = 0; i < word.length; i++)
        ints[i] = Integer.parseInt(words[i]);
    return ints;
}
```

We will consider a different implementation in `Section 1.3` (see page 126).

- 1.2.16 *Rational numbers*. Implement an immutable data type `Rational` for rational numbers that supports addition, subtraction, multiplication, and division.

You do not have to worry about testing for overflow (see Exercise 1.2.17), but use as instance variables two long values that represent the numerator and denominator to limit the possibility of overflow. Use Euclid’s algorithm (see page 4) to ensure that the numerator and denominator never have any common factors. Include a test client that exercises all of your methods.

- 1.2.17 *Robust implementation of rational numbers*. Use assertions to develop an im- plementation of `Rational` (see Exercise 1.2.16) that is immune to overflow.

```java
import edu.princeton.cs.algs4.StdOut;

public class Rational {
    public final int numerator;
    public final int denominator;

    public Rational (int numerator, int denominator) {
        if (denominator == 0) throw new IllegalArgumentException("Denominator can't be zero");
        this.numerator   = numerator;
        this.denominator = denominator;
    }

    // sum of this number and b
    public Rational plus (Rational b) {
        long newDenominator = (long) denominator * b.denominator;
        long newNumerator   = (long) numerator * b.denominator + b.numerator * denominator;

        assert newDenominator <= Integer.MAX_VALUE;
        assert   newNumerator <= Integer.MAX_VALUE;

        int gcd = gcd(Math.abs((int)newNumerator), Math.abs((int)newDenominator));
        if (gcd != 1) {
            newDenominator /= gcd;
            newNumerator /= gcd;
        }
        return new Rational((int)newNumerator, (int)newDenominator);
    }

    private int gcd(int p, int q) {
        if (q == 0) return p;
        int r = p % q;
        return gcd(q, r);
    }

    // difference of this number and b
    public Rational minus (Rational b) {
        long newDenominator = (long) denominator * b.denominator;
        long newNumerator   = (long) numerator * b.denominator - b.numerator * denominator;

        assert newDenominator <= Integer.MAX_VALUE;
        assert   newNumerator <= Integer.MAX_VALUE;

        int gcd = gcd(Math.abs((int)newNumerator), Math.abs((int)newDenominator));
        if (gcd != 1) {
            newDenominator /= gcd;
            newNumerator /= gcd;
        }
        return new Rational((int)newNumerator, (int)newDenominator);
    }

    // product of this number and b
    public Rational times (Rational b) {
        long newDenominator = (long) denominator * b.denominator;
        long newNumerator   = (long) numerator * b.numerator;

        assert newDenominator <= Integer.MAX_VALUE;
        assert   newNumerator <= Integer.MAX_VALUE;

        int gcd = gcd(Math.abs((int)newNumerator), Math.abs((int)newDenominator));
        if (gcd != 1) {
            newDenominator /= gcd;
            newNumerator /= gcd;
        }
        return new Rational((int)newNumerator, (int)newDenominator);
    }

    // product of this number and b
    public Rational divides (Rational b) {
        return times(new Rational(b.denominator, b.numerator));
    }

    // is this number equal to that ?
    public boolean equals (Rational that) {
        if (that == this) return true;
        if (that == null) return false;
        if (that.getClass() != this.getClass()) return false;
        return this.numerator == that.numerator && this.denominator == that.denominator;
    }

    // string representation
    public String toString() {
        return numerator + "/" + denominator;
    }

    public static void main(String[] args) {
        Rational a = new Rational(1, 2000000000);
        Rational b = new Rational(3, 2000000000);
        StdOut.printf("a + b: %s\n", a.plus(b));
        StdOut.printf("a - b: %s\n", a.minus(b));
        StdOut.printf("a * b: %s\n", a.times(b));
        StdOut.printf("a / b: %s\n", a.divides(b));
        StdOut.println("a == b: " + a.equals(b));
        StdOut.println("a == a: " + a.equals(a));
    }
}
```

```
$ javac-algs4 *.java
$ java-algs4 -ea Rational
```

- 1.2.18 *Variance for accumulator*. Validate that the following code, which adds the methods `var()` and `stddev()` to `Accumulator`, computes both the mean and variance of the numbers presented as arguments to `addDataValue()`:

    ```java
    public class Accumulator
    {
        private double m;
        private double s;
        private int N;

        public void addDataValue(double x)
        {
            N++;
            s = s + 1.0 * (N-1) / N * (x - m) * (x - m);
            m = m + (x - m) / N;
        }

        public double mean()
        {  return m;  }
        
        public double var()
        {  return s/(N - 1);  }

        public double stddev()
        {  return Math.sqrt(this.var());  }
    }
    ```

This implementation is less susceptible to roundoff error than the straightforward im- plementation based on saving the sum of the squares of the numbers.

- 1.2.19 *Parsing*. Develop the parse constructors for your `Date` and `Transaction` implementations of Exercise 1.2.13 that take a single `String` argument to specify the initialization values, using the formats given in the table below.

    *Partial solution:*

    ```java
    public Date(String date)
    {
        String[] fields = date.split("/");
        month = Integer.parseInt(fields[0]);
        day   = Integer.parseInt(fields[1]);
        year  = Integer.parseInt(fields[2]);
    }
    ```

    type | format | example
    -----|--------|---------
    Date | integers separated by slashes | 5/22/1939
    Transaction | customer, date, and amount, separated by whitespace | Turing 5/22/1939 11.99

    [Date.java](https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/Date.java.html)
    [Transaction.java](https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/Transaction.java.html)

    ```java
    /**
     * Initializes new date specified as a string in form MM/DD/YYYY.
     * @param date the string representation of this date
     * @throws IllegalArgumentException if this date is invalid
     */
    public Date(String date) {
        String[] fields = date.split("/");
        if (fields.length != 3)
            throw new IllegalArgumentException("Invalid date");
        month = Integer.parseInt(fields[0]);
        day   = Integer.parseInt(fields[1]);
        year  = Integer.parseInt(fields[2]);
        if (!isValid(month, day, year)) throw new IllegalArgumentException("Invalid date");
    }

    /**
     * Initializes a new transaction by parsing a string of the form NAME DATE AMOUNT.
     *
     * @param  transaction the string to parse
     * @throws IllegalArgumentException if {@code amount} 
     *         is {@code Double.NaN}, {@code Double.POSITIVE_INFINITY},
     *         or {@code Double.NEGATIVE_INFINITY}
     */
    public Transaction(String transaction) {
        String[] a = transaction.split("\\s+");
        if (a.length != 3)
            throw new IllegalArgumentException("Invalid transaction");
        who    = a[0];
        when   = new Date(a[1]);
        amount = Double.parseDouble(a[2]);
        if (Double.isNaN(amount) || Double.isInfinite(amount))
            throw new IllegalArgumentException("Amount cannot be NaN or infinite");
    }
    ```
