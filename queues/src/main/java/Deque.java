import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private int head, tail;
    private static final int CAPACITY = 16;
    private Object[] elements;

    public Deque() {
        elements = new Object[CAPACITY];
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        System.out.println(deque.size());
        for (int i = 0; i < 100; i++) {
            deque.addFirst(i);
        }
        for (Integer integer : deque) {
            System.out.println(integer);
        }
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
        return result;
    }

    public Item removeLast() {
        if (elements.length == 0) throw new NoSuchElementException();
        int t = (tail - 1) & (elements.length - 1);
        Item result = (Item) elements[t];
        if (result == null) throw new NoSuchElementException();
        elements[t] = null;
        tail = t;
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
}