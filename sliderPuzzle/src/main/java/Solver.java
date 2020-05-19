import edu.princeton.cs.algs4.MinPQ;

public class Solver {

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {

    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return false;
    }

    // min number of moves to solve initial board
    public int moves() {
        return 0;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        return new MinPQ<>();
    }

    // test client (see below) 
    public static void main(String[] args) {
        Board board = new Board(new int[][]{});
        Solver solver = new Solver(board);
        System.out.println(board.manhattan());
    }

}