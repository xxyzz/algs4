/******************************************************************************
 *  Compilation:  javac-algs4 BruteCollinearPoints.java
 *  Execution:    java-algs4 BruteCollinearPoints
 *  Dependencies: Point.java
 *  
 *
 ******************************************************************************/

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class BruteCollinearPoints {
    private final LineSegment[] lineSegments;
    /**
     * finds all line segments containing 4 points
     * 
     * @param points 4 points
     */
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        int n = points.length;
        for (int i = 0; i < n; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
            for (int j = i + 1; j < n; j++) {
                // check if the last element is null
                if (points[j] == null || points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }
        Point[] psclone = points.clone();
        Arrays.sort(psclone);
        List<LineSegment> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    double slop1 = psclone[i].slopeTo(psclone[j]);
                    double slop2 = psclone[i].slopeTo(psclone[k]);
                    if (slop1 == slop2) {
                        for (int m = k + 1; m < n; m++) {
                            if (slop1 == psclone[i].slopeTo(psclone[m])) {
                                list.add(new LineSegment(psclone[i], psclone[m]));
                            }
                        }
                    }
                }
            }
        }
        lineSegments = list.toArray(new LineSegment[0]);
    }
    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.length;
    }
    // the line segments
    public LineSegment[] segments() {
        return lineSegments.clone();
    }
}