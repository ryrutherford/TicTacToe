import java.util.*;

public class TicTacToe {
	/* ATTRIBUTES
	 * board: the board is represented by a 2d matrix
	 * --> -1 means there is an empty slot
	 * --> 0 means there is an O in the slot
	 * --> 1 means there is an X in the slot
	 *
	 * turn: the turn is represented by an integer
	 * --> 0 means it's player A's turn who plays the O's
	 * --> 1 means it's player B's turn who plays the X's
	 * 
	 * width: the width of the board is represented by a positive integer
	 * 
	 * size: the size of the board is represented by a positive integer (width*width)
	 * 
	 * toWin: an integer that represents the number of consecutive X's or 0's that are needed to win
	 * 
	 * numMoves: the number of moves made is represented by a positive inteer
	 * 
	 * move: the move inputted by the user is retrieved by the Scanner object
	 * 
	 * numPlayers: the number of players in the game are stored in an integer
	 * --> this attribute isn't necessary right now but it will be if the game is expanded to include more players
	 */
	
	private int[][] board;
	private int turn;
	private int width;
	private int size;
	private int toWin;
	private int numMoves;
	private Scanner move;
	private int numPlayers;

	
	/* CONSTRUCTOR
	 * the constructor takes an integer that will determine how big the board size is
	 * it also initializes an empty board with -1's
	 */
	
	public TicTacToe(int width, int numPlayers, int toWin) {
		//minimum board size is 3x3
		if(width < 3) {
			System.out.println("The width must be greater than or equal to 3. The width is now 3.");
			width = 3;
		}
		if(toWin > width) {
			System.out.println("The value to win must be less than or equal to the width of the board. The value to win is now the width");
			toWin = width;
		}
		if(toWin < 3) {
			System.out.println("The value to win must be greater than or equal to 3. The value to win is now 3");
			toWin = 3;
		}
		if(numPlayers > 26) {
			System.out.println("No more than 26 players can play the game. The number of players is now 26");
			numPlayers = 26;
		}
		if(numPlayers > width) {
			System.out.println("The number of players must be less than or equal to the width of the board. The number of players is now the width");
			numPlayers = width;
		}
		if(numPlayers < 2) {
			System.out.println("The number of players must be greater than or equal to 2. The number of players is now 2");
			numPlayers = 2;
		}
		
		//initializing the scanner
		this.move = new Scanner(System.in);
		
		//initializing the toWin
		this.toWin = toWin;
		this.size = width*width;
		this.width = width;
		
		//initializing the map
		this.board = new int[width][width];
		for(int i = 0; i < this.width; i++) {
			for(int j = 0; j < this.width; j++) {
				this.board[i][j] = -1;
			}
		}
		//initializing the numPlayers
		this.numPlayers = numPlayers;	
		//randomly assigns the first turn to player 0-numPlayers-1
		this.turn = (int)(this.numPlayers*Math.random());
	}
	
	/*
	 * GAME WON METHOD
	 * if a player has won, return true, else return false
	 */
	
	private boolean gameWon() {

		
		int prev; //prev stores a predecessor value for the matrix value at row i, col j
		int k; //k will be used to iterate through the predecessors in a row, col, or diag
		
		//the nested for loop iterates through each map entry and uses the indices to check for 'toWin' consecutive X's or O's
		for(int i = 0; i < this.width; i++) {
			for(int j = 0; j < this.width; j++) {

				//ROW CHECK
				int cur = this.board[i][j];
				try {
					for(k = 1; k < this.toWin; k++) {
						prev = this.board[i][j-k];
						if(prev != cur || cur == -1) { //if prev and cur don't match or cur = -1 then the row hasn't been won
							break;
						}
					}
					if(k == this.toWin) { //if we iterated through the whole loop then a player has won
						System.out.println("Player " + (char)(cur+65) + " has won! Congratulations.");
						return true;
					}
				}
				catch(Exception e) {
					//do nothing, array index out of bounds or negative index exception was thrown
				}
				
				//SW DIAG CHECK
				try {
					for(k = 1; k < this.toWin; k++) {
						prev = this.board[i-k][j-k];
						if(prev != cur || cur == -1) { //if prev isn't equal to cur or cur = -1 then the SW diag hasn't been won
							break;
						}
					}
					if(k == this.toWin) { //if we iterated through the whole loop then a player has won
						System.out.println("Player " + (char)(cur+65) + " has won! Congratulations.");
						return true;
					}
				}
				catch(Exception e) {
					//do nothing, array index out of bounds or negative index exception was thrown
				}
				
				//SE DIAG CHECK
				try {
					for(k = 1; k < this.toWin; k++) {
						prev = this.board[i-k][j+k];
						if(prev != cur || cur == -1) { //if prev isn't equal to cur or cur = -1 then the SE diag hasn't been won
							break;
						}
					}
					if(k == this.toWin) { //if we iterated through the whole loop then a player has won
						System.out.println("Player " + (char)(cur+65) + " has won! Congratulations.");
						return true;
					}
				}
				catch(Exception e) {
					//do nothing, array index out of bounds or negative index exception was thrown
				}
				
				//COL CHECK
				cur = this.board[i][j];
				try {
					for(k = 1; k < this.toWin; k++) {
						prev = this.board[i-k][j];
						if(prev != cur || cur == -1) { //if prev isn't equal to cur or cur = -1 then the SW diag hasn't been won
							break;
						}
					}
					if(k == this.toWin) { //if we iterated through the whole loop then a player has won
						System.out.println("Player " + (char)(cur+65) + " has won! Congratulations.");
						return true;
					}
				}
				catch(Exception e) {
					//do nothing, array index out of bounds or negative index exception was thrown
				}
			}
		}
		return false;
	}
	
	/*
	 * MOVE MADE METHOD
	 * location[0] is the row, location[1] is the column
	 */
	
	private void moveMade(int[] location) {
		try {
			System.out.println("Attempting to execute player " + (char) (this.turn+65) + "'s move");
			int row = location[0];
			int col = location[1];
			if(this.board[row][col] == -1) {
				
				//increasing the number of moves
				this.numMoves++;
				
				//making the move
				//this.board.set(index, this.turn);
				this.board[row][col] = this.turn;
				System.out.println("Move Successful.");
				
				//updating the turn
				this.turn = (this.turn + 1)%(this.numPlayers);
			}
			else {
				System.out.println("Move failed. Try again.");
			}
			
			System.out.println(this);
			
		}
		catch(Exception e) {
			System.out.println("Invalid location entered, try again");
		}
	}
	
	/*
	 * CLEAR BOARD METHOD
	 * resets the board to start a new game
	 */
	
	private void clearBoard() {
		//resetting the board
		for(int i = 0; i < this.width; i++) {
			//this.board.set(i, -1);
			for(int j = 0; j < this.width; j++) {
				this.board[i][j] = -1;
			}
		}
		
		//randomizing the turn again
		this.turn = (int)(this.numPlayers*Math.random());
		
		//resetting the number of moves made
		this.numMoves = 0;
	}
	
	/*
	 * PLAY METHOD
	 * invoke this method to play a game of Tic Tac Toe 
	 */
	
	public void play() {
		System.out.println("Welcome to TicTacToe. Please note that the minimum board size is 3X3. Here is the board:");
		System.out.println(this);
		while(this.numMoves < this.size) {
			try {
				System.out.println("Player " + (char) (this.turn + 65) + "'s turn. Enter a move as 'Row #' press Enter"
						+ " then 'Column #' and press Enter again i.e. 0 'press enter' 0 'press enter'");
				//parsing the user's input
				int[] location = new int[2];
				location[0] = this.move.nextInt();
				location[1] = this.move.nextInt();
	
				//making the move
				this.moveMade(location);
				
				//if the game has been won --> the players can play again if they want
				if(this.gameWon() == true || this.numMoves == size) {
					if(this.numMoves == size) {
						System.out.println("It's a tie!");
					}
					System.out.println("Do you want to play again? Enter yes or no.");
					
					//garbage collector
					this.move.nextLine();
					
					//actual answer
					String answer = this.move.nextLine();
					
					if(answer.equals("yes")) {
						System.out.println("Starting a new game.");
						this.clearBoard();
						System.out.println("Here is the board: " + '\n' + this);
					}
					else if(answer.equals("no")) {
						System.out.println("Thanks for playing!");
						break;
					}
					else {
						System.out.println("Invalid Input. The game has ended.");
						break;
					}
				}
			}
			catch(InputMismatchException e) {
				System.out.println("You did not enter an integer number. Try again.");
				//garbage collector
				this.move.nextLine();
			}
			catch(Exception e) {
				System.out.println("Something went wrong. Try again.");
			}
		}
		
	}
	
	/*
	 * DRAW BOARD METHOD
	 */
	
	public String toString() {
		String out = "";
		
		//double for loop iterates through the arraylist like it's a width X width matrix
		for(int i = 0; i < this.width; i++) {
			
			//prints out the column numbers
			int k = 0;
			while(i == 0 && k < this.width) {
				out += "   " + k + "  ";
				k++;
				if(k == this.width) {
					out+= "\n";
				}
			}
			k = 0;
			
			//prints out the section dividers of the board
			while(i != 0 && k < this.width) {
				out += "------";
				if(k == this.width - 1) {
					out += "-";
				}
				k++;
			}
			out+= "\n";
			
			//prints out the X's, O's, or empty boxes
			for(int j = 0; j < this.width; j++) {
				if(j < this.width -1) {
					if(this.board[i][j]==-1) {
						out += "|     ";
					}
					else {
						out += "|  " + (char) (board[i][j] + 65) + "  ";
					}
				}
				else {
					if(this.board[i][j]==-1) {
						out += "|     |   " + i;
					}
					else {
						out += "|  " + (char) (board[i][j] + 65) + "  |   " + i;
					}
				}

			}
			out += "\n";
		}
		return out;
	}
	
	/*
	 * GETTERS
	 */
	
	public int getSize() {
		return this.size;
	}
	public int getWidth() {
		return this.width;
	}
	public int[][] getBoard() {
		return this.board;
	}
	public int getTurn() {
		return this.turn;
	}	
}
