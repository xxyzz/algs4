/******************************************************************************
 *  Compilation:  javac-algs4 Deque.java
 *  Execution:    java-algs4 Deque < ./queues/distinct.txt
 *  Dependencies: 
 *
 ******************************************************************************/
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
public class Deque<Item> implements Iterable<Item> {
    private Node first, last;
    private final Node nul;
    private int size;
    private class Node {
        private final Item item;
        private Node prev;
        private Node next;
        Node(Item item, Node prev, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
    // construct an empty deque
    public Deque() {
        nul = new Node(null, null, null);
        size = 0;
        first = nul;
        last = nul;
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext() {
            return current.next != null;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }
    // return the number of items on the deque
    public int size() {
        return size;
    }
    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node newfirst = new Node(item, nul, first);
        if (isEmpty()) {
            first = newfirst;
            last = newfirst;
        }
        else {
            first.prev = newfirst;
            first = newfirst;
        }
        size++;
    }
    // add the item to the end
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node newlast = new Node(item, last, nul);
        if (isEmpty()) {
            first = newlast;
            last = newlast;
        }
        else {
            last.next = newlast;
            last = newlast;
        }
        size++;
    }
    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = first.item;
        first = first.next;
        size--;
        if (isEmpty()) {
            last = first;
        }
        else {
            first.prev = nul;
        }
        return item;
    }
    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = last.item;
        last = last.prev;
        size--;
        if (isEmpty()) {
            first = last;
        }
        else {
            last.next = nul;
        }
        return item;
    }
    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
    // unit testing (optional)
    public static void main(String[] args) {
        Deque<String> s = new Deque<String>();
        
        s.addFirst("1");
        s.addFirst("2");
        s.addLast("3");

        for (String i : s) StdOut.println(i);

        StdOut.print(s.removeLast() + " ");
        StdOut.print(s.removeFirst() + " ");
        StdOut.print(s.removeFirst() + " \n");
            
        while (!StdIn.isEmpty()) {
            s.addFirst(StdIn.readString());
        }
        for (String i : s) StdOut.println(i);
    }
}