import java.util.Scanner;

public class HumanPlayer implements Player {

	Stone playerColor;
	
	public HumanPlayer(Stone color) {
		playerColor = color;
	}
	
	@Override
	public MyCoordinate getMove(Board b) {
		int input_row = 0;
		int input_col = 0;
		Scanner in = new Scanner(System.in);
		System.out.printf("You are playing as %s.\n"
				+ "Red has %d captures.\n"
				+ "Yellow has %d captures.\n"
				+ "In which row would you like to place your stone?\n" , 
				playerColor, b.getRedCaptures(), b.getYellowCaptures());
		input_row = in.nextInt();
		System.out.println("In which column would you like to place your stone?");
		input_col = in.nextInt();
		MyCoordinate newCoordinate = new MyCoordinate(input_row, input_col);
		return newCoordinate;
	}

	@Override
	public Stone getStone() {
		return playerColor;
	}

}
