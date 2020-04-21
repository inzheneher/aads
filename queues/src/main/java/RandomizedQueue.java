import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int head, tail;
    private Object[] elements;
    private int[] pointers;
    private int capacity = 16;

    // construct an empty randomized queue
    public RandomizedQueue() {
        elements = new Object[capacity];
        pointers = new int[capacity];
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
        return head == tail;
    }

    // return the number of items on the randomized queue
    public int size() {
        return (tail - head) & (elements.length - 1);
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        head = (head - 1) & (elements.length - 1);
        elements[head] = item;
        pointers[head] = head;
        if (head == tail) doubleCapacity();
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        int rand = head > tail ?
                StdRandom.uniform(head, elements.length) :
                StdRandom.uniform(head, tail);
        Item result = (Item) elements[rand];
        if (result == null) throw new NoSuchElementException();
        if (rand != head) elements[rand] = elements[head];
        elements[head] = null;
        head = (head + 1) & (elements.length - 1);
        return result;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        int rand = StdRandom.uniform(head, elements.length);
        Item result = (Item) elements[rand];
        if (result == null) throw new NoSuchElementException();
        return result;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }

    private class RandomizedIterator implements Iterator<Item> {

        private int cursor = head;
        private final int fence = tail;
        private final int[] temPointers = new int[capacity];
        private boolean isCopied;

        @Override
        public boolean hasNext() {
            return cursor != fence;
        }

        @Override
        public Item next() {
            if (cursor == fence) throw new NoSuchElementException();
            copyArray();
            int rand = cursor > fence ?
                    StdRandom.uniform(cursor, temPointers.length) & (temPointers.length - 1) :
                    StdRandom.uniform(cursor, tail) & (temPointers.length - 1);
            int pointer = temPointers[rand];
            Item result = (Item) elements[pointer];
            if (rand != head) temPointers[rand] = temPointers[cursor];
            temPointers[cursor] = 0;
            cursor = (cursor + 1) & (temPointers.length - 1);
            return result;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        private void copyArray() {
            if (!isCopied) {
                System.arraycopy(pointers, 0, temPointers, 0, pointers.length);
                isCopied = true;
            }
        }
    }

    private void doubleCapacity() {
        int h = head;
        int n = elements.length;
        int r = n - h;
        int newCapacity = n << 1;
        if (newCapacity < 0) throw new IllegalStateException();
        Object[] a = new Object[newCapacity];
        int[] b = new int[newCapacity];
        System.arraycopy(elements, h, a, 0, r);
        System.arraycopy(elements, 0, a, r, h);
        System.arraycopy(pointers, h, b, 0, r);
        System.arraycopy(pointers, 0, b, r, h);
        elements = a;
        pointers = b;
        head = 0;
        tail = n;
        capacity = newCapacity;
    }
}