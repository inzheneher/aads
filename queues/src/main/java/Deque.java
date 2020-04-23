import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private int head, tail;
    private Object[] elements;

    public Deque() {
        elements = new Object[2];
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        System.out.println(deque.elements.length);
        for (int i = 0; i < 10; i++) {
            deque.addFirst(i);
        }
        System.out.println(deque.elements.length);
        for (int i = 0; i < 10; i++) {
            deque.removeLast();
        }
        System.out.println(deque.elements.length);
    }

    public boolean isEmpty() {
        return head == tail;
    }

    public int size() {
        return (tail - head) & (elements.length - 1);
    }

    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        head = (head - 1) & (elements.length - 1);
        elements[head] = item;
        if (head == tail) doubleCapacity();
    }

    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        elements[tail] = item;
        tail = (tail + 1) & (elements.length - 1);
        if (tail == head) doubleCapacity();
    }

    public Item removeFirst() {
        if (elements.length == 0) throw new NoSuchElementException();
        int n = head;
        Item result = (Item) elements[n];
        if (result == null) throw new NoSuchElementException();
        elements[n] = null;
        head = (n + 1) & (elements.length - 1);
        if (tail - head > 0 && tail - head <= elements.length / 4) halvingCapacity();
        return result;
    }

    public Item removeLast() {
        if (elements.length == 0) throw new NoSuchElementException();
        int t = (tail - 1) & (elements.length - 1);
        Item result = (Item) elements[t];
        if (result == null) throw new NoSuchElementException();
        elements[t] = null;
        tail = t;
        if (head - tail > 0 && elements.length - head + tail <= elements.length / 4) {
            halvingCapacity2();
        } else if (head - tail < 0 && tail <= elements.length / 4) halvingCapacity2();
        return result;
    }

    public Iterator<Item> iterator() {
        return new DeqIterator();
    }

    private class DeqIterator implements Iterator<Item> {

        private int cursor = head;
        private final int fence = tail;

        @Override
        public boolean hasNext() {
            return cursor != fence;
        }

        @Override
        public Item next() {
            if (cursor == fence) throw new NoSuchElementException();
            Item result = (Item) elements[cursor];
            cursor = (cursor + 1) & (elements.length - 1);
            return result;
        }

        @Override
        public void remove() {
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

    private void halvingCapacity() {
        int h = head;
        int n = elements.length;
        int t = tail;
        int r = n - h;
        int newCapacity = elements.length >> 1;
        Object[] a = new Object[newCapacity];
        System.arraycopy(elements, h, a, 0, t - h);
        elements = a;
        tail = head;
        head = 0;
    }

    private void halvingCapacity2() {
        int h = head;
        int n = elements.length;
        int t = tail;
        int r = n - h;
        int newCapacity = elements.length >> 1;
        Object[] a = new Object[newCapacity];
        if (head != 0) {
            System.arraycopy(elements, 0, a, t, r);
        }
        System.arraycopy(elements, h, a, 0, t);
        elements = a;
        tail = r + t;
        head = 0;
    }
}