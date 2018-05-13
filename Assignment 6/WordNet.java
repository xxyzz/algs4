/******************************************************************************
 *  Compilation:  javac-algs4 WordNet.java
 *  Execution:    java-algs4 WordNet wordnet/synsets.txt wordnet/hypernyms.txt
 *  Dependencies: SAP.java
 *
 *  Digraph: https://algs4.cs.princeton.edu/42digraph/Digraph.java.html
 *  DirectedCycle: https://algs4.cs.princeton.edu/42digraph/DirectedCycle.java.html
 *  SeparateChainingHashST: https://algs4.cs.princeton.edu/34hash/SeparateChainingHashST.java.html
 *  Bag: https://algs4.cs.princeton.edu/13stacks/Bag.java.html
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
// import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.princeton.cs.algs4.Bag;
import java.util.Arrays;

public class WordNet {
    private final SeparateChainingHashST<Integer, Bag<String>> idHST;
    private final SeparateChainingHashST<String, Bag<Integer>> wordHST;
    private Digraph G;
    private final SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        validateString(synsets);
        validateString(hypernyms);

        idHST = new SeparateChainingHashST<>();
        wordHST = new SeparateChainingHashST<>();

        readSynsets(synsets);
        readHypernyms(hypernyms);

        checkRooted(G);
        checkCycle(G);

        sap = new SAP(G);
        // StdOut.println("Vertices: " + G.V());
        // StdOut.println("Edges: " + G.E());
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return wordHST.keys();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        validateString(word);
        return wordHST.contains(word);
    }

    // distance between nounA and nounB
    // the minimum length of any ancestral path between any synset v of A and any synset w of B.
    public int distance(String nounA, String nounB) {
        validateNouns(nounA, nounB);

        Iterable<Integer> a = wordHST.get(nounA);
        Iterable<Integer> b = wordHST.get(nounB);

        return sap.length(a, b);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        validateNouns(nounA, nounB);

        Iterable<Integer> a = wordHST.get(nounA);
        Iterable<Integer> b = wordHST.get(nounB);

        Bag<String> bag = idHST.get(sap.ancestor(a, b));

        // use StringBuilder in loop
        StringBuilder synset = new StringBuilder();
        for (String noun: bag) {
            synset.append(noun);
        }

        return synset.toString();
    }

    // Corner case: throw IllegalArgumentException if string is null
    private void validateString(String word) {
        if (word == null) throw new IllegalArgumentException("Argument can't be null.");
    }

    // Corner case: both of the noun arguments should be  WordNet nouns.
    private void validateNouns(String nounA, String nounB) {
        validateString(nounA);
        validateString(nounB);
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException("Both input nouns should be WordNet nouns.");
    }

    // Corner case: throw IllegalArgumentException if digraph is not DAG
    private void checkCycle(Digraph digraph) {
        DirectedCycle directedCycle = new DirectedCycle(digraph);
        if (directedCycle.hasCycle()) throw new IllegalArgumentException("The input does not correspond to a rooted DAG, it has cycle.");
    }

    // Corner case: throw IllegalArgumentException if digraph is not rooted
    private void checkRooted(Digraph digraph) {
        for (int i = 0; i < digraph.V(); i++) {
            if (digraph.outdegree(i) == 0 && digraph.indegree(i) > 0) return;
        }
        throw new IllegalArgumentException("The input does not correspond to a rooted DAG, it doesn't rooted.");
    }

    private void readSynsets(String synsets) {
        In in = new In(synsets);
        int id = 0;
        while (!in.isEmpty()) {
            String[] line = in.readLine().split(",");
            id = Integer.parseInt(line[0]);
            Bag<String> nounsBag = new Bag<>();
            for (String noun : line[1].split(" ")) {
                nounsBag.add(noun);
                Bag<Integer> idsBag = new Bag<>();
                if (wordHST.contains(noun)) idsBag = wordHST.get(noun);
                idsBag.add(id);
                wordHST.put(noun, idsBag);
            }
            idHST.put(id, nounsBag);
        }
        G = new Digraph(id + 1);
    }

    private void readHypernyms(String hypernyms) {
        In in = new In(hypernyms);
        while (!in.isEmpty()) {
            String[] line = in.readLine().split(",");
            for (String hypernymId : Arrays.copyOfRange(line, 1, line.length)) {
                G.addEdge(Integer.parseInt(line[0]), Integer.parseInt(hypernymId));
            }
        }
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);

        // StdOut.println("All wordnet nouns:");
        // for (String noun : wordnet.nouns()) {
        //     StdOut.println(noun);
        // }

        // StdOut.println(String.valueOf(wordnet.isNoun(args[2])));

        StdOut.println("Distance of white_marlin and mileage: " + wordnet.distance("white_marlin", "mileage"));
        StdOut.println("Distance of Black_Plague and black_marlin: " + wordnet.distance("Black_Plague", "black_marlin"));
        StdOut.println("Distance of American_water_spaniel and histology: " + wordnet.distance("American_water_spaniel", "histology"));
        StdOut.println("Distance of Brown_Swiss and barrel_roll: " + wordnet.distance("Brown_Swiss", "barrel_roll"));
        StdOut.println("Shortest ancestral path of individual and edible_fruit: " + wordnet.sap("individual", "edible_fruit"));
    }
}