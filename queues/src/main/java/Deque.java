import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    // construct an empty deque
    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        return false;
    }

    // return the number of items on the deque
    public int size() {
        return -1;
    }

    // add the item to the front
    public void addFirst(Item item) {
    }

    // add the item to the back
    public void addLast(Item item) {
    }

    // remove and return the item from the front
    public Item removeFirst() {
        return (Item) new Object();
    }

    // remove and return the item from the back
    public Item removeLast() {
        return (Item) new Object();
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return (Iterator<Item>) new Object();
    }

    // unit testing (required)
    public static void main(String[] args) {
    }

}