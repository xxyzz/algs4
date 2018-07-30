/******************************************************************************
 *  Compilation:  javac-algs4 MoveToFront.java
 *  Execution:    java-algs4 MoveToFront - < burrows/abra.txt | java-algs4 edu.princeton.cs.algs4.HexDump 16
 *      java-algs4 MoveToFront - < burrows/abra.txt | java-algs4 MoveToFront +
 *  Dependencies: BinaryStdIn, BinaryStdOut
 *
 *  
 *
 ******************************************************************************/
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
    private static final int R = 256;
    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        char[] characters = new char[R];
        for (char i = 0; i < R; i++)
            characters[i] = i;

        while (!BinaryStdIn.isEmpty()) {
            char inputChar = BinaryStdIn.readChar();
            
            for (char i = 0; i < R; i++) {
                if (characters[i] == inputChar) {
                    // output the 8-bit index
                    BinaryStdOut.write(i);
                    System.arraycopy(characters, 0, characters, 1, i);
                    // move to front
                    characters[0] = inputChar;
                    break;
                }
            }
        }
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        char[] characters = new char[R];
        for (char i = 0; i < R; i++)
            characters[i] = i;

        while (!BinaryStdIn.isEmpty()) {
            int inputNumber = BinaryStdIn.readInt(8);
            char movedChar = characters[inputNumber];
            BinaryStdOut.write(movedChar);
            System.arraycopy(characters, 0, characters, 1, inputNumber);
            // move to front
            characters[0] = movedChar;
        }
        BinaryStdOut.close();
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) encode();
        if (args[0].equals("+")) decode();
    }
}