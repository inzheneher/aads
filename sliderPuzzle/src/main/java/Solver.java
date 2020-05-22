import edu.princeton.cs.algs4.MinPQ;

public final class Solver {

    private final Board board;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        this.board = initial;
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
        Solver solver1 = new Solver(new Board(new int[][]{}));
        Solver solver2 = new Solver(new Board(new int[][]{}));
        System.out.println(solver1.board.manhattan());
        System.out.println(solver1.board.isGoal());
        System.out.println(solver1.board.equals(solver2.board));
        System.out.println(solver1.moves());
    }

}