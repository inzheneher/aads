import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationWeightedQuickUnionUF {

    private WeightedQuickUnionUF quickUnionUF;
    private int[] id;
    private int count;

    public PercolationWeightedQuickUnionUF(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        count = n;
        id = new int[n];
        for (int i = 0; i < n; i++)
            id[i] = i;
        quickUnionUF = new WeightedQuickUnionUF(n);
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        PercolationWeightedQuickUnionUF uf = new PercolationWeightedQuickUnionUF(n);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (uf.find(p) == uf.find(q)) continue;
            uf.union(p, q);
            StdOut.println(p + " " + q);
        }
        StdOut.println(uf.count() + " components");
//        uf.print2DArray(uf.quickUnionUF.);
    }

    private void print2DArray(int[][] arr) {
        int arrLength = arr.length;
        for (int[] ints : arr) {
            for (int j = 0; j < arrLength; j++) {
                System.out.print(ints[j] + " ");
            }
            System.out.println();
        }
    }

    public void union(int p, int q) {
        int pID = id[p];   // needed for correctness
        int qID = id[q];   // to reduce the number of array accesses

        // p and q are already in the same component
        if (id[p] == qID) return;

        for (int i = 0; i < id.length; i++)
            if (id[i] == pID) id[i] = qID;
        count--;
    }

    public int find(int p) {
        return id[p];
    }

    public int count() {
        return count;
    }
}
