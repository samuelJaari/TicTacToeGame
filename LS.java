//package tictactoe;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
public class LS extends javax.swing.JFrame {

	// Marks on the board
	public static final int EMPTY = 0;
	public static final int HUMAN = 1;
	public static final int COMPUTER = 2;

	// Outcomes of the game
	public static final int HUMAN_WIN = 4;
	public static final int DRAW = 5;
	public static final int CONTINUE = 6;
	public static final int COMPUTER_WIN = 7;


	public static final int SIZE = 3;
	public int[][] board = new int[SIZE][SIZE];  // The marks on the board
	private javax.swing.JButton[][] jB;           // The buttons of the board
	private int turn = HUMAN;                    // HUMAN starts the game

	private GameMove computersGameMove;
	private List<GameMove> availableGameMoves;

	/* Constructor for the Tic Tac Toe game */
	public LS() {
		// Close the window when the user exits
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		initBoard();      // Set up the board with all marks empty
	}

	// Initalize an empty board. 
	private void initBoard() {
		// Create a SIZE*SIZE gridlayput to hold the buttons	
		java.awt.GridLayout layout = new GridLayout(SIZE, SIZE);
		getContentPane().setLayout(layout);

		// The board is a grid of buttons
		jB = new Button[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				// Create a new button and add an actionListerner to it
				jB[i][j] = new Button(i, j);
				// Add an action listener to the button to handle mouse clicks
				jB[i][j].addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent act) {
						jBAction(act);
					}
				});
				add(jB[i][j]);   // Add the buttons to the GridLayout

				board[i][j] = EMPTY;     // Initialize all marks on the board to empty
			}
		}
		// Pack the GridLayout and make it visible
		pack();
	}

	// Action listener which handles mouse clicks on the buttons
	private void jBAction(java.awt.event.ActionEvent act) {
		Button thisButton = (Button) act.getSource();   // Get the button clicked on
		// Get the grid coordinates of the clicked button
		int i = thisButton.getRow();
		int j = thisButton.getCol();
		if (checkResult() != CONTINUE) {
			return;
		}
		if (board[i][j] == EMPTY) {

			System.out.println("Button[" + i + "][" + j + "] was clicked by " + turn);  // DEBUG

			// Check if this square is empty.
			// If it is empty then place a HUMAN mark (X) in it and check if it was a winning move
			board[i][j] = turn;
			// In this version no checks are done, the marks just alter between HUMAN and COMPUTER
			GameMove gameMove = new GameMove(i, j);
			makeGameMove(gameMove, 2);
			thisButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("X.png")));

			if (checkResult() != CONTINUE) {
				return;
			}

			minimax(0, 1);
			i =computersGameMove.getX();
			j =computersGameMove.getY();
			board[i][j] = 2;
			makeGameMove(computersGameMove, 1);
			jB[computersGameMove.getX()][computersGameMove.getY()].setIcon(new javax.swing.ImageIcon(getClass().getResource("O.png")));

			if (checkResult() != CONTINUE) {
				return;
			}
		}
	}

	private int checkResult() {
		// This function should check if one player (HUMAN or COMPUTER) wins, if the board is full (DRAW)
		// or if the game should continue. You implement this.

		// Check the columns
		if (has1Won()) {
			System.out.println("Compute Wins!!!");
			return HUMAN_WIN;
		}
		if (has2Won()) {
			System.out.println("Human Wins!!!");
			return COMPUTER_WIN;
		}

		for (int col = 0; col < 3; col++) {
			for (int row = 0; row < 3; row++) {
			if (board[row][col] == EMPTY) {
				System.out.println("Continue...");
				return CONTINUE;
			}
		}
		}

		System.out.println("Draw");
		return DRAW;
	}

	private boolean has2Won() {

		if ((board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == COMPUTER)
				|| (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == COMPUTER)) {
			// System.out.println("O Diagonal Win");
			return true;
		}
		for (int i = 0; i < 3; ++i) {
			if ((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == COMPUTER)
					|| (board[0][i] == board[1][i] && board[0][i] == board[2][i]
					&& board[0][i] == COMPUTER)) {
				// System.out.println("O Row or Column win");
				return true;
			}
		}

		return false;
	}

	private boolean has1Won() {

		if ((board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == HUMAN)
				|| (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == HUMAN)) {
			// System.out.println("X Diagonal Win");
			return true;
		}
		for (int i = 0; i < 3; ++i) {
			if (((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == HUMAN)
					|| (board[0][i] == board[1][i] && board[0][i] == board[2][i]
					&& board[0][i] == HUMAN))) {
				// System.out.println("X Row or Column win");
				return true;
			}
		}
		return false;
	}


	// Place a mark for one of the playsers (HUMAN or COMPUTER) in the specified position
	public void place(int row, int col, int player) {
		board[row][col] = player;
	}

	public List getAvailableGameMoves() {

		availableGameMoves = new ArrayList<GameMove>();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j] == 0)
					availableGameMoves.add(new GameMove(i, j));
			}
		}
		return availableGameMoves;
	}

	// the minimax algorithm
	// the minimax algorithm
	// the minimax algorithm
	public int minimax(int depth, int turn) {

		if (has1Won())
			return +1;
		if (has2Won())
			return -1;

		List<GameMove> gameMovesAvailable = getAvailableGameMoves();
		if (gameMovesAvailable.isEmpty())
			return 0;

		int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;

		for (int i = 0; i < gameMovesAvailable.size(); ++i) {

			GameMove gameMove = gameMovesAvailable.get(i);

			if (turn == 1) {
				makeGameMove(gameMove, 1);
				int currentScore = minimax(depth + 1, 2);
				max = Math.max(currentScore, max);

				if (depth == 0)
					System.out.println("info: score for position " + (i+1) + ": " + currentScore);
				if (currentScore >= 0) {
					if (depth == 0)
						computersGameMove = gameMove;
				} else if (currentScore == 1) {
					resetGameMove(gameMove);
					break;
				}
				if (i == availableGameMoves.size() - 1 && max > 0) {
					if (depth == 0)
						computersGameMove = gameMove;
				}
			} else if (turn == 2) {
				makeGameMove(gameMove, 2);
				int currentScore = minimax(depth + 1, 1);
				min = Math.min(currentScore, min);
				if (min == -1) {
					resetGameMove(gameMove);
					break;
				}
			}
			resetGameMove(gameMove);
		}
		return (turn == 1) ? max : min;
	}


	public void resetGameMove(GameMove move) {
		board[move.getX()][move.getY()] = 0;
	}

	public void makeGameMove(GameMove move, int player) {

		board[move.getX()][move.getY()] = player;
	}


	
	
    
    public static void main (String [] args){

	String threadName = Thread.currentThread().getName();
	LS lsGUI = new LS();      // Create a new user inteface for the game
	lsGUI.setVisible(true);
	
	java.awt.EventQueue.invokeLater (new Runnable() {
		public void run() {
		    while ( (Thread.currentThread().getName() == threadName) &&
		    	    (lsGUI.checkResult() == CONTINUE) ){
			try {
			    Thread.sleep(100);  // Sleep for 100 millisecond, wait for button press
			} catch (InterruptedException e) { };
		    }
		}
	    });
    }
}
