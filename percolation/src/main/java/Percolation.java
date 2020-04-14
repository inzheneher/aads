import edu.princeton.cs.algs4.StdRandom;

public class Percolation {

    private final int[] nByNGrid;
    private final int[] sizeArr;
    private final boolean[][] opened;
    private int numberOfOpenSites;
    private final int size;
    private final int top, bottom;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        size = n;
        top = 0;
        bottom = n * n + 1;
        opened = new boolean[n][n];
        nByNGrid = new int[n * n + 2];
        sizeArr = new int[n * n + 2];
        for (int i = 0; i < nByNGrid.length; i++) {
            nByNGrid[i] = i;
            sizeArr[i] = 1;
        }
    }

    public static void main(String[] args) {
        Percolation percolation = new Percolation(Integer.parseInt(args[0]));
        int row, col;
        while (!percolation.percolates()) {
            row = StdRandom.uniform(1, percolation.size + 1);
            col = StdRandom.uniform(1, percolation.size + 1);
            percolation.open(row, col);
        }
    }

    public void open(int row, int col) {
        validate(row);
        validate(col);
        if (!isOpen(row, col)) {
            opened[row - 1][col - 1] = true;
            numberOfOpenSites++;
            if (row == 1) union(top, getArrayIndexFor(row, col));
            if (row == size) union(bottom, getArrayIndexFor(row, col));
            if (col > 1 && isOpen(row, col - 1)) union(getArrayIndexFor(row, col - 1), getArrayIndexFor(row, col));
            if (col < size && isOpen(row, col + 1)) union(getArrayIndexFor(row, col + 1), getArrayIndexFor(row, col));
            if (row > 1 && isOpen(row - 1, col)) union(getArrayIndexFor(row - 1, col), getArrayIndexFor(row, col));
            if (row < size && isOpen(row + 1, col)) union(getArrayIndexFor(row + 1, col), getArrayIndexFor(row, col));
        }
    }

    public boolean isOpen(int row, int col) {
        validate(row);
        validate(col);
        return opened[row - 1][col - 1];
    }

    /**
     * A full site is an open site that can be connected to an open site
     * in the top row via a chain of neighboring (left, right, up, down) open sites.
     */
    public boolean isFull(int row, int col) {
        validate(row);
        validate(col);
        return connected(top, getArrayIndexFor(row, col));
    }

    private boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    /**
     * The system percolates if there is a full site in the bottom row.
     */
    public boolean percolates() {
        return connected(top, bottom);
    }

    private int getArrayIndexFor(int row, int col) {
        if ((row < 1 || row > size) || (col < 1 || col > size)) throw new IllegalArgumentException();
        return (row - 1) * size + col;
    }

    private int find(int p) {
        int n = nByNGrid.length;
        if (p < 0 || p > n - 1) {
            throw new IllegalArgumentException("Index "
                    .concat(String.valueOf(p))
                    .concat(" must be between 0 and ")
                    .concat(String.valueOf(n - 1)));
        }
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
        if (a < 1 || a > size)
            throw new IllegalArgumentException("Index ".concat(String.valueOf(a)).concat(" must be between 0 and ").concat(String.valueOf(size - 1)));
    }
}
