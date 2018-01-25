import java.util.Random;


public class Game {

	public static void main(String[] args) {
		MyBoard board = new MyBoard();
		BheathPlayer firstPlayer = null;
//		HumanPlayer firstPlayer = null;
//		BheathPlayer secondPlayer = null;
		HumanPlayer secondPlayer = null;
		boolean firstPlayerGoesFirst = firstMove();
		if (firstPlayerGoesFirst == true) {
			firstPlayer = new BheathPlayer(Stone.RED);
			secondPlayer = new HumanPlayer(Stone.YELLOW);
//			secondPlayer = new BheathPlayer(Stone.YELLOW);
			while (!board.gameOver()) {
				board.placeStone(firstPlayer.getStone(), firstPlayer.getMove(board));
				System.out.println(board);
				if (!board.gameOver()) {
					board.placeStone(secondPlayer.getStone(), secondPlayer.getMove(board));
					System.out.println(board);
				} else {
					break;
				}
			}
			System.out.printf("The game is over and the winner is %s" , board.getWinner());
		} else {
			firstPlayer = new BheathPlayer(Stone.YELLOW);
			secondPlayer = new HumanPlayer(Stone.RED);
//			secondPlayer = new BheathPlayer(Stone.RED);
			while (!board.gameOver()) {
				board.placeStone(secondPlayer.getStone(), secondPlayer.getMove(board));
				System.out.println(board);
				if (!board.gameOver()) {
					board.placeStone(firstPlayer.getStone(), firstPlayer.getMove(board));
					System.out.println(board);
				} else {
					break;
				}
			}
			System.out.printf("The game is over and the winner is %s" , board.getWinner());
		}
	}
	
	private static boolean firstMove() {
		boolean coinFlip = false;
		Random rand = new Random();
		int toss = rand.nextInt(2);
		if (toss == 0) {
			coinFlip = true;
		}
		return coinFlip;
	}

}
