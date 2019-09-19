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
