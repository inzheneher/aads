import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Object[] elements;
    private int n;

    // construct an empty randomized queue
    public RandomizedQueue() {
        elements = new Object[2];
        n = 0;
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        for (int i = 0; i < 100; i++) {
            rq.enqueue(String.valueOf(i));
        }
        for (int i = 0; i < 1; i++) {
            for (String s : rq) {
                System.out.println(s);
            }
        }
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (n == elements.length) doubleCapacity();
        elements[n++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (size() == 0) throw new NoSuchElementException();
        int rand = StdRandom.uniform(n);
        Item result = (Item) elements[rand];
        if (rand != n - 1) elements[rand] = elements[n - 1];
        elements[n - 1] = null;
        n--;
        if (n > 0 && n < elements.length / 4) halvingCapacity();
        return result;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size() == 0) throw new NoSuchElementException();
        int rand = StdRandom.uniform(n);
        Item result = (Item) elements[rand];
        return result;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }

    private class RandomizedIterator implements Iterator<Item> {

        private int copySize = n;
        private Object[] copy = new Object[copySize];

        public RandomizedIterator() {
            System.arraycopy(elements, 0, copy, 0, copySize);
        }

        @Override
        public boolean hasNext() {
            return copySize > 0;
        }

        @Override
        public Item next() {
            if (copySize == 0) throw new NoSuchElementException();
            int rand = StdRandom.uniform(copySize);
            Item result = (Item) copy[rand];
            if (rand != copySize - 1) copy[rand] = copy[copySize - 1];
            copy[copySize - 1] = null;
            copySize--;
            return result;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private void doubleCapacity() {
        int newCapacity = elements.length << 1;
        Object[] a = new Object[newCapacity];
        System.arraycopy(elements, 0, a, 0, n);
        elements = a;
    }

    private void halvingCapacity() {
        int newCapacity = elements.length >> 1;
        Object[] a = new Object[newCapacity];
        System.arraycopy(elements, 0, a, 0, n);
        elements = a;
    }
}