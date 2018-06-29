/******************************************************************************
 *  Compilation:  javac TrieST.java
 *  Execution:
 *  Dependencies: StdIn.java
 *
 *  A string symbol table for A - Z, implemented
 *  using a 26-way trie.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class MyTrieST<Value> {
    private static final int R = 26;        // A - Z

    private Node root;      // root of trie
    private Node lastNode;
    private String lastPrefix = "";

    // R-way trie node
    private static class Node {
        private Object val;
        private Node[] next = new Node[R];
    }

   /**
     * Initializes an empty string symbol table.
     */
    public MyTrieST() {
    }


    /**
     * Returns the value associated with the given key.
     * @param key the key
     * @return the value associated with the given key if the key is in the symbol table
     *     and {@code null} if the key is not in the symbol table
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Value get(String key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        Node x = get(root, key, 0);
        if (x == null) return null;
        return (Value) x.val;
    }

    /**
     * Does this symbol table contain the given key?
     * @param key the key
     * @return {@code true} if this symbol table contains {@code key} and
     *     {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean contains(String key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = key.charAt(d);
        return get(x.next[c - 'A'], key, d+1);
    }

    /**
     * Inserts the key-value pair into the symbol table, overwriting the old value
     * with the new value if the key is already in the symbol table.
     * If the value is {@code null}, this effectively deletes the key from the symbol table.
     * @param key the key
     * @param val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(String key, Value val) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");
        else root = put(root, key, val, 0);
    }

    private Node put(Node x, String key, Value val, int d) {
        if (x == null) x = new Node();
        if (d == key.length()) {
            x.val = val;
            return x;
        }
        char c = key.charAt(d);
        x.next[c - 'A'] = put(x.next[c - 'A'], key, val, d+1);
        return x;
    }

    public boolean hasKeysWithPrefix(String prefix) {
        if (prefix == null) throw new IllegalArgumentException("Argument is null");
        if (lastNode == null) lastNode = root;
        Node x;
        int prefixL = prefix.length();
        if (prefixL > 1 && lastPrefix.equals(prefix.substring(0, prefixL - 2)) && prefix.substring(prefixL - 2).equals("QU")) {
            x = get(lastNode, "Q", 0);
        }
        else if (prefixL > 1 && lastPrefix.equals(prefix.substring(0, prefixL - 1))) {
            x = get(lastNode, prefix.substring(prefixL - 1), 0);
        }
        else x = get(root, prefix, 0);
        if (x != null) {
            lastNode = x;
            lastPrefix = prefix;
        }
        return x != null;
    }
}