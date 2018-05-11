/******************************************************************************
 *  Compilation:  javac-algs4 SAP.java
 *  Execution:    java-algs4 SAP wordnet/digraph1.txt
 *                3 11
 *  Dependencies:
 *
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;

public class SAP {
    private final Digraph digraph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) throw new IllegalArgumentException("Digraph G can't be null.");
        digraph = G;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        validateVertex(v);
        validateVertex(w);

        BreadthFirstDirectedPaths vpaths = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths wpaths = new BreadthFirstDirectedPaths(digraph, w);

        return spa(vpaths, wpaths, true);
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        validateVertex(v);
        validateVertex(w);

        BreadthFirstDirectedPaths vpaths = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths wpaths = new BreadthFirstDirectedPaths(digraph, w);

        return spa(vpaths, wpaths, false);
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        validateVertices(v);
        validateVertices(w);

        BreadthFirstDirectedPaths vpaths = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths wpaths = new BreadthFirstDirectedPaths(digraph, w);

        return spa(vpaths, wpaths, true);
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        validateVertices(v);
        validateVertices(w);

        BreadthFirstDirectedPaths vpaths = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths wpaths = new BreadthFirstDirectedPaths(digraph, w);

        return spa(vpaths, wpaths, false);
    }

    private int spa(BreadthFirstDirectedPaths vpaths, BreadthFirstDirectedPaths wpaths, boolean getLength) {
        int minLength = Integer.MAX_VALUE;
        int ancestor = -1;
        for (int i = 0; i < digraph.V(); i++) {
            if (vpaths.hasPathTo(i) && wpaths.hasPathTo(i)) {
                int thisLength = vpaths.distTo(i) + wpaths.distTo(i);
                if (thisLength < minLength) {
                    minLength = thisLength;
                    ancestor = i;
                }
            }
        }
        if (getLength) return minLength != Integer.MAX_VALUE ? minLength : -1;
        return ancestor;
    }

    // Corner cases
    private void validateVertex(int v) {
        int V = digraph.V();
        if (v < 0 || v >= V) throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    // Corner cases
    private void validateVertices(Iterable<Integer> vertices) {
        if (vertices == null) {
            throw new IllegalArgumentException("argument is null");
        }
        int V = digraph.V();
        for (int v : vertices) {
            if (v < 0 || v >= V) {
                throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
            }
        }
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}