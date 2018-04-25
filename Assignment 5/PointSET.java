/******************************************************************************
 *  Compilation:  javac-algs4 PointSET.java
 *  Execution:    java-algs4 PointSET
 *  Dependencies: 
 *  
 *
 ******************************************************************************/
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.RectHV;

public class PointSET {
    private final SET<Point2D> set;
    // construct an empty set of points
    public PointSET() {
        set = new SET<>();
    }
    // is the set empty? 
    public boolean isEmpty() {
        return set.isEmpty();
    }
    // number of points in the set 
    public int size() {
        return set.size();
    }
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        else if (!set.contains(p)) {
            set.add(p);
        }
    }
    // does the set contain point p? 
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        else {
            return set.contains(p);
        }
    }
    // draw all points to standard draw 
    public void draw() {
        for (Point2D point:set) {
            point.draw();
        }
    }
    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        else {
            Stack<Point2D> points = new Stack<>();
            for (Point2D point:set) {
                if (rect.contains(point)) {
                    points.push(point);
                }
            }
            return points;
        }
    }
    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        else if (set.isEmpty()) {
            return null;
        }
        else {
            double nearestDistance = Double.MAX_VALUE;
            Point2D nearestPoint = null;
            for (Point2D point:set) {
                if (point.distanceTo(p) < nearestDistance) {
                    nearestPoint = point;
                }
            }
            return nearestPoint;
        }
    }
 
    // public static void main(String[] args)                  // unit testing of the methods (optional) 
}