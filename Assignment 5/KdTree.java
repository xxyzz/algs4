/******************************************************************************
 *  Compilation:  javac-algs4 KdTree.java
 *  Execution:    java-algs4 KdTree
 *  Dependencies: 
 *  
 *
 ******************************************************************************/
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;
import jdk.nashorn.internal.runtime.RecompilableScriptFunctionData;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private Node root;
    private int size = 0;

    private static class Node {
        private final Point2D p;      // the point
        private final RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;              // the left/bottom subtree
        private Node rt;              // the right/top subtree

        public Node(Point2D p, RectHV rect, Node lb, Node rt) {
            this.p = p;
            this.rect = rect;
            this.lb = lb;
            this.rt = rt;
        }
    }
    // construct an empty set of points
    public KdTree() { }
    // is the set empty? 
    public boolean isEmpty() {
        return root == null;
    }
    // number of points in the set 
    public int size() {
        return size;
    }
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        root = insert(root, p, true);
    }

    private Node insert(Node node, Point2D p, boolean vertical) {
        if (node == null) {
            size++;
            return new Node(p, new RectHV(0.0, 0.0, 1.0, 1.0), null, null);
        }
        Node newNode;
        if (vertical) {
            if (p.x() < node.p.x()) {
                if (node.lb == null) {
                    newNode = new Node(p, new RectHV(node.rect.xmin(), node.rect.ymin(), node.p.x(), node.rect.ymax()), null, null);
                    node.lb = newNode;
                    size++;
                }
                else return insert(node.lb, p, !vertical);
            }
            else if (p.x() > node.p.x()) {
                if (node.rt == null) {
                    newNode = new Node(p, new RectHV(node.p.x(), node.rect.ymin(), node.rect.xmax(), node.rect.ymax()), null, null);
                    node.rt = newNode;
                    size++;
                }
                else return insert(node.rt, p, !vertical);
            }
            else if (Double.compare(p.y(), node.p.y()) != 0) {
                newNode = new Node(p, new RectHV(p.x(), node.rect.ymin(), node.rect.xmax(), node.rect.ymax()), null, null);
                node.rt = newNode;
                size++;
            }
        }
        else {
            if (p.y() < node.p.y()) {
                if (node.lb == null) {
                    newNode = new Node(p, new RectHV(node.rect.xmin(), node.rect.ymin(), node.rect.xmax(), node.p.y()), null, null);
                    node.lb = newNode;
                    size++;
                }
                else return insert(node.lb, p, !vertical);
            }
            else if (p.y() > node.p.y()) {
                if (node.rt == null) {
                    newNode = new Node(p, new RectHV(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.rect.ymax()), null, null);
                    node.rt = newNode;
                    size++;
                }
                else return insert(node.rt, p, !vertical);
            }
            else if (Double.compare(p.x(), node.p.x()) != 0) {
                newNode = new Node(p, new RectHV(node.rect.xmin(), p.y(), node.rect.xmax(), node.rect.ymax()), null, null);
                node.rt = newNode;
                size++;
            }
        }
        return root;
    }

    // does the set contain point p? 
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return contains(root, p);
    }

    private boolean contains(Node node, Point2D p) {
        if (p == null) throw new IllegalArgumentException(); 
        if (node == null) return false;
        if (Double.compare(node.p.x(), p.x()) == 0 && Double.compare(node.p.y(), p.y()) == 0) return true;
        else if (node.lb != null && node.lb.rect.contains(p) && (p.x() < node.p.x() || p.y() < node.p.y())) {
            return contains(node.lb, p);
        }
        else if (node.rt != null && node.rt.rect.contains(p) && (p.x() > node.p.x() || p.y() > node.p.y())) {
            return contains(node.rt, p);
        }
        else return false;
    }

    // draw all points to standard draw 
    public void draw() {
        draw(root, true);
    }

    private void draw(Node node, boolean vertical) {
        if (node != null) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            node.p.draw();
            if (vertical) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.setPenRadius();
                StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
            }
            else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.setPenRadius();
                StdDraw.setPenRadius();
                StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
            }
            draw(node.lb, !vertical);
            draw(node.rt, !vertical);
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        Stack<Point2D> points = new Stack<>();
        range(root, rect, points, true);
        return points;
    }

    private void range(Node node, RectHV rect, Stack<Point2D> points, boolean vertical) {
        if (node == null) return;
        if (rect.contains(node.p)) points.push(node.p);
        RectHV nodeRectHV;
        if (vertical) {
            nodeRectHV = new RectHV(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
        }
        else {
            nodeRectHV = new RectHV(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
        }
        if (rect.intersects(nodeRectHV)) {
            range(node.lb, rect, points, !vertical);
            range(node.rt, rect, points, !vertical);
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;          
        return nearest(root, p, root.p, root.p.distanceSquaredTo(p));
    }

    private Point2D nearest(Node node, Point2D p, Point2D nearestPoint, double nearestDistance) {
        if (node == null) return nearestPoint;
        if (node.lb != null && node.lb.rect.distanceSquaredTo(p) < nearestDistance && node.lb.p.distanceSquaredTo(p) < nearestDistance) {
            nearestPoint = node.lb.p;
            nearestDistance = nearestPoint.distanceSquaredTo(p);
            nearest(node.lb, p, nearestPoint, nearestDistance);
            nearest(node.rt, p, nearestPoint, nearestDistance);
        }
        if (node.rt != null && node.rt.rect.distanceSquaredTo(p) < nearestDistance && node.rt.p.distanceSquaredTo(p) < nearestDistance) {
            nearestPoint = node.rt.p;
            nearestDistance = nearestPoint.distanceSquaredTo(p);
            nearest(node.rt, p, nearestPoint, nearestDistance);
            nearest(node.lb, p, nearestPoint, nearestDistance);
        }
        return nearestPoint;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        KdTree kdtree = new KdTree();
        kdtree.insert(new Point2D(0.75, 1.0));
        kdtree.insert(new Point2D(0.5, 0.25));
        kdtree.insert(new Point2D(0.25, 1.0));
        kdtree.insert(new Point2D(0.75, 0.25));
        kdtree.insert(new Point2D(0.75, 0.75));
        kdtree.insert(new Point2D(0.25, 0.75));
        kdtree.insert(new Point2D(0.0, 0.0));
        kdtree.insert(new Point2D(1.0, 0.0));
        kdtree.insert(new Point2D(0.25, 0.5));
        kdtree.insert(new Point2D(1.0, 0.5));
        StdOut.println(Boolean.toString(kdtree.contains(new Point2D(0.25, 0.5))));
        // StdOut.println(String.valueOf(kdtree.size()));
        // StdOut.println(Boolean.toString(kdtree.isEmpty()));
        // StdOut.println(kdtree.nearest(new Point2D(1.0, 1.0)).x() + " " + kdtree.nearest(new Point2D(1.0, 1.0)).y());
        // kdtree.draw();
    }
}