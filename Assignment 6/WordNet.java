/******************************************************************************
 *  Compilation:  javac-algs4 WordNet.java
 *  Execution:    java-algs4 WordNet
 *  Dependencies:
 *
 *  DirectedCycle: https://algs4.cs.princeton.edu/42digraph/DirectedCycle.java.html
 *  SeparateChainingHashST: https://algs4.cs.princeton.edu/34hash/SeparateChainingHashST.java.html
 *  Bag: https://algs4.cs.princeton.edu/13stacks/Bag.java.html
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.princeton.cs.algs4.Bag;

public class WordNet {
    private final SeparateChainingHashST<Integer, Bag<String>> idHST;
    private final SeparateChainingHashST<String, Bag<Integer>> wordHST;
    private Digraph G;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        validateString(synsets);
        validateString(hypernyms);
        Digraph G = new Digraph();
        checkRooted(G);
        checkCycle(G);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {

    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        validateString(word);
        return wordHST.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        validateString(nounA);
        validateString(nounB);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        validateString(nounA);
        validateString(nounB);
    }

    // Corner cases: throw IllegalArgumentException if string is null
    private void validateString(string word) {
        if (word == null) throw IllegalArgumentException("Argument can't be null.");
    }

    // Corner cases: throw IllegalArgumentException if digraph is not DAG
    private void checkCycle(Digraph digraph) {
        DirectedCycle directedCycle = new DirectedCycle(digraph);
        if (directedCycle.hasCycle()) throw new IllegalArgumentException("The input does not correspond to a rooted DAG");
    }

    // Corner cases: throw IllegalArgumentException if digraph is not rooted
    private void checkRooted(Digraph digraph) {
        for (int i = 0; i < digraph.V(); i++) {
            if (digraph.outdegree(i) == 0 && digraph.indegree(i) > 0) return;
        }
        throw new IllegalArgumentException("The input does not correspond to a rooted DAG");
    }

    private void readSynsets(string synsets) {
        In in = new In(synsets);
        while (!in.isEmpty()) {
            String[] line = in.readLine().split(",");
            Bag<String> nounsBag = new Bag<>();
            for (String noun : line[1].split(" ")) {
                nounsBag.add(noun);
                Bag<Integer> idsBag = new Bag<>();
                if (wordHST.contains(noun)) idsBag = wordHST.get(noun);
                idsBag.add(Integer.parseInt(line[0]));
                wordHST.put(noun, idsBag);
            }
            idHST.put(Integer.parseInt(line[0]), nounsBag);
        }
        
    }

    // do unit testing of this class
    public static void main(String[] args) { }
}