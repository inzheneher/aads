import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

public class Percolation {

    private int[][] nByNGrid;
    private int numberOfOpenSites;


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        nByNGrid = new int[n][n];
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        if (!isOpen(row, col)) {
            nByNGrid[row - 1][col - 1] = col;
            if (validateCornerCases(row, col - 1) && isOpen(row, col)) union(row, col);
            numberOfOpenSites++;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return nByNGrid[row - 1][col - 1] != 0;
    }

    /**
     * A full site is an open site that can be connected to an open site
     * in the top row via a chain of neighboring (left, right, up, down) open sites.
     */
    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        return true;
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
        return true;
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(10);
        percolation.print2DArray(percolation.nByNGrid);

        int n = percolation.nByNGrid.length;

        double start = System.nanoTime();
        for (int i = 0; i < n * n; i++) {
            int row = StdRandom.uniform(1, n + 1);
            int col = StdRandom.uniform(1, n + 1);
            percolation.open(row, col);
        }
        System.out.println("Time for percolation is: ".concat(String.valueOf((System.nanoTime() - start)/1000_000)).concat(" ms"));
        percolation.print2DArray(percolation.nByNGrid);
        System.out.println("Number of open sites is: ".concat(String.valueOf(percolation.numberOfOpenSites())));
    }

    private int root(int[] id, int i)
    {
        while (i != id[i]) i = id[i];
        return i;
    }

    public void union(int p, int q) {
        int pID = nByNGrid[p - 1][q - 2];   // needed for correctness
        int qID = nByNGrid[p - 1][q - 1];   // to reduce the number of array accesses

        // p and q are already in the same component
        if (pID == qID) return;

        for (int i = 0; i < nByNGrid[p - 1].length; i++)
            if (nByNGrid[p - 1][i] == pID) nByNGrid[p - 1][i] = qID;
    }

    public boolean connected(int[] id, int p, int q)
    {
        return root(id, p) == root(id, q);
    }

    private void print2DArray(int[][] arr) {
        for (int[] ints : arr) {
            Arrays.stream(ints).forEach(result -> System.out.print(result + " "));
            System.out.println();
        }
    }

    private void validate(int p1, int p2) {
        int arrLength = nByNGrid.length;
        if ((p1 < 1 || p1 > arrLength) || (p2 < 1 || p2 > arrLength)) {
            throw new IllegalArgumentException();
        }
    }

    private boolean validateCornerCases(int p, int q) {
        return p >= 0 && q >= 0;
    }
}
