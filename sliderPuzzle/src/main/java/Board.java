import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdRandom;

public final class Board {

    private final int[][] tiles;
    private final int dimension;
    private Board twin;

    /**
     * Create a board from an n-by-n array of tiles,
     * where tiles[row][col] = tile at (row, col)
     */
    public Board(int[][] tiles) {
        this.tiles = copyArray(tiles);
        dimension = this.tiles.length;
    }

    /**
     * Unit testing (not graded)
     */
    public static void main(String[] args) {
        Board board4Neighbors = new Board(new int[][]{{5, 0, 4}, {2, 3, 8}, {7, 1, 6}});
        Board otherBoard = new Board(new int[][]{{5, 0, 4}, {2, 3, 8}, {7, 1, 6}});
        System.out.printf("Is current board equals to other board: %s\n", board4Neighbors.equals(otherBoard));
        System.out.printf("Is this board goal: %s\n", board4Neighbors.isGoal());
        System.out.printf("Is current board equals to other board: %s\n", board4Neighbors.equals(otherBoard));
        Board twinBoard = board4Neighbors.twin();
        Board anotherTwinBoard = board4Neighbors.twin();
        System.out.println(board4Neighbors.toString());
        System.out.println(twinBoard.toString());
        System.out.println(anotherTwinBoard.toString());
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
        return dimension;
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
        int n = dimension();
        int k = 1;
        int[][] goalArray = new int[n][n];
        outbreak:
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                goalArray[i][j] = k;
                k++;
                if (k == n * n) break outbreak;
            }
        }
        return this.equals(new Board(goalArray));
    }

    /**
     * Does this board equal y?
     */
    public boolean equals(Object y) {
        if (y != null && this.getClass() == y.getClass()) {
            int[][] aTiles = ((Board) y).tiles;
            if (this.tiles == aTiles) return true;
            if (this.tiles == null || aTiles == null) return false;
            int length = dimension();
            if (aTiles.length != length) return false;
            for (int i = 0; i < length; i++) {
                for (int j = 0; j < this.tiles[i].length; j++) {
                    if (!(this.tiles[i][j] == aTiles[i][j])) return false;
                }
            }
            return true;
        } else return false;
    }

    /**
     * All neighboring boards
     */
    public Iterable<Board> neighbors() {
        Stack<Board> boards = new Stack<>();
        int x = 0;
        int y = 0;
        outerBreakPoint:
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
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
            } else if (y == dimension - 1) {
                neighbor = copyArray(tiles);
                this.swap(neighbor, y, x, y, x + 1);
                boards.push(new Board(neighbor));
                neighbor = copyArray(tiles);
                this.swap(neighbor, y, x, y - 1, x);
                boards.push(new Board(neighbor));
            } else if (y < dimension - 1) {
                swapArrayOne(boards, x, y);
                neighbor = copyArray(tiles);
                this.swap(neighbor, y, x, y - 1, x);
                boards.push(new Board(neighbor));
            }
        } else if (x == dimension - 1) {
            if (y == 0) {
                swapArrayTwo(boards, x, y);
            } else if (y == dimension - 1) {
                swapArrayThree(boards, x, y);
            } else if (y < dimension - 1) {
                swapArrayTwo(boards, x, y);
                neighbor = copyArray(tiles);
                this.swap(neighbor, y, x, y - 1, x);
                boards.push(new Board(neighbor));
            }
        } else if (x < dimension - 1) {
            if (y == 0) {
                neighbor = copyArray(tiles);
                this.swap(neighbor, y, x, y, x + 1);
                boards.push(new Board(neighbor));
                swapArrayTwo(boards, x, y);
            } else if (y == dimension - 1) {
                neighbor = copyArray(tiles);
                this.swap(neighbor, y, x, y, x + 1);
                boards.push(new Board(neighbor));
                swapArrayThree(boards, x, y);
            } else if (y < dimension - 1) {
                neighbor = copyArray(tiles);
                this.swap(neighbor, y, x, y, x + 1);
                boards.push(new Board(neighbor));
                swapArrayTwo(boards, x, y);
                neighbor = copyArray(tiles);
                this.swap(neighbor, y, x, y - 1, x);
                boards.push(new Board(neighbor));
            }
        }
        return boards;
    }

    /**
     * A board that is obtained by exchanging any pair of tiles
     */
    public Board twin() {
        int n = dimension();
        int[][] goalArray = copyArray(this.tiles);
        int initX = 0;
        int initY = 0;
        int goalX = 0;
        int goalY = 0;
        while (initX == goalX && initY == goalY || (goalArray[initY][initX] == 0 || goalArray[goalY][goalX] == 0)) {
            initX = StdRandom.uniform(n);
            initY = StdRandom.uniform(n);
            goalX = StdRandom.uniform(n);
            goalY = StdRandom.uniform(n);
        }
        swap(goalArray, initY, initX, goalY, goalX);
        if (twin == null) twin = new Board(goalArray);
        return twin;
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

    private void swapArrayOne(Stack<Board> boards, int x, int y) {
        int[][] neighbor;
        neighbor = copyArray(tiles);
        this.swap(neighbor, y, x, y, x + 1);
        boards.push(new Board(neighbor));
        neighbor = copyArray(tiles);
        this.swap(neighbor, y, x, y + 1, x);
        boards.push(new Board(neighbor));
    }

    private void swapArrayTwo(Stack<Board> boards, int x, int y) {
        int[][] neighbor;
        neighbor = copyArray(tiles);
        this.swap(neighbor, y, x, y, x - 1);
        boards.push(new Board(neighbor));
        neighbor = copyArray(tiles);
        this.swap(neighbor, y, x, y + 1, x);
        boards.push(new Board(neighbor));
    }

    private void swapArrayThree(Stack<Board> boards, int x, int y) {
        int[][] neighbor;
        neighbor = copyArray(tiles);
        this.swap(neighbor, y, x, y, x - 1);
        boards.push(new Board(neighbor));
        neighbor = copyArray(tiles);
        this.swap(neighbor, y, x, y - 1, x);
        boards.push(new Board(neighbor));
    }
}