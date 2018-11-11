import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    private final int[] blocks;
    private final int n;
    private final int manhattanDistance;
    private final int hammingDistance;

    public Board(int[][] blocks) {
        n = blocks.length;
        this.blocks = new int[this.n * this.n];

        for (int i = 0; i < this.n; i++)
            for (int j = 0; j < this.n; j++)
                this.blocks[i * this.n + j] = blocks[i][j];

        int square = this.n * this.n;
        int manhattan = 0;
        int hamming = 0;
        for (int i = 0; i < square; i++) {
            int expectedValue = (i + 1) % square;
            if (this.blocks[i] != expectedValue && this.blocks[i] != 0) {
                int validRow = (this.blocks[i] - 1) / this.n;
                int validCol = (this.blocks[i] - 1) % this.n;
                int row = i / this.n;
                int col = i % this.n;
                hamming++;
                manhattan += Math.abs(validRow - row) + Math.abs(validCol - col);
            }
        }
        this.manhattanDistance = manhattan;
        this.hammingDistance = hamming;
    }

    public int dimension() {
        return this.n;
    }

    public int hamming() {
        return this.hammingDistance;
    }

    public int manhattan() {
        return this.manhattanDistance;
    }

    public boolean isGoal() {
        int square = this.n * this.n;
        for (int i = 0; i < square; i++)
            if (this.blocks[i] != (i + 1) % square)
                return false;
        return true;
    }

    public Board twin() {
        int square = this.n * this.n;

        int first = -1, second = -1;
        int i = 0;
        while (first == -1 || second == -1) {
            if (this.blocks[i] == 0)
                continue;
            if (first == -1) first = i;
            else if (i != first) second = i;
            i++;
        }

        return copyWithSwap(first / this.n, first % this.n, second / this.n,
                            second % this.n);
    }

    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null || getClass() != y.getClass())
            return false;
        Board that = (Board) y;
        if (that.n != this.n)
            return false;
        for (int i = 0; i < this.n * this.n; i++)
            if (this.blocks[i] != that.blocks[i])
                return false;
        return true;
    }

    public Iterable<Board> neighbors() {
        Stack<Board> stack = new Stack<Board>();
        int square = this.n * this.n;
        int blankIndex = -1;
        for (int i = 0; i < square; i++) {
            if (this.blocks[i] == 0) {
                blankIndex = i;
                break;
            }
        }
        int blankCol = blankIndex % this.n;
        int blankRow = blankIndex / this.n;
        if (blankCol != 0)
            stack.push(copyWithSwap(blankRow, blankCol, blankRow, blankCol - 1));
        if (blankRow != 0)
            stack.push(copyWithSwap(blankRow, blankCol, blankRow - 1, blankCol));
        if (blankCol != this.n - 1)
            stack.push(copyWithSwap(blankRow, blankCol, blankRow, blankCol + 1));
        if (blankRow != this.n - 1)
            stack.push(copyWithSwap(blankRow, blankCol, blankRow + 1, blankCol));
        return stack;
    }

    private Board copyWithSwap(int firstRow, int firstCol, int secondRow, int secondCol) {
        int[][] tiles = new int[n][n];
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                tiles[i][j] = this.blocks[i * this.n + j];
            }
        }

        int tmp = tiles[firstRow][firstCol];
        tiles[firstRow][firstCol] = tiles[secondRow][secondCol];
        tiles[secondRow][secondCol] = tmp;
        return new Board(tiles);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.n);
        sb.append('\n');
        for (int i = 0; i < this.n * this.n; i++) {
            sb.append(this.blocks[i]);
            if ((i + 1) % this.n == 0)
                sb.append("\n");
            else
                sb.append(" ");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        int n = 3;
        int[] data = new int[] { 1, 2, 3, 0, 7, 6, 5, 4, 8 };
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = data[i * n + j];
        Board initial = new Board(blocks);
        StdOut.print(initial.toString());
        StdOut.print(initial.twin().toString());
    }
}
