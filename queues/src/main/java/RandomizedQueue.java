import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    // construct an empty randomized queue
    public RandomizedQueue(){

    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return false;
    }

    // return the number of items on the randomized queue
    public int size() {
        return -1;
    }

    // add the item
    public void enqueue(Item item) {
    }

    // remove and return a random item
    public Item dequeue() {
        return (Item) new Object();
    }

    // return a random item (but do not remove it)
    public Item sample() {
        return (Item) new Object();
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return (Iterator<Item>) new Object();
    }

    // unit testing (required)
    public static void main(String[] args) {
    }

}