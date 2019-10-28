import java.util.*;

public class TicTacToe {
	/* ATTRIBUTES
	 * board: the board is represented by an ArrayList
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
	 * numMoves: the number of moves made is represented by a positive inteer
	 * 
	 * move: the move inputted by the user is retrieved by the Scanner object
	 * 
	 * BOARD TRACKERS
	 * row: a width X 2 matrix that stores the status of the ith row in row[i][0] and the capacity of the ith row in row[i][1]
	 * --> the status can be either:
	 * 		-2: the row is dead and cannot be won
	 * 		-1: the row has no inputs
	 * 		0: the row can be won by player A
	 * 		1: the row can be won by player B
	 * --> the capacity will range from 0 to width (if the capacity is 0 the row is empty)
	 * 		a row can be won <--> the capacity is equal to width
	 * col: a width X 2 matrix that stores the status of the ith column in col[i][0] and the capacity of the ith column in col[i][1]
	 * diag: a 2 X 2 matrix that stores the status and capacity of the south east diagonal in diag[0][0] and diag[0][1] respectively
	 * 		 the status and capacity of the south west diagonal are stored in diag[1][0] and diag[1][1] respectively
	 * 
	 * numPlayers: the number of players in the game are stored in an integer
	 * --> this attribute isn't necessary right now but it will be if the game is expanded to include more players
	 * 
	 * names: the names of the players are stored in an integer array called names
	 */
	
	private ArrayList<Integer> board;
	private int turn;
	private int width;
	private int size;
	private int numMoves;
	private Scanner move;
	private int[][] row;
	private int[][] col;
	private int[][] diag = {{-1,0},{-1,0}};
	private int numPlayers;
	private int[] names;
	
	/* CONSTRUCTOR
	 * the constructor takes an integer that will determine how big the board size is
	 * it also initializes an empty board with -1's
	 */
	
	public TicTacToe(int width, int numPlayers) {
		//minimum board size is 3x3
		if(width < 3) {
			width = 3;
		}
		
		//initializing the scanner
		this.move = new Scanner(System.in);
		
		//initializing the board
		this.size = width*width;
		this.width = width;
		this.board = new ArrayList<Integer>(this.size);
		for(int i = 0; i < this.size; i++) {
			this.board.add(i,-1);
		}
		
		//randomly assigns the first turn to player A or player B
		this.turn = (int)(2*Math.random());
		
		//initializing the names and numPlayers
		this.numPlayers = numPlayers;
		this.names = new int[this.numPlayers];
		for(int i = 0; i < this.numPlayers; i++) {
			names[i] = i;
		}
		
		//initializing the board trackers
		this.row = new int[this.width][2];
		this.col = new int[this.width][2];
		for(int i = 0; i < this.width; i++) {
			this.row[i][0] = -1;
			this.row[i][1] = 0;
			this.col[i][0] = -1;
			this.col[i][1] = 0;
		}
	}
	
	/*
	 * GAME WON METHOD
	 * if a player has won, return true, else return false
	 * three variables track the status of a row, and the columns and diagonals
	 * if any one of these variables stores the value 0 or this.width, then a player has won
	 */
	
	private boolean gameWon() {

		//checking whether a Player has won on a column, diagonal, or a row
		for(int k = 0; k < this.width; k++) {
			for(int i = 0; i < 2; i++) {
				//if the col, diag, and row is equal to i (0,1) and the col or row is full --> a player has won
				boolean colWin = (col[k][0] == i && col[k][1] == this.width);
				boolean rowWin = row[k][0] == i && row[k][1] == this.width;
				boolean diagWin = diag[i][0] == 0 && diag[i][1] == this.width;
				diagWin = diagWin || diag[i][0] == 1 && diag[i][1] == this.width;
				
				if(colWin || rowWin || diagWin) {
					System.out.println("Player " + this.names[(this.turn + 1)%2] + " wins!");
					return true;
				}
			}
		}
		return false;
	}
	
	/*
	 * MOVE MADE METHOD
	 * location[0] is the column, location[1] is the row
	 */
	
	private void moveMade(int[] location) {
		//calculates the index in the board based on the column, row, and width
		try {
			System.out.println("Attempting to execute player " + this.turn + "'s move");
			int index = location[0] + this.width*location[1];
			if(this.board.get(index) == -1) {
				
				//increasing the number of moves
				this.numMoves++;
				
				//making the move
				this.board.set(index, this.turn);
				System.out.println("Move Successful.");
				
				//updating the value stored in the row capacity tracker
				this.row[location[1]][1] += 1;
				//updating the value stored in the row status tracker --> a value of -2 means the row is dead
				if(this.row[location[1]][0] == turn || this.row[location[1]][0] == -1) {
					this.row[location[1]][0] = turn;
				}
				else {
					this.row[location[1]][0] = -2;
				}
				
				//updating the value stored in the col capacity tracker
				this.col[location[0]][1] += 1;
				//updating the value stored in col tracker --> a value of -2 means the col is dead
				if(this.col[location[0]][0] == turn || this.col[location[0]][0] == -1) {
					this.col[location[0]][0] = turn;
				}
				else {
					this.col[location[0]][0] = -2;
				}
				
				//updating the value stored in diag tracker --> a value of -2 means the diag is dead
				//checking the South East diag
				if(index%(width+1) == 0) {
					//updating the value stored in the diag SE capacity tracker
					this.diag[0][1] += 1;
					if(this.diag[0][0] == turn || this.diag[0][0] == -1) {
						this.diag[0][0] = turn;
					}
					else {
						this.diag[0][0] = -2;
					}
				}
				//checking the South West diag
				if((index%(width-1) == 0) && (index != 0) && (index != size-1)) {
					//updating the value stored in the diag SW capacity tracker
					this.diag[1][1] += 1;
					if(this.diag[1][0] == turn || this.diag[1][0] == -1) {
						this.diag[1][0] = turn;
					}
					else {
						this.diag[1][0] = -2;
					}
				}

				//if the turn is 1 then it will become 0, if the turn is 0 then it will become 1
				this.turn = (this.turn + 1)%2;
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
		for(int i = 0; i < this.size; i++) {
			this.board.set(i, -1);
		}
		
		//resetting the board trackers
		for(int i = 0; i < this.width; i++) {
			this.row[i][0] = -1;
			this.row[i][1] = 0;
			this.col[i][0] = -1;
			this.col[i][1] = 0;
		}
		this.diag[0][0] = -1;
		this.diag[0][1] = 0;
		this.diag[1][0] = -1;
		this.diag[1][1] = 0;
		
		//randomizing the turn again
		this.turn = (int)(2*Math.random());
		
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
				System.out.println("Player " + this.names[turn] + "'s turn. Enter a move as 'Column #' press Enter"
						+ " then 'Row #' and press Enter again i.e. 0 'press enter' 0 'press enter'");
				//parsing the user's input
				int[] location = new int[2];
				location[0] = this.move.nextInt();
				location[1] = this.move.nextInt();
	
				//making the move
				this.moveMade(location);
				
				//if the game has been won --> the players can play again if they want
				if(this.gameWon() == true || this.numMoves == size) {
					System.out.println("Do you want to play again? Enter yes or no.");
					
					//garbage collector
					this.move.nextLine();
					
					//actual answer
					String answer = this.move.nextLine();
					
					if(answer.equals("yes")) {
						System.out.println("Starting a new game.");
						this.clearBoard();
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
			catch(InputMismatchException e1) {
				System.out.println("You did not enter an integer number. Try again.");
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
			int leftEdge = this.width*i;
			int rightEdge = this.width*i + this.width;
			for(int j = leftEdge; j < rightEdge; j++) {
				if(j < rightEdge -1) {
					if(this.board.get(j) == -1) {
						out += "|     ";
					}
					else if(this.board.get(j) == 0) {
						out += "|  O  ";
					}
					else {
						out += "|  X  ";
					}
				}
				else {
					if(this.board.get(j) == -1) {
						out += "|     |   " + (j/this.width);
					}
					else if(this.board.get(j) == 0) {
						out += "|  O  |   " + (j/this.width);
					}
					else {
						out += "|  X  |   " + (j/this.width);
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
	public ArrayList<Integer> getBoard() {
		return this.board;
	}
	public int getTurn() {
		return this.turn;
	}
	

	
}
