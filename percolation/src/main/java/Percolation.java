import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final boolean[][] opened;
    private int numberOfOpenSites;
    private final int size;
    private final int top, bottom;
    private final WeightedQuickUnionUF ufTop;
    private final WeightedQuickUnionUF ufBottom;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        size = n;
        top = 0;
        bottom = n * n + 1;
        ufTop = new WeightedQuickUnionUF(size * size + 2);
        ufBottom = new WeightedQuickUnionUF(size * size + 1);
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
            if (row == 1) {
                ufTop.union(getArrayIndexFor(row, col), top);
                ufBottom.union(getArrayIndexFor(row, col), top);
            }
            if (row == size) ufTop.union(getArrayIndexFor(row, col), bottom);
            if (col > 1 && isOpen(row, col - 1)) {
                ufTop.union(getArrayIndexFor(row, col - 1), getArrayIndexFor(row, col));
                ufBottom.union(getArrayIndexFor(row, col - 1), getArrayIndexFor(row, col));
            }
            if (col < size && isOpen(row, col + 1)) {
                ufTop.union(getArrayIndexFor(row, col + 1), getArrayIndexFor(row, col));
                ufBottom.union(getArrayIndexFor(row, col + 1), getArrayIndexFor(row, col));
            }
            if (row > 1 && isOpen(row - 1, col)) {
                ufTop.union(getArrayIndexFor(row - 1, col), getArrayIndexFor(row, col));
                ufBottom.union(getArrayIndexFor(row - 1, col), getArrayIndexFor(row, col));
            }
            if (row < size && isOpen(row + 1, col)) {
                ufTop.union(getArrayIndexFor(row + 1, col), getArrayIndexFor(row, col));
                ufBottom.union(getArrayIndexFor(row + 1, col), getArrayIndexFor(row, col));
            }
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
        return connectedBottom(top, getArrayIndexFor(row, col));
    }

    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    /**
     * The system percolates if there is a full site in the bottom row.
     */
    public boolean percolates() {
        return connectedTop(top, bottom);
    }

    private boolean connectedTop(int p, int q) {
        return ufTop.find(p) == ufTop.find(q);
    }

    private boolean connectedBottom(int p, int q) {
        return ufBottom.find(p) == ufBottom.find(q);
    }

    private int getArrayIndexFor(int row, int col) {
        if ((row < 1 || row > size) || (col < 1 || col > size)) throw new IllegalArgumentException();
        return (row - 1) * size + col;
    }

    private void validate(int a) {
        int n = 1;
        if (a < n || a > size) throw new IllegalArgumentException(String.format("Index %s must be between %s and %s", a, n, size - 1));
    }
}
