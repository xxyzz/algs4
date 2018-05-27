/******************************************************************************
 *  Compilation:  javac-algs4 SeamCarver.java
 *  Execution:    java-algs4 SeamCarver
 *  Dependencies:
 *
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private int width;
    private int height;
    private Picture picture;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        // corner case
        if (picture == null) throw new IllegalArgumentException("Argument is null");
        // Creates a new picture that is a deep copy of the argument picture.
        picture = new Picture(picture);
        width = picture.width();
        height = picture.height();
    }

    // current picture
    public Picture picture() {
        return picture;
    }

    // width of current picture
    public int width() {
        return width;
    }

    // height of current picture
    public int height() {
        return height;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        // corner case
        if (x < 0 || x > width - 1 || y < 0 || y > height - 1) throw new IllegalArgumentException("x or y is outside its prescribed range");
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {

    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {

    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        // corner cases
        if (height <= 1) throw new IllegalArgumentException("The height of the picture is <= 1");
        if (seam.length != width) throw new IllegalArgumentException("Wrong array length");
        checkSeamValid(seam);
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        // corner cases
        if (width <= 1) throw new IllegalArgumentException("The width of the picture is <= 1");
        if (seam.length != height) throw new IllegalArgumentException("Wrong array length");
        checkSeamValid(seam);
    }

    /* corner cases:
     * check if seam is null
     * check if two adjacent entries differ by more than 1
     */
     private void checkSeamValid(int[] seam) {
        if (seam == null) throw new IllegalArgumentException("Argument is null");
        for (int i = 0; i < seam.length - 1; i++) {
            if (Math.abs(seam[i] - seam[i + 1]) != 1) throw new IllegalArgumentException("The array is not a valid seam");
        }
    }
}