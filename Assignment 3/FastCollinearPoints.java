/******************************************************************************
 *  Compilation:  javac-algs4 FastCollinearPoints.java
 *  Execution:    java-algs4 FastCollinearPoints
 *  Dependencies: Point.java
 *  
 *
 ******************************************************************************/

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class FastCollinearPoints {
    private final LineSegment[] lineSegments;
    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
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
            Point[] psclone1 = psclone.clone();
            Arrays.sort(psclone1, psclone1[i].slopeOrder());
            int j = 1;
            while (j < n - 2) {
                int k = j + 1;
                double nowSlop = psclone1[0].slopeTo(psclone1[j]);
                double nextSlop;
                do {
                    if (k == n) {
                        k++;
                        break;
                    }
                    nextSlop = psclone1[0].slopeTo(psclone1[k++]);
                }
                while (nowSlop == nextSlop);
                // only add 4 or more points in a line
                if (k - j < 4) {
                    j++;
                    continue;
                }
                Point[] psclone2 = Arrays.copyOfRange(psclone1, j, --k);
                Arrays.sort(psclone2);
                // shoud use < 0, > 0 instead of == 1, == -1
                if (psclone1[0].compareTo(psclone2[0]) < 0) {
                    list.add(new LineSegment(psclone1[0], psclone2[psclone2.length - 1]));
                }
                j = k;
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