/******************************************************************************
 *  Compilation:  javac-algs4 Outcast.java
 *  Execution:    java-algs4 Outcast
 *  Dependencies: WordNet.java
 *
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private final WordNet wordNet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        wordNet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        String leastRelated = "";
        int leastDist = 0;
        for (String nounA : nouns) {
            int nowDist = 0;
            for (String nounB : nouns) {
                nowDist += wordNet.distance(nounA, nounB);
            }
            if (nowDist > leastDist) {
                leastRelated = nounA;
                leastDist = nowDist;
            }
        }
        return leastRelated;
    }
    
    // see test client below
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}