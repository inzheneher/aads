import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.NoSuchElementException;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        int n = args.length == 0 ? 1 : Integer.parseInt(args[0]);
        try {
            String str = StdIn.readString();
            while (str != null) {
                rq.enqueue(str);
                str = StdIn.readString();
            }
        } catch (NoSuchElementException ignored) {
        }
        while (n > 0) {
            n--;
            StdOut.printf("%s\n", rq.dequeue());
        }
    }
}