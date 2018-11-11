import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private SearchNode finalNode;

    private class SearchNode implements Comparable<SearchNode> {
        private final int moves;
        private final SearchNode previous;
        private final Board board;

        public SearchNode(int moves, SearchNode prev, Board current) {
            this.moves = moves;
            this.previous = prev;
            this.board = current;
        }

        @Override
        public int compareTo(SearchNode other) {
            int their = other.moves +
                    (other.board != null ? other.board.manhattan() : 0);
            int our = this.moves +
                    (this.board != null ? this.board.manhattan() : 0);

            return Integer.compare(our, their);
        }
    }

    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException();
        MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
        MinPQ<SearchNode> twinPq = new MinPQ<SearchNode>();
        pq.insert(new SearchNode(0, null, initial));
        twinPq.insert(new SearchNode(0, null, initial.twin()));
        while (!pq.isEmpty() && !twinPq.isEmpty()) {
            SearchNode current = pq.delMin();
            if (current.board.isGoal()) {
                this.finalNode = current;
                break;
            }
            for (Board b : current.board.neighbors()) {
                if (current.previous == null
                        || !current.previous.board.equals(b))
                    pq.insert(new SearchNode(current.moves + 1, current, b));
            }

            SearchNode currentTwin = twinPq.delMin();
            if (currentTwin.board.isGoal()) {
                this.finalNode = null;
                break;
            }
            for (Board b : currentTwin.board.neighbors()) {
                if (currentTwin.previous == null
                        || !currentTwin.previous.board.equals(b))
                    twinPq.insert(new SearchNode(currentTwin.moves + 1, currentTwin, b));
            }
        }
    }

    public boolean isSolvable() {
        return this.finalNode != null;
    }

    public int moves() {
        return this.finalNode != null ? this.finalNode.moves : -1;
    }

    public Iterable<Board> solution() {
        Stack<Board> q = new Stack<>();
        q.push(this.finalNode.board);
        for (SearchNode node = this.finalNode;
             node.previous != null; node = node.previous)
            q.push(node.previous.board);
        return q;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
