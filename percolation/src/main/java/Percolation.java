import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final boolean[][] opened;
    private int numberOfOpenSites;
    private final int size;
    private final int top, bottom;
    private final WeightedQuickUnionUF uf;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        size = n;
        top = 0;
        bottom = n * n + 1;
        uf = new WeightedQuickUnionUF(size * size + 2);
        opened = new boolean[n][n];
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
            if (row == 1) uf.union(top, getArrayIndexFor(row, col));
            if (row == size) uf.union(bottom, getArrayIndexFor(row, col));
            if (col > 1 && isOpen(row, col - 1)) uf.union(getArrayIndexFor(row, col - 1), getArrayIndexFor(row, col));
            if (col < size && isOpen(row, col + 1)) uf.union(getArrayIndexFor(row, col + 1), getArrayIndexFor(row, col));
            if (row > 1 && isOpen(row - 1, col)) uf.union(getArrayIndexFor(row - 1, col), getArrayIndexFor(row, col));
            if (row < size && isOpen(row + 1, col)) uf.union(getArrayIndexFor(row + 1, col), getArrayIndexFor(row, col));
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
        return uf.connected(top, getArrayIndexFor(row, col));
    }

    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    /**
     * The system percolates if there is a full site in the bottom row.
     */
    public boolean percolates() {
        return uf.connected(top, bottom);
    }

    private int getArrayIndexFor(int row, int col) {
        if ((row < 1 || row > size) || (col < 1 || col > size)) throw new IllegalArgumentException();
        return (row - 1) * size + col;
    }

    private void validate(int a) {
        if (a < 1 || a > size)
            throw new IllegalArgumentException("Index ".concat(String.valueOf(a)).concat(" must be between 0 and ").concat(String.valueOf(size - 1)));
    }
}
