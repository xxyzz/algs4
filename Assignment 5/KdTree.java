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
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;

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
            else if (node.rt == null && Double.compare(p.y(), node.p.y()) != 0) {
                newNode = new Node(p, new RectHV(node.p.x(), node.rect.ymin(), node.rect.xmax(), node.rect.ymax()), null, null);
                node.rt = newNode;
                size++;
            }
            else if (node.rt != null && Double.compare(p.y(), node.p.y()) != 0) return insert(node.rt, p, !vertical);
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
            else if (node.rt == null && Double.compare(p.x(), node.p.x()) != 0) {
                newNode = new Node(p, new RectHV(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.rect.ymax()), null, null);
                node.rt = newNode;
                size++;
            }
            else if (node.rt != null && Double.compare(p.x(), node.p.x()) != 0) return insert(node.rt, p, !vertical);
        }
        return root;
    }

    // does the set contain point p? 
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return contains(root, p);
    }

    private boolean contains(Node node, Point2D p) {
        if (node == null) return false;
        if (Double.compare(node.p.x(), p.x()) == 0 && Double.compare(node.p.y(), p.y()) == 0) return true;
        else if (node.rt != null && node.rt.rect.contains(p)) {
            return contains(node.rt, p);
        }
        else if (node.lb != null && node.lb.rect.contains(p)) {
            return contains(node.lb, p);
        }
        return false;
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
            StdDraw.setPenRadius();
            if (vertical) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
            }
            else {
                StdDraw.setPenColor(StdDraw.BLUE);
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
        if (rect.intersects(node.rect)) {
            if (rect.contains(node.p)) points.push(node.p);
            range(node.rt, rect, points, !vertical);
            range(node.lb, rect, points, !vertical);
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;          
        return nearest(root, p, root.p);
    }

    private Point2D nearest(Node node, Point2D p, Point2D nearestPoint) {
        if (node == null) return nearestPoint;
        double nearestDistance = nearestPoint.distanceSquaredTo(p);
        if (node.rect.distanceSquaredTo(p) < nearestDistance) {
            if (node.p.distanceSquaredTo(p) < nearestDistance) nearestPoint = node.p;
            nearestPoint = nearest(node.rt, p, nearestPoint);
            nearestPoint = nearest(node.lb, p, nearestPoint);
        }
        return nearestPoint;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        KdTree kdtree = new KdTree();
        In in = new In(args[0]);
        while (!in.isEmpty()) {
            Point2D p = new Point2D(in.readDouble(), in.readDouble());
            kdtree.insert(p);
        }
        // kdtree.nearest(new Point2D(0.5, 1.0));
        // Iterable<Point2D> points = kdtree.range(new RectHV(0.375, 0.875, 0.625, 1.0));
        // for (Point2D point:points) {
        //     StdOut.println(point.x() + " " + point.y());
        // }
        // StdOut.println(Boolean.toString(kdtree.contains(new Point2D(0.5, 0.375))));
        // StdOut.println(String.valueOf(kdtree.size()));
        // StdOut.println(Boolean.toString(kdtree.isEmpty()));
        StdOut.println("Nearest point: " + kdtree.nearest(new Point2D(0.873, 0.477)).x() + " " + kdtree.nearest(new Point2D(0.873, 0.477)).y());
        // kdtree.draw();
    }
}