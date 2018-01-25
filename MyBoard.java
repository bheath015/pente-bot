import java.util.Arrays;

public class MyBoard implements Board {

	private Stone[][] board;
	private int boardSize;
	private int moveNumber;
	private int redCaptures;
	private int yellowCaptures;
	private boolean gameOver;
	Stone winner;
	
	public MyBoard() {
		boardSize = 19;
		board = setBoard(boardSize);
		moveNumber = 0;
		redCaptures = 0;
		yellowCaptures = 0;
	}
	
	private Stone[][] setBoard(int size) {
		Stone[][] newBoard = new Stone[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				newBoard[i][j] = Stone.EMPTY;
			}
		}
		return newBoard;
	}
	
	@Override
	public void placeStone(Stone s, Coordinate c) {
		if (isOutOfBounds(c)) {
			throw new IllegalArgumentException("ERROR: That is not a place on the board!");
		}
		if (!isEmpty(c)) {
			throw new IllegalArgumentException("ERROR: There is already a stone at that position!");
		}
		if (moveNumber == 0 && c.getRow() != 9 && c.getColumn() != 9) {
			throw new IllegalArgumentException("ERROR: You have to place your stone in the center!");
		}
		if (moveNumber == 2 && (Math.abs(c.getRow() - 9) < 3 && Math.abs(c.getColumn() - 9) < 3)) {
			throw new IllegalArgumentException("ERROR: You cannot place your stone within 3 intersections of the center!");
		}
		board[c.getRow()][c.getColumn()] = s;
		moveNumber++;
		Stone otherColor;
		if (s == Stone.RED) {
			otherColor = Stone.YELLOW;
		} else {
			otherColor = Stone.RED;
		}
		gameOver = checkFive(c, s);
		if (gameOver == true) {
			winner = s;
		}
		if (s == Stone.RED) {
			redCaptures = redCaptures + countNewCaptures(c, s, otherColor);;
		} else {
			yellowCaptures = yellowCaptures + countNewCaptures(c, s, otherColor);;
		}
		if (checkFiveCaptures(s)) {
			gameOver = true;
			winner = s;
		}
	}

	private boolean checkFive(Coordinate c, Stone p1) {
		boolean gameComplete = false;
		int consecutiveHorizontal = 0;
		int consecutiveVertical = 0;
		int consecutiveDiagonalFalling = 0;
		int consecutiveDiagonalRising = 0;
		for (int i = -4; i < 5; i++) {
			if (c.getRow() + i < 19 && c.getRow() + i >= 0) {
				if (board[c.getRow() + i][c.getColumn()] == p1) {
					consecutiveHorizontal++;
					if (consecutiveHorizontal == 5) {
						gameComplete = true;
						break;
					}
				} else {
					consecutiveHorizontal = 0;
				}
			}
			if (c.getColumn() + i < 19 && c.getColumn() + i >= 0) {
				if (board[c.getRow()][c.getColumn() + i] == p1) {
					consecutiveVertical++;
					if (consecutiveVertical == 5) {
						gameComplete = true;
					}
				} else {
					consecutiveVertical = 0;
				}
			}
			if (c.getColumn() + i < 19 && c.getColumn() + i >= 0 &&
					c.getRow() + i < 19 && c.getRow() + i >= 0) {
				if (board[c.getRow() + i][c.getColumn() + i] == p1) {
					consecutiveDiagonalFalling++;
					if (consecutiveDiagonalFalling == 5) {
						gameComplete = true;
					}
				} else {
					consecutiveDiagonalFalling = 0;
				}
			}
			if (c.getColumn() - i < 19 && c.getColumn() - i >= 0 &&
					c.getRow() + i < 19 && c.getRow() + i >= 0) {
				if (board[c.getRow() + i][c.getColumn() - i] == p1) {
					consecutiveDiagonalRising++;
					if (consecutiveDiagonalRising == 5) {
						gameComplete = true;
					}
				} else {
					consecutiveDiagonalRising = 0;
				}
			}
		}
		return gameComplete;
	}
	
	private int countNewCaptures(Coordinate coord, Stone p1, Stone p2) {
		int newCaptures = 0;
		if (checkCapture(coord, p1, p2, "1") == true ) {
			board[coord.getRow() - 1][coord.getColumn() + 1] = Stone.EMPTY;
			board[coord.getRow() - 2][coord.getColumn() + 2] = Stone.EMPTY;
			newCaptures++;
		}
		if (checkCapture(coord, p1, p2, "2") == true ) {
			board[coord.getRow() - 1][coord.getColumn() - 1] = Stone.EMPTY;
			board[coord.getRow() - 2][coord.getColumn() - 2] = Stone.EMPTY;
			newCaptures++;
		}
		if (checkCapture(coord, p1, p2, "3") == true ) {
			board[coord.getRow() + 1][coord.getColumn() - 1] = Stone.EMPTY;
			board[coord.getRow() + 2][coord.getColumn() - 2] = Stone.EMPTY;
			newCaptures++;
		}
		if (checkCapture(coord, p1, p2, "4") == true ) {
			board[coord.getRow() + 1][coord.getColumn() + 1] = Stone.EMPTY;
			board[coord.getRow() + 2][coord.getColumn() + 2] = Stone.EMPTY;
			newCaptures++;
		}
		if (checkCapture(coord, p1, p2, "N") == true ) {
			board[coord.getRow() - 1][coord.getColumn()] = Stone.EMPTY;
			board[coord.getRow() - 2][coord.getColumn()] = Stone.EMPTY;
			newCaptures++;
		}
		if (checkCapture(coord, p1, p2, "S") == true ) {
			board[coord.getRow() + 1][coord.getColumn()] = Stone.EMPTY;
			board[coord.getRow() + 2][coord.getColumn()] = Stone.EMPTY;
			newCaptures++;
		}
		if (checkCapture(coord, p1, p2, "E") == true ) {
			board[coord.getRow()][coord.getColumn() + 1] = Stone.EMPTY;
			board[coord.getRow()][coord.getColumn() + 2] = Stone.EMPTY;
			newCaptures++;
		}
		if (checkCapture(coord, p1, p2, "W") == true ) {
			board[coord.getRow()][coord.getColumn() - 1] = Stone.EMPTY;
			board[coord.getRow()][coord.getColumn() - 2] = Stone.EMPTY;
			newCaptures++;
		}
		return newCaptures;
	}
	
	private boolean checkCapture(Coordinate c, Stone p1, Stone p2, String directionOrQuadrant) {
		boolean captured = false;
		if (directionOrQuadrant == "1" && c.getRow() >= 3 && c.getColumn() <= 15) {
			if (board[c.getRow() - 1][c.getColumn() + 1] == p2 &&
				board[c.getRow() - 2][c.getColumn() + 2] == p2 &&
				board[c.getRow() - 3][c.getColumn() + 3] == p1) {
				captured = true;
			}
		}
		if (directionOrQuadrant == "2" && c.getRow() >= 3 && c.getColumn() >= 3) {
			if (board[c.getRow() - 1][c.getColumn() - 1] == p2 &&
				board[c.getRow() - 2][c.getColumn() - 2] == p2 &&
				board[c.getRow() - 3][c.getColumn() - 3] == p1) {
				captured = true;
			}
		}
		if (directionOrQuadrant == "3" && c.getRow() <= 15 && c.getColumn() >= 3) {
			if (board[c.getRow() + 1][c.getColumn() - 1] == p2 &&
				board[c.getRow() + 2][c.getColumn() - 2] == p2 &&
				board[c.getRow() + 3][c.getColumn() - 3] == p1) {
				captured = true;
			}
		}
		if (directionOrQuadrant == "4"  && c.getRow() <= 15 && c.getColumn() <= 15) {
			if (board[c.getRow() + 1][c.getColumn() + 1] == p2 &&
				board[c.getRow() + 2][c.getColumn() + 2] == p2 &&
				board[c.getRow() + 3][c.getColumn() + 3] == p1) {
				captured = true;
			}
		}
		if (directionOrQuadrant == "N" && c.getRow() >= 3) {
			if (board[c.getRow() - 1][c.getColumn()] == p2 &&
				board[c.getRow() - 2][c.getColumn()] == p2 &&
				board[c.getRow() - 3][c.getColumn()] == p1) {
				captured = true;
			}
		}
		if (directionOrQuadrant == "S" && c.getRow() <= 15) {
			if (board[c.getRow() + 1][c.getColumn()] == p2 &&
				board[c.getRow() + 2][c.getColumn()] == p2 &&
				board[c.getRow() + 3][c.getColumn()] == p1) {
				captured = true;
			}
		}
		if (directionOrQuadrant == "E" && c.getColumn() <= 15) {
			if (board[c.getRow()][c.getColumn() + 1] == p2 &&
				board[c.getRow()][c.getColumn() + 2] == p2 &&
				board[c.getRow()][c.getColumn() + 3] == p1) {
				captured = true;
			}
		}
		if (directionOrQuadrant == "W" && c.getColumn() >= 3) {
			if (board[c.getRow()][c.getColumn() - 1] == p2 &&
				board[c.getRow()][c.getColumn() - 2] == p2 &&
				board[c.getRow()][c.getColumn() - 3] == p1) {
				captured = true;
			}
		}
		return captured;
	}
	
	private boolean checkFiveCaptures(Stone player) {
		if (player == Stone.RED) {
			if (redCaptures >= 5) {
				return true;
			} else {
				return false;
			}
		} else if (player == Stone.YELLOW) {
			if (yellowCaptures >= 5) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
	@Override
	public Stone pieceAt(Coordinate c) {
		Stone color = board[c.getRow()][c.getColumn()];
		return color;
	}

	@Override
	public boolean isOutOfBounds(Coordinate c) {
		if (c.getRow() < 0 || c.getRow() > 19) {
			return true;
		}
		if (c.getColumn() < 0 || c.getColumn() > 19) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isEmpty(Coordinate c) {
		if (board[c.getRow()][c.getColumn()] == Stone.EMPTY) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int getMoveNumber() {
		return moveNumber;
	}

	@Override
	public int getRedCaptures() {
		return redCaptures;
	}

	@Override
	public int getYellowCaptures() {
		return yellowCaptures;
	}

	@Override
	public boolean gameOver() {
		return gameOver;
	}

	@Override
	public Stone getWinner() {
		return winner;
	}
	
	public String toString() {
		String output = "";
		String topLine = String.format("   %3d |%3d |%3d |%3d |%3d |%3d |%3d |%3d |%3d |%3d |%3d |%3d |%3d |%3d |%3d |%3d |%3d |%3d |%3d\n" , 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18);
		output = output + topLine;
		for (int i = 0; i < 19; i++) {
//			for (int j = 0; j < 19; j++) {
//				System.out.println(board[i][j]);
//			}
//			System.out.println("\n");
			char[] outputLine = new char[19];
			for (int j = 0; j < 19; j++) {
				if (board[i][j] == Stone.YELLOW) {
					outputLine[j] = 'X';
				} else if (board[i][j] == Stone.RED) {
					outputLine[j] = 'O';
				} else {
					outputLine[j] = '-';
				}
			}
			String gameLine = String.format("%2d %3s |%3s |%3s |%3s |%3s |%3s |%3s |%3s |%3s |%3s |%3s |%3s |%3s |%3s |%3s |%3s |%3s |%3s |%3s\n" , i, outputLine[0], outputLine[1], outputLine[2], outputLine[3], outputLine[4], outputLine[5], outputLine[6], outputLine[7], outputLine[8], outputLine[9], outputLine[10], outputLine[11], outputLine[12], outputLine[13], outputLine[14], outputLine[15], outputLine[16], outputLine[17], outputLine[18]);
			output = output + gameLine;
		}
		return output;
	}

}
