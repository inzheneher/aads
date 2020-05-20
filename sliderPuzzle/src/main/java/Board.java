import java.util.ArrayList;
import java.util.Arrays;

public class Board {

    private final int[][] tiles;
    private final int N;

    /**
     * Create a board from an n-by-n array of tiles,
     * where tiles[row][col] = tile at (row, col)
     */
    public Board(int[][] tiles) {
        this.tiles = Arrays.copyOf(tiles, tiles.length);
        N = this.tiles.length;
    }

    /**
     * Unit testing (not graded)
     */
    public static void main(String[] args) {
        Board board4Neighbors = new Board(new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}});
        System.out.println(board4Neighbors.toString());
        for (Board neighbor : board4Neighbors.neighbors()) {
            System.out.println(neighbor.toString());
        }
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
        return dimension() + "\n" + tableArray;
    }

    /**
     * Board dimension n
     */
    public int dimension() {
        return N;
    }

    /**
     * Number of tiles out of place
     */
    public int hamming() {
        int k = 1;
        int n = dimension();
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
        int n = dimension();
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
            int length = dimension();
            if (aTiles.length != length) return false;
            for (int i = 0; i < length; i++) {
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
        ArrayList<Board> boards = new ArrayList<>();
        int x, y;
        x = y = 0;
        outerBreakPoint:
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] == 0) {
                    x = j;
                    y = i;
                    break outerBreakPoint;
                }
            }
        }
        int[][] neighbor;
        if (x == 0) {
            if (y == 0) {
                swapArrayOne(boards, x, y);
            } else if (y == N - 1) {
                neighbor = copyArray(tiles);
                this.swap(neighbor, y, x, y, x + 1);
                boards.add(new Board(neighbor));
                neighbor = copyArray(tiles);
                this.swap(neighbor, y, x, y - 1, x);
                boards.add(new Board(neighbor));
            } else if (y < N - 1) {
                swapArrayOne(boards, x, y);
                neighbor = copyArray(tiles);
                this.swap(neighbor, y, x, y - 1, x);
                boards.add(new Board(neighbor));
            }
        } else if (x == N - 1) {
            if (y == 0) {
                swapArrayTwo(boards, x, y);
            } else if (y == N - 1) {
                swapArrayThree(boards, x, y);
            } else if (y < N - 1) {
                swapArrayTwo(boards, x, y);
                neighbor = copyArray(tiles);
                this.swap(neighbor, y, x, y - 1, x);
                boards.add(new Board(neighbor));
            }
        } else if (x < N - 1) {
            if (y == 0) {
                neighbor = copyArray(tiles);
                this.swap(neighbor, y, x, y, x + 1);
                boards.add(new Board(neighbor));
                swapArrayTwo(boards, x, y);
            } else if (y == N - 1) {
                neighbor = copyArray(tiles);
                this.swap(neighbor, y, x, y, x + 1);
                boards.add(new Board(neighbor));
                swapArrayThree(boards, x, y);
            } else if (y < N - 1) {
                neighbor = copyArray(tiles);
                this.swap(neighbor, y, x, y, x + 1);
                boards.add(new Board(neighbor));
                swapArrayTwo(boards, x, y);
                neighbor = copyArray(tiles);
                this.swap(neighbor, y, x, y - 1, x);
                boards.add(new Board(neighbor));
            }
        }
        return boards;
    }

    /**
     * A board that is obtained by exchanging any pair of tiles
     */
    public Board twin() {
        return new Board(new int[][]{});
    }

    private void swap(int[][] array, int initY, int initX, int goalY, int goalX) {
        int x = array[initY][initX];
        array[initY][initX] = array[goalY][goalX];
        array[goalY][goalX] = x;
    }

    private int[][] copyArray(int[][] initialArray) {
        int n = initialArray.length;
        int[][] goalArray = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(initialArray[i], 0, goalArray[i], 0, n);
        }
        return goalArray;
    }

    private void swapArrayOne(ArrayList<Board> boards, int x, int y) {
        int[][] neighbor;
        neighbor = copyArray(tiles);
        this.swap(neighbor, y, x, y, x + 1);
        boards.add(new Board(neighbor));
        neighbor = copyArray(tiles);
        this.swap(neighbor, y, x, y + 1, x);
        boards.add(new Board(neighbor));
    }

    private void swapArrayTwo(ArrayList<Board> boards, int x, int y) {
        int[][] neighbor;
        neighbor = copyArray(tiles);
        this.swap(neighbor, y, x, y, x - 1);
        boards.add(new Board(neighbor));
        neighbor = copyArray(tiles);
        this.swap(neighbor, y, x, y + 1, x);
        boards.add(new Board(neighbor));
    }

    private void swapArrayThree(ArrayList<Board> boards, int x, int y) {
        int[][] neighbor;
        neighbor = copyArray(tiles);
        this.swap(neighbor, y, x, y, x - 1);
        boards.add(new Board(neighbor));
        neighbor = copyArray(tiles);
        this.swap(neighbor, y, x, y - 1, x);
        boards.add(new Board(neighbor));
    }
}