import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final boolean[][] opened;
    private int numberOfOpenSites;
    private final int gridSize;
    private final int top, bottom;
    private final int[] parent;
    private final int[] size;
    private final WeightedQuickUnionUF ufTop;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        gridSize = n;
        top = 0;
        bottom = n * n + 1;
        ufTop = new WeightedQuickUnionUF(gridSize * gridSize + 2);
        parent = new int[bottom];
        size = new int[bottom];
        for (int i = 0; i < bottom; i++) {
            parent[i] = i;
            size[i] = 1;
        }
        opened = new boolean[n][n];
    }

    public static void main(String[] args) {
        Percolation percolation = new Percolation(Integer.parseInt(args[0]));
        int row, col;
        while (!percolation.percolates()) {
            row = StdRandom.uniform(1, percolation.gridSize + 1);
            col = StdRandom.uniform(1, percolation.gridSize + 1);
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
                union(getArrayIndexFor(row, col), top);
            }
            if (row == gridSize) ufTop.union(getArrayIndexFor(row, col), bottom);
            if (col > 1 && isOpen(row, col - 1)) {
                ufTop.union(getArrayIndexFor(row, col - 1), getArrayIndexFor(row, col));
                union(getArrayIndexFor(row, col - 1), getArrayIndexFor(row, col));
            }
            if (col < gridSize && isOpen(row, col + 1)) {
                ufTop.union(getArrayIndexFor(row, col + 1), getArrayIndexFor(row, col));
                union(getArrayIndexFor(row, col + 1), getArrayIndexFor(row, col));
            }
            if (row > 1 && isOpen(row - 1, col)) {
                ufTop.union(getArrayIndexFor(row - 1, col), getArrayIndexFor(row, col));
                union(getArrayIndexFor(row - 1, col), getArrayIndexFor(row, col));
            }
            if (row < gridSize && isOpen(row + 1, col)) {
                ufTop.union(getArrayIndexFor(row + 1, col), getArrayIndexFor(row, col));
                union(getArrayIndexFor(row + 1, col), getArrayIndexFor(row, col));
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

    private int getArrayIndexFor(int row, int col) {
        if ((row < 1 || row > gridSize) || (col < 1 || col > gridSize)) throw new IllegalArgumentException();
        return (row - 1) * gridSize + col;
    }

    private void validate(int a) {
        int n = 1;
        if (a < n || a > gridSize) throw new IllegalArgumentException(String.format("Index %s must be between %s and %s", a, n, gridSize - 1));
    }
    
    private boolean connectedBottom(int p, int q) {
        return find(p) == find(q);
    }

    private int find(int p) {
        while (p != parent[p])
            p = parent[p];
        return p;
    }

    private void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;

        // make smaller root point to larger one
        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
        }
        else {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
    }
}
