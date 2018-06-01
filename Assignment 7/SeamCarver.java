/******************************************************************************
 *  Compilation:  javac-algs4 SeamCarver.java
 *  Execution:    java-algs4 SeamCarver
 *  Dependencies:
 *
 * Picture: https://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/Picture.html
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
        this.picture = new Picture(picture);
        width = this.picture.width();
        height = this.picture.height();
    }

    // current picture
    public Picture picture() {
        return new Picture(picture);
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
    public int[] findHorizontalSeam() {
        transpose(true);
        int[] sp = findVerticalSeam();
        transpose(false);
        sp = reverseArray(sp);

        return sp;
    }

    private int[] reverseArray(int[] array) {
        int arrayLength = array.length;
        for (int i = 0; i < arrayLength / 2; i++) {
            int temp = array[i];
            array[i] = array[arrayLength - i - 1];
            array[arrayLength - i - 1] = temp;
        }

        return array;
    }

    // HORIZONTAL: true, VERTICAL: false
    private void transpose(boolean direction) {
        height = picture.width();
        width = picture.height();
        Picture tsPicture = new Picture(width, height);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (direction) tsPicture.setRGB(j, i, picture.getRGB(height - i - 1, j));
                else tsPicture.setRGB(j, i, picture.getRGB(i, width - j - 1));
            }
        }
        picture = tsPicture;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        double[][] energy = new double[width][height];
        double[][] energyTo = new double[width][height];
        int[][] edgeTo = new int[width][height];
        double minEnergy = Double.POSITIVE_INFINITY;
        int[] sp = new int[height];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i == 0) {
                    energyTo[j][i] = 0;
                }
                else {
                    energy[j][i] = energy(j, i);
                    energyTo[j][i] = Double.POSITIVE_INFINITY;
                }
            }
        }

        for (int i = 0; i < height - 1; i++) {
            for (int j = 0; j < width; j++) {
                for (int k = -1; k < 2; k++) {
                    if (j + k < 0 || j + k > width - 1) {
                        continue;
                    }
                    else if (energyTo[j + k][i + 1] > energyTo[j][i] + energy[j + k][i + 1]) {
                        energyTo[j + k][i + 1] = energyTo[j][i] + energy[j + k][i + 1];
                        edgeTo[j + k][i + 1] = j;
                    }
                }
            }
        }

        for (int j = 0; j < width; j++) {
            if (energyTo[j][height - 1] < minEnergy) {
                minEnergy = energyTo[j][height - 1];
                int y = height - 1;
                int x = j;
                while (y > -1) {
                    sp[y] = x;
                    x = edgeTo[x][y];
                    y--;
                }
            }
        }

        return sp;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        // corner cases
        if (height <= 1) throw new IllegalArgumentException("The height of the picture is <= 1");
        checkSeamValid(seam);
        if (seam.length != width) throw new IllegalArgumentException("Wrong array length");

        transpose(true);
        seam = reverseArray(seam);
        removeVerticalSeam(seam);
        transpose(false);
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        // corner cases
        if (width <= 1) throw new IllegalArgumentException("The width of the picture is <= 1");
        checkSeamValid(seam);
        if (seam.length != height) throw new IllegalArgumentException("Wrong array length");

        Picture rmPicture = new Picture(width - 1, height);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (j < seam[i]) rmPicture.setRGB(j, i, picture.getRGB(j, i));
                else if (j > seam[i]) rmPicture.setRGB(j - 1, i, picture.getRGB(j, i));
            }
        }
        width--;
        picture = rmPicture;

        // for (int i = 1; i < height; i++) {
        //     if (seam[i] > 0) energy[seam[i] - 1][i] = energy(seam[i] - 1, i);
        //     if (seam[i] < width) energy[seam[i]][i] = energy(seam[i], i);
        // }
    }

    /* corner cases:
     * check if seam is null
     * check if two adjacent entries differ by more than 1
     */
    private void checkSeamValid(int[] seam) {
        if (seam == null) throw new IllegalArgumentException("Argument is null");
        for (int i = 0; i < seam.length - 1; i++) {
            if (Math.abs(seam[i] - seam[i + 1]) > 1) throw new IllegalArgumentException("The array is not a valid seam");
        }
    }
}