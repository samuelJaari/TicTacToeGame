



import java.util.ArrayList;
import java.util.List;

public class Minimax {
    private static final int X = 1;
    private static final int O = 2;
    private static final int DRAW = 0;

    private static final int[][] winningCombinations = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // columns
            {0, 4, 8}, {2, 4, 6}  // diagonals
    };

    private int[][] board;

    public Minimax() {
        board = new int[3][3];
    }

    public void makeMove(int player, int row, int col) {
        board[row][col] = player;
    }

    public int minimax(int player, int depth) {
        // Check if the game is over or if the depth limit has been reached
        if (gameOver() || depth == 0) {
            return getScore();
        }

        // Initialize the best value to the lowest possible score
        int bestValue = player == X ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        // Generate the children of the current node (i.e., all the possible moves)
        List<Minimax> children = generateChildren();

        // Recursively explore the children
        for (Minimax child : children) {
            // Recursively compute the value of the child
            int value = child.minimax(-player, depth - 1);

            // Update the best value based on the player's perspective
            if (player == X) {
                bestValue = Math.max(bestValue, value);
            } else {
                bestValue = Math.min(bestValue, value);
            }
        }

        return bestValue;
    }

    private List<Minimax> generateChildren() {
        // TODO: Generate the children of the current node (i.e., all the possible moves)
        List<Minimax> children = new ArrayList<>();

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == 0) {
                    // This position is empty, so we can generate a child for it
                    Minimax child = new Minimax();
                    child.board = copyBoard();
                    child.makeMove(getNextPlayer(), row, col);
                    children.add(child);
                }
            }
        }

        return children;
    }

    private int[][] copyBoard() {
        int[][] copy = new int[3][3];
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                copy[row][col] = board[row][col];
            }
        }
        return copy;
    }
    private int getNextPlayer() {
        int xCount = 0;
        int oCount = 0;

        // Count the number of X's and O's on the board
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == X) {
                    xCount++;
                } else if (board[row][col] == O) {
                    oCount++;
                }
            }
        }

        // Return the next player based on the count of X's and O's
        if (xCount == oCount) {
            // It's X's turn
            return X;
        } else {
            // It's O's turn
            return O;
        }
    }
    private boolean gameOver() {
        // TODO: Check if the game is over (i.e., someone has won or the board is full)
        return false;
    }

    private int getScore() {
        // TODO: Determine the score of the current board state (i.e., X wins, O wins, or draw)
        return 0;
    }
}