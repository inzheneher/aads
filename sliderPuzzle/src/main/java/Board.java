import edu.princeton.cs.algs4.MinPQ;

import java.util.Arrays;

public class Board {

    private final int[][] tiles;
    private final int n;

    /**
     * Create a board from an n-by-n array of tiles,
     * where tiles[row][col] = tile at (row, col)
     */
    public Board(int[][] tiles) {
        this.tiles = Arrays.copyOf(tiles, tiles.length);
        n = this.tiles.length;
    }

    /**
     * Unit testing (not graded)
     */
    public static void main(String[] args) {
        Board board1 = new Board(new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}});
        System.out.println(board1.manhattan());
        Board board2 = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}});
        System.out.println(board1.toString());
        System.out.println(board1.equals(board2));
        System.out.printf("Dimension: %s\n", board1.dimension());
        System.out.printf("Hamming distance is: %s\n", board1.hamming());
    }

    /**
     * String representation of this board
     */
    public String toString() {
        StringBuilder tableArray = new StringBuilder();
        for (int[] tile : tiles) {
            for (int i : tile) {
                tableArray.append(i).append(" ");
            }
            tableArray.append("\n");
        }
        return tiles.length + "\n" + tableArray;
    }

    /**
     * Board dimension n
     */
    public int dimension() {
        return n;
    }

    /**
     * Number of tiles out of place
     */
    public int hamming() {
        int k = 1;
        int distance = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] != k && this.tiles[i][j] != 0) distance++;
                k++;
            }
        }
        return distance;
    }

    /**
     * Sum of Manhattan distances between tiles and goal
     */
    public int manhattan() {
        int dX = 0;
        int dY = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) continue;
                dX += Math.abs(tiles[i][j] - n * (Math.ceil(tiles[i][j] / (double) n) - 1) - (j + 1));
                dY += (int) Math.abs(Math.ceil(tiles[i][j] / (double) n) - (i + 1));
            }
        }
        return dX + dY;
    }

    /**
     * Is this board the goal board?
     */
    public boolean isGoal() {
        return false;
    }

    /**
     * Does this board equal y?
     */
    public boolean equals(Object y) {
        if (y != null && this.getClass() == y.getClass()) {
            int[][] aTiles = ((Board) y).tiles;
            if (tiles == aTiles) return true;
            if (tiles == null || aTiles == null) return false;
            int length = tiles.length;
            if (aTiles.length != length) return false;
            for (int i = 0; i < tiles.length; i++) {
                for (int j = 0; j < tiles[i].length; j++) {
                    if (!(tiles[i][j] == aTiles[i][j])) return false;
                }
            }
            return true;
        } else return false;
    }

    /**
     * All neighboring boards
     */
    public Iterable<Board> neighbors() {
        return new MinPQ<>();
    }

    /**
     * A board that is obtained by exchanging any pair of tiles
     */
    public Board twin() {
        return new Board(new int[][]{});
    }
}