import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    public static final double CONFIDENCE_95 = 1.96;
    private final int size;
    private final int trials;
    private final double[] numberOfOpenSitesArray;
    private Percolation pn;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
        this.size = n;
        this.trials = trials;
        this.numberOfOpenSitesArray = new double[trials];
    }

    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        for (int i = 0; i < ps.trials; i++) {
            ps.pn = new Percolation(ps.size);
            int row, col;
            while (!ps.pn.percolates()) {
                row = StdRandom.uniform(ps.size);
                col = StdRandom.uniform(ps.size);
                ps.pn.open(row, col);
            }
            ps.numberOfOpenSitesArray[i] = (double) ps.pn.numberOfOpenSites() / (ps.size * ps.size);
        }
        System.out.println("mean = ".concat(String.valueOf(ps.mean())));
        System.out.println("stddev = ".concat(String.valueOf(ps.stddev())));
        System.out.println("95% confidence interval = [".concat(String.valueOf(ps.confidenceLo())).concat(", ").concat(String.valueOf(ps.confidenceHi())).concat("]"));
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