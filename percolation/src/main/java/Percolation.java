import edu.princeton.cs.algs4.StdRandom;

public class Percolation {

    private final int[] nByNGrid;
    private final int[] sizeArr;
    private final boolean[][] opened;
    private int numberOfOpenSites;
    private final int size;
    private final int top;
    private final int bottom;

    private int counter = 0;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
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

    public static void main(String[] args) {
        Percolation pn = new Percolation(Integer.parseInt(args[0]));
        int row, col;
        while (!pn.percolates()) {
            row = StdRandom.uniform(pn.size);
            col = StdRandom.uniform(pn.size);
            pn.open(row, col);
        }
        System.out.println("Percolation threshold is: ".concat(String.valueOf((double) pn.numberOfOpenSites() / (pn.size * pn.size))));
        System.out.println("Amount of attempts: ".concat(String.valueOf(pn.counter)));
    }

    public void open(int row, int col) {
        validate(row, col);
        if (!isOpen(row, col)) {
            opened[row][col] = true;
            numberOfOpenSites++;
            if (col > 0 && isOpen(row, col - 1)) union(getArrayIndexFor(row, col - 1), getArrayIndexFor(row, col));
            if (col < size - 1 && isOpen(row, col + 1))
                union(getArrayIndexFor(row, col + 1), getArrayIndexFor(row, col));
            if (row < size - 1 && isOpen(row + 1, col))
                union(getArrayIndexFor(row + 1, col), getArrayIndexFor(row, col));
            if (row > 0 && isOpen(row - 1, col)) union(getArrayIndexFor(row - 1, col), getArrayIndexFor(row, col));
        }
    }

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

    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    /**
     * The system percolates if there is a full site in the bottom row.
     */
    public boolean percolates() {
        counter++;
        return nByNGrid[bottom] == nByNGrid[top];
    }

    private int getArrayIndexFor(int row, int col) {
        if ((row < 0 || row >= size) || (col < 0 || col >= size)) throw new IllegalArgumentException();
        return row * size + col + 1;
    }

    private int find(int p) {
        validate(p);
        while (p != nByNGrid[p]) p = nByNGrid[p];
        return p;
    }

    private void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;

        if (sizeArr[rootP] < sizeArr[rootQ]) {
            nByNGrid[rootP] = rootQ;
            sizeArr[rootQ] += sizeArr[rootP];
        } else {
            nByNGrid[rootQ] = rootP;
            sizeArr[rootP] += sizeArr[rootQ];
        }
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
}
