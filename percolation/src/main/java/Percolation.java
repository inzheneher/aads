import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.Arrays;
import java.util.stream.Stream;

public class Percolation {

    private final int[] nByNGrid;
    private int[] sizeArr;
    private boolean[][] opened;
    private int numberOfOpenSites;
    private int size;
    private int count;
    private final int top;
    private final int bottom;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        count = n * n;
        top = 0;
        bottom = n * n + 1;
        size = n;
        opened = new boolean[n][n];
        nByNGrid = new int[n * n + 2];
        sizeArr = new int[n * n + 2];
        for (int i = 0; i < nByNGrid.length; i++) {
            nByNGrid[i] = i;
            sizeArr[i] = 1;
        }
        for (int i = 0; i < n; i++) {
            union(0, getArrayIndexFor(0, i));
            union(n * n + 1, getArrayIndexFor(size - 1, i));
        }
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(20);
        percolation.printArray(percolation.nByNGrid);
        System.out.println();

        int n = percolation.nByNGrid.length;
        int row, col;
        percolation.print2DArray(percolation.opened);
        double start = System.nanoTime();

        while (!percolation.percolates()) {
            row = StdRandom.uniform(percolation.size);
            col = StdRandom.uniform(percolation.size);
            percolation.open(row, col);
        }

        percolation.print2DArray(percolation.opened);

        System.out.println("Time for percolation is: ".concat(String.valueOf((System.nanoTime() - start) / 1000_000)).concat(" ms"));
        System.out.println();
        percolation.printArray(percolation.nByNGrid);
        System.out.println();
        System.out.println("Number of open sites is: ".concat(String.valueOf(percolation.numberOfOpenSites())));
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        if (!isOpen(row, col)) {
            opened[row][col] = true;
            numberOfOpenSites++;
            //left lookup
            if (col > 0 && isOpen(row, col - 1)) union(getArrayIndexFor(row, col - 1), getArrayIndexFor(row, col));
            if (col < size - 1 && isOpen(row, col + 1)) union(getArrayIndexFor(row, col + 1), getArrayIndexFor(row, col));
            if (row < size - 1 && isOpen(row + 1, col)) union(getArrayIndexFor(row + 1, col), getArrayIndexFor(row, col));
            if (row > 0 && isOpen(row - 1, col)) union(getArrayIndexFor(row - 1, col), getArrayIndexFor(row, col));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return opened[row][col];
    }

    /**
     * A full site is an open site that can be connected to an open site
     * in the top row via a chain of neighboring (left, right, up, down) open sites.
     */
    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        return isOpen(row, col) && nByNGrid[getArrayIndexFor(row, col)] == 0;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    /**
     * The system percolates if there is a full site in the bottom row.
     */
    // does the system percolate?
    public boolean percolates() {
        return nByNGrid[bottom] == nByNGrid[top];
    }

    public int getArrayIndexFor(int row, int col) {
        if ((row < 0 || row >= size) || (col < 0 || col >= size)) throw new IllegalArgumentException();
        return row * size + col + 1;
    }

    private int root(int[] id, int i) {
        while (i != id[i]) i = id[i];
        return i;
    }

    public boolean connected(int[] id, int p, int q) {
        return root(id, p) == root(id, q);
    }

    public int find(int p) {
        validate(p);
        while (p != nByNGrid[p]) p = nByNGrid[p];
        return p;
    }

    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;

        // make smaller root point to larger one
        if (sizeArr[rootP] < sizeArr[rootQ]) {
            nByNGrid[rootP] = rootQ;
            sizeArr[rootQ] += sizeArr[rootP];
        }
        else {
            nByNGrid[rootQ] = rootP;
            sizeArr[rootP] += sizeArr[rootQ];
        }
        count--;
    }

    private void printArray(int[] arr) {
        Arrays.stream(arr).forEach(result -> System.out.print(result + " "));
        System.out.println();
    }

    private void print2DArray(int[][] arr) {
        for (int[] ints : arr) {
            Arrays.stream(ints).forEach(result -> System.out.print(result + " "));
            System.out.println();
        }
    }

    private void print2DArray(boolean[][] arr) {
        for (boolean[] booleans : arr) {
            Stream.of(booleans).forEach(result -> System.out.print(Arrays.toString(result) + " "));
            System.out.println();
        }
        System.out.println();
    }

    private void validate(int a) {
        int n = nByNGrid.length;
        if (a < 0 || a > n - 1) {
            throw new IllegalArgumentException("Index "
                    .concat(String.valueOf(a))
                    .concat(" must be between 0 and ")
                    .concat(String.valueOf(n - 1)));
        }
    }

    private void validate(int row, int col) {
        validate(row);
        validate(col);
    }

    public int[] getNByNGrid() {
        return nByNGrid;
    }
}
