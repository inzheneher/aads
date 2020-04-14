import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;
    private final int trials;
    private final double[] numberOfOpenSitesArray;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
        this.trials = trials;
        this.numberOfOpenSitesArray = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation pn = new Percolation(n);
            int row, col;
            while (!pn.percolates()) {
                row = StdRandom.uniform(1, n + 1);
                col = StdRandom.uniform(1, n + 1);
                pn.open(row, col);
            }
            numberOfOpenSitesArray[i] = (double) pn.numberOfOpenSites() / (n * n);
        }
    }

    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

        StdOut.println("mean                    = ".concat(String.valueOf(ps.mean())));
        StdOut.println("stddev                  = ".concat(String.valueOf(ps.stddev())));
        StdOut.println("95% confidence interval = [".concat(String.valueOf(ps.confidenceLo())).concat(", ").concat(String.valueOf(ps.confidenceHi())).concat("]"));
    }

    public double mean() {
        return StdStats.mean(numberOfOpenSitesArray);
    }

    public double stddev() {
        return StdStats.stddev(numberOfOpenSitesArray);
    }

    public double confidenceLo() {
        return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(trials);
    }

    public double confidenceHi() {
        return mean() + CONFIDENCE_95 * stddev() / Math.sqrt(trials);
    }
}