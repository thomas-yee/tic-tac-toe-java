/**
 * A java data tye file that has data fields. This
 * is the board that the Tic-Tac-Toe game uses.
 * The overall purpose of this class is to show
 * the board, add marks to the board, and check
 * to see who has won or if there was a tie.
 * @author Thomas Yee & Shenghan Zhang
 * @version 1.0
 * @since February 12, 2020
 */
public class Board implements Constants {
///////////////////////////////////////////////////////////////////////
////Instance Variables
	/**
	 * Creates a 2D array of characters which
	 * represent the board.
	 */
	private char theBoard[][];

	/**
	 * Determines the count of marks
	 * on the board. An int data type.
	 */
	private int markCount;

//////////////////////////////////////////////////////////////////
////Constructors

	/**
	 * Constructs a Board object with theBoard
	 * array. The array is 3 x 3 with space variables
	 * from the class called Constants filling each spot.
	 * Initializes the mark count as 0.
	 */
	public Board() {
		markCount = 0;
		theBoard = new char[3][];
		for (int i = 0; i < 3; i++) {
			theBoard[i] = new char[3];
			for (int j = 0; j < 3; j++)
				theBoard[i][j] = SPACE_CHAR;
		}
	}

///////////////////////////////////////////////////////////////////
////Instance Methods

	/**
	 * Determines if the board is full by comparing
	 * the mark count with the total amount of spaces
	 * on the board.
 	 * @return A boolean data type. true if markCount
	 * 			matches the total amount of spaces on the board. Otherwise
	 * 			returns false.
	 */
	public boolean isFull() {
		return markCount == 9;
	}

	/**
	 * Checks to see if X-Player has won.
	 * @return A boolean data type. true if the value of
	 * 			checkWinner for X equals 1. Otherwise returns false.
	 */
	public boolean xWins() {
		if (checkWinner(LETTER_X) == 1)
			return true;
		else
			return false;
	}

	/**
	 * Checks to see if O-Player has won.
	 * @return A boolean data type. true if the value of checkWinner
	 * 			for O equals 1. Otherwise returns false.
	 */
	public boolean oWins() {
		if (checkWinner(LETTER_O) == 1)
			return true;
		else
			return false;
	}

	/**
	 * Displays the board in the console.
	 */
	public String display() {
		String line = "";
		line = displayColumnHeaders();
		line = line + addHyphens();
		for (int row = 0; row < 3; row++) {
			line = line + addSpaces();
			line = line + ("    row " + row + ' ');
			for (int col = 0; col < 3; col++)
				line = line + ("|  " + getMark(row, col) + "  ");
			line = line + ("|\n");
			line = line + addSpaces();
			line = line + addHyphens();
		}
		return line;
	}

	/**
	 * Adds the mark to the board based on the parameters given.
	 * @param row The row number the player has selected. An integer data type.
	 * @param col The column number the player has selected. An integer data type.
	 * @param mark The mark associated with the Player. Either an "X" or "O".
	 *             This is a character data type.
	 */
	public void addMark(int row, int col, char mark) {
		
		theBoard[row][col] = mark; //Adds the mark to the board
		markCount++; //Increases the mark count on the board.
	}

	/**
	 * Clears the board by adding the space variable from
	 * the class called Constants. Changes the mark count to 0.
	 */
	public void clear() {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				theBoard[i][j] = SPACE_CHAR;
		markCount = 0;
	}

	/**
	 * Checks the board to see if the player has won
	 * based on the mark supplied.
	 * @param mark The mark associated with the player.
	 *             Either an "O" or "X".
	 * @return Will return an integer of 1 if it has determined a win.
	 */
	int checkWinner(char mark) {
		int row, col;
		int result = 0;

		for (row = 0; result == 0 && row < 3; row++) {
			int row_result = 1;
			for (col = 0; row_result == 1 && col < 3; col++)
				if (theBoard[row][col] != mark)
					row_result = 0;
			if (row_result != 0)
				result = 1;
		}

		
		for (col = 0; result == 0 && col < 3; col++) {
			int col_result = 1;
			for (row = 0; col_result != 0 && row < 3; row++)
				if (theBoard[row][col] != mark)
					col_result = 0;
			if (col_result != 0)
				result = 1;
		}

		if (result == 0) {
			int diag1Result = 1;
			for (row = 0; diag1Result != 0 && row < 3; row++)
				if (theBoard[row][row] != mark)
					diag1Result = 0;
			if (diag1Result != 0)
				result = 1;
		}
		if (result == 0) {
			int diag2Result = 1;
			for (row = 0; diag2Result != 0 && row < 3; row++)
				if (theBoard[row][3 - 1 - row] != mark)
					diag2Result = 0;
			if (diag2Result != 0)
				result = 1;
		}
		return result;
	}

	/**
	 * Displays the names of the columns to help
	 * the user choose which column.
	 */
	String displayColumnHeaders() {
		String line = "";
		line = ("          ");
		for (int j = 0; j < 3; j++)
			line = line + ("|col " + j);
		line = line + "\n";
		return line;
	}

	/**
	 * Used to help represent the top, rows,
	 * and bottom of the Tic-Tac-Toe board.
	 */
	String addHyphens() {
		String line = "";
		line = ("          ");
		for (int j = 0; j < 3; j++)
			line = line + ("+-----");
		line = line + ("+\n");
		return line;
	}

	/**
	 * Used to help represent positions on the board
	 * by adding sides to the board as well as spaces
	 * to widen each position on the board.
	 */
	String addSpaces() {
		String line = "";
		line = line + ("          ");
		for (int j = 0; j < 3; j++)
			line = line + ("|     ");
		line = line + ("|\n");
		return line;
	}
/////////////////////////////////////////////////////////////////////////////
////Getters and Setters

	/**
	 * Receives the mark on the board at the position
	 * provided by the row and column that is supplied.
	 * @param row The row number the mark is placed. An integer data type.
	 * @param col The column number the mark is placed. An integer data type.
	 * @return The mark at the specified row and column number. A character data type.
	 */
	public char getMark(int row, int col) {
		return theBoard[row][col];
	}
}
