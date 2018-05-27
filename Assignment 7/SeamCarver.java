/******************************************************************************
 *  Compilation:  javac-algs4 SeamCarver.java
 *  Execution:    java-algs4 SeamCarver
 *  Dependencies:
 *
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private final int width;
    private final int height;
    private final Picture picture;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        // corner case
        if (picture == null) throw new IllegalArgumentException("Argument is null");
        // Creates a new picture that is a deep copy of the argument picture.
        this.picture = new Picture(picture);
        width = this.picture.width();
        height = this.picture.height();
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

        // border pixels
        if (x == 0 || y == 0 || x == width - 1 || y == height - 1) return 1000;

        return Math.sqrt(xGradientSquare(x, y) + yGradientSquare(x, y));
    }

    private double xGradientSquare(int x, int y) {
        int leftRGB = picture.getRGB(x - 1, y);
        int rightRGB = picture.getRGB(x + 1, y);
        return sumSquare(leftRGB, rightRGB);
    }

    private double yGradientSquare(int x, int y) {
        int upRGB = picture.getRGB(x, y - 1);
        int downRGB = picture.getRGB(x, y + 1);
        return sumSquare(upRGB, downRGB);
    }

    private double sumSquare(int rgbA, int rgbB) {
        int r = ((rgbA >> 16) & 0xFF) - ((rgbB >> 16) & 0xFF);
        int g = ((rgbA >> 8) & 0xFF) - ((rgbB >> 8) & 0xFF);
        int b = (rgbA & 0xFF) - (rgbB & 0xFF);

        return Math.pow(r, 2) + Math.pow(g, 2) + Math.pow(b, 2);
    }

    // sequence of indices for horizontal seam
    // public int[] findHorizontalSeam() {
        
    // }

    // sequence of indices for vertical seam
    // public int[] findVerticalSeam() {
        
    // }

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