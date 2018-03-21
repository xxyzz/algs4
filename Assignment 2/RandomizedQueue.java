/******************************************************************************
 *  Compilation:  javac-algs4 RandomizedQueue.java
 *  Execution:    java-algs4 RandomizedQueue
 *  Dependencies: 
 *
 ******************************************************************************/
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int size;
    // construct an empty randomized queue
    public RandomizedQueue() {
        size = 0;
        items = (Item[]) new Object[1];
    }
    private class RandomizedQueueIterator implements Iterator<Item> {
        private int i = size;
        private final int[] order;
        public RandomizedQueueIterator() {
            order = new int[i];
            for (int j = 0; j < i; j++) {
                order[j] = j;
            }
            StdRandom.shuffle(order);
        }
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return items[order[--i]];
        }
        public boolean hasNext() {
            return i > 0;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }
    // return the number of items on the randomized queue
    public int size() {
        return size;
    }
    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        items[size++] = item;
        if (size == items.length) {
            resize(2 * items.length);
        }
    }
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            copy[i] = items[i];
        }
        items = copy;
    }
    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int randomIndex = StdRandom.uniform(size);
        Item item = items[randomIndex];
        items[randomIndex] = items[--size];
        items[size] = null;
        if (size > 0 && size == items.length/4) {
            resize(items.length / 2);
        }
        return item;
    }
    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return items[StdRandom.uniform(size)];
    }
    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }
    // unit testing (optional)
    // public static void main(String[] args) {}
}