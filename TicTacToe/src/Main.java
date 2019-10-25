
public class Main {
	public static void main(String[] args) {
		TicTacToe t = new TicTacToe(3);
		for(int i = 0; i < t.getSize() ; i++) {
			int[] loc = {i/t.getWidth(),i/t.getWidth()};
			t.moveMade(loc);
		}
	}
}
