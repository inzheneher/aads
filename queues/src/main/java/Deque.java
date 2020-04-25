import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first, last;
    private int size;

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        for (int i = 0; i < 1; i++) {
            deque.addFirst(i);
        }
        for (Integer integer : deque) {
            System.out.println(integer);
        }
    }

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        if (size() != 0) {
            first.prev = oldFirst;
            oldFirst.next = first;
        } else last = first;
        size++;
    }

    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node oldLast = last;
        last = new Node();
        last.item = item;
        if (size() != 0) {
            last.next = oldLast;
            oldLast.prev = last;
        } else first = last;
        size++;
    }

    public Item removeFirst() {
        if (size == 0) throw new NoSuchElementException();
        Item item = first.item;
        if (size == 1) first = last = null;
        else {
            Node prev = first.prev;
            prev.next = null;
            first = prev;
        }
        size--;
        return item;
    }

    public Item removeLast() {
        if (size == 0) throw new NoSuchElementException();
        Item item = last.item;
        if (size == 1) last = first = null;
        else {
            Node next = last.next;
            next.prev = null;
            last = next;
        }
        size--;
        return item;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {

        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.prev;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}