import java.util.*;

public class TicTacToe {
	/* ATTRIBUTES
	 * the board is represented by an ArrayList
	 * --> -1 means there is an empty slot
	 * --> 0 means there is an O in the slot
	 * --> 1 means there is an X in the slot
	 *
	 * the turn is represented by an integer
	 * --> 0 means it's player A's turn who plays the O's
	 * --> 1 means it's player B's turn who plays the X's
	 * 
	 * the width of the board is represented by a positive integer
	 * 
	 * the size of the board is represented by a positive integer (width*width)
	 */
	
	private ArrayList<Integer> board;
	private int turn;
	private int width;
	private int size;
	
	/* CONSTRUCTOR
	 * the constructor takes an integer that will determine how big the board size is
	 * it also initializes an empty board with -1's
	 */
	
	public TicTacToe(int width) {
		//initializing the board
		this.size = width*width;
		this.width = width;
		this.board = new ArrayList<Integer>(this.size);
		for(int i = 0; i < this.size; i++) {
			this.board.add(i,-1);
		}
		//randomly assigns the first turn to player A or player B
		turn = (int)(2*Math.random());
	}
	
	/*
	 * MOVE MADE METHOD
	 * location[0] is the column, location[1] is the row
	 */
	
	public void moveMade(int[] location) {
		//calculates the index in the board based on the column, row, and width
		try {
			System.out.println("Attempting to execute player " + this.turn + "'s move");
			int index = location[0] + this.width*location[1];
			if(this.board.get(index) == -1) {
				this.board.set(index, this.turn);
				System.out.println("Move Successful.");
			}
			else {
				System.out.println("Move failed. Turn lost.");
			}
			System.out.println(this);
			
			//if the turn is 1 then it will become 0, if the turn is 0 then it will become 1
			this.turn = (this.turn + 1)%2;
			System.out.println("It is now player " + this.turn + "'s turn.\n");
		}
		catch(Exception e) {
			System.out.println("Invalid location entered, try again");
		}
	}
	
	/*
	 * DRAW BOARD METHOD
	 */
	public String toString() {
		String out = "";
		
		//double for loop iterates through the arraylist like it's a width X width matrix
		for(int i = 0; i < this.width; i++) {
			
			//prints out the section dividers of the board
			int k = 0;
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
						out += "|     |";
					}
					else if(this.board.get(j) == 0) {
						out += "|  O  |";
					}
					else {
						out += "|  X  |";
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
