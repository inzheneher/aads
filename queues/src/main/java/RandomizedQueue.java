import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int head, tail;
    private Object[] elements;

    // construct an empty randomized queue
    public RandomizedQueue() {
        elements = new Object[16];
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        for (int i = 0; i < 20; i++) {
            rq.enqueue(String.valueOf(i));
        }
        for (String s : rq) {
            System.out.println(s);
        }
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return head == tail;
    }

    // return the number of items on the randomized queue
    public int size() {
        return elements.length;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        elements[head = (head - 1) & (elements.length - 1)] = item;
        if (head == tail) doubleCapacity();
    }

    // remove and return a random item
    public Item dequeue() {
        Item result = sample();
        elements[head] = null;
        head = (head + 1) & (elements.length - 1);
        return result;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        int rand = (int) StdRandom.uniform(head, elements.length);
        Item result = (Item) elements[rand];
        if (result == null) throw new NoSuchElementException();
        if (rand != head) {
            elements[rand] = elements[head];
        }
        return result;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }

    private class RandomizedIterator implements Iterator<Item> {

        private int cursor = head;
        private final int fence = tail;

        @Override
        public boolean hasNext() {
            return cursor != fence;
        }

        @Override
        public Item next() {
            if (cursor == fence) throw new NoSuchElementException();
            cursor = StdRandom.uniform(head, elements.length) & (elements.length - 1);
            Item result = (Item) elements[cursor];
            return result;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void forEachRemaining(Consumer<? super Item> action) {
            throw new UnsupportedOperationException();
        }
    }

    private void doubleCapacity() {
        int h = head;
        int n = elements.length;
        int r = n - h;
        int newCapacity = n << 1;
        if (newCapacity < 0) throw new IllegalStateException();
        Object[] a = new Object[newCapacity];
        System.arraycopy(elements, h, a, 0, r);
        System.arraycopy(elements, 0, a, r, h);
        elements = a;
        head = 0;
        tail = n;
    }
}