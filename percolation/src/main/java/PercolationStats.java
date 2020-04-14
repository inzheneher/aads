import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;
    private final int trials;
    private final double[] numberOfOpenSitesArray;
    private double mean, stddev;
    private boolean setMean;
    private boolean setStddev;

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
        new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1])).printResults();
    }

    public double mean() {
        mean = StdStats.mean(numberOfOpenSitesArray);
        setMean = true;
        return mean;
    }

    public double stddev() {
        stddev = StdStats.stddev(numberOfOpenSitesArray);
        setStddev = true;
        return stddev;
    }

    public double confidenceLo() {
        if (setMean && setStddev) {
            return mean - CONFIDENCE_95 * stddev / Math.sqrt(trials);
        } else if (setMean && !setStddev) {
            return mean - CONFIDENCE_95 * stddev() / Math.sqrt(trials);
        } else if (!setMean && setStddev) {
            return mean() - CONFIDENCE_95 * stddev / Math.sqrt(trials);
        } else {
            return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(trials);
        }
    }

    public double confidenceHi() {
        if (setMean && setStddev) {
            return mean + CONFIDENCE_95 * stddev / Math.sqrt(trials);
        } else if (setMean && !setStddev) {
            return mean + CONFIDENCE_95 * stddev() / Math.sqrt(trials);
        } else if (!setMean && setStddev) {
            return mean() + CONFIDENCE_95 * stddev / Math.sqrt(trials);
        } else {
            return mean() + CONFIDENCE_95 * stddev() / Math.sqrt(trials);
        }
    }

    private void printResults() {
        StdOut.printf("mean\t\t\t\t\t= %s\nstddev\t\t\t\t\t= %s\n95%% confidence interval\t= [%s, %s]", mean(), stddev(), confidenceLo(), confidenceHi());
    }
}