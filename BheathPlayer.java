import java.util.Random;

public class BheathPlayer implements Player {

	Stone playerColor;
	Stone oppColor;
	Board b;
	
	public BheathPlayer(Stone color) {
		playerColor = color;
		if (playerColor == Stone.RED) {
			oppColor = Stone.YELLOW;
		} else {
			oppColor = Stone.RED;
		}
		
	}
	
	@Override
	public Coordinate getMove(Board boardInput) {
		b = boardInput;
		System.out.printf("You are playing as %s.\n"
				+ "Red has %d captures.\n"
				+ "Yellow has %d captures.\n"
				+ "In which row would you like to place your stone?\n" , 
				playerColor, b.getRedCaptures(), b.getYellowCaptures());
		Coordinate turnCoordinate = null;
		if (b.getMoveNumber() == 0) {
			turnCoordinate = new MyCoordinate(9, 9);
		} else if (b.getMoveNumber() == 2) {
			turnCoordinate = new MyCoordinate(5, 5);
		} else if (checkSeq(4, playerColor) != null) {
			turnCoordinate = checkSeq(4, playerColor);
		} else if (checkSeq(4, oppColor) != null) {
			turnCoordinate = checkSeq(4, oppColor);
		} else if (checkSeq(3, oppColor) != null) {
			turnCoordinate = checkSeq(3, oppColor);
		} else if (checkSeq(3, playerColor) != null) {
			turnCoordinate = checkSeq(3, playerColor);
		} else if (availableCapture() != null) {
			turnCoordinate = availableCapture();
		} else if (checkSeq(2, playerColor) != null) {
			turnCoordinate = checkSeq(2, playerColor);
		} else if (fillGap() != null) {
			turnCoordinate = fillGap();
		} else if (makeGap() != null) {
			turnCoordinate = makeGap();
		} else {
			turnCoordinate = getRandomCoord();
		}
		return turnCoordinate;
	}

	@Override
	public Stone getStone() {
		return playerColor;
	}
	
	private MyCoordinate fillGap() {
		for (int i = 0; i < 19; i++) {
			for (int j = 0; j < 19; j++) {
				MyCoordinate testCoord0 = new MyCoordinate(i, j);
				if (b.isEmpty(testCoord0)) {
					if (testCoord0.getRow() + 1 < 19 && testCoord0.getRow() - 1 >= 0) {
						MyCoordinate testCoordE1 = new MyCoordinate(i - 1, j);
						MyCoordinate testCoordE2 = new MyCoordinate(i + 1, j);
						if (b.pieceAt(testCoordE1) == playerColor &&
								b.pieceAt(testCoordE2) == playerColor) {
							return testCoord0;
						}
					}
					if (testCoord0.getColumn() - 1 >= 0) {
						MyCoordinate testCoordE1 = new MyCoordinate(i, j - 1);
						MyCoordinate testCoordW1 = new MyCoordinate(i, j + 1);
						if (b.pieceAt(testCoordE1) == playerColor &&
								b.pieceAt(testCoordW1) == playerColor) {
							return testCoord0;
						}
					}
				}
			}
		}
		return null;
	}
	
	private MyCoordinate makeGap() {
		for (int i = 0; i < 19; i++) {
			for (int j = 0; j < 19; j++) {
				MyCoordinate testCoord0 = new MyCoordinate(i, j);
				if (b.isEmpty(testCoord0)) {
					if (testCoord0.getColumn() + 4 < 19) {
						MyCoordinate testCoordE1 = new MyCoordinate(i, j + 1);
						MyCoordinate testCoordE2 = new MyCoordinate(i, j + 2);
						MyCoordinate testCoordE3 = new MyCoordinate(i, j + 3);
						MyCoordinate testCoordE4 = new MyCoordinate(i, j + 4);
						if (b.pieceAt(testCoordE1) == Stone.EMPTY &&
								b.pieceAt(testCoordE2) == playerColor &&
								b.pieceAt(testCoordE3) == Stone.EMPTY &&
								b.pieceAt(testCoordE4) == Stone.EMPTY) {
							return testCoord0;
						}
					}
					if (testCoord0.getColumn() - 4 >= 0) {
						MyCoordinate testCoordW1 = new MyCoordinate(i, j - 1);
						MyCoordinate testCoordW2 = new MyCoordinate(i, j - 2);
						MyCoordinate testCoordW3 = new MyCoordinate(i, j - 3);
						MyCoordinate testCoordW4 = new MyCoordinate(i, j - 4);
						if (b.pieceAt(testCoordW1) == Stone.EMPTY &&
								b.pieceAt(testCoordW2) == playerColor &&
								b.pieceAt(testCoordW3) == Stone.EMPTY &&
								b.pieceAt(testCoordW4) == Stone.EMPTY) {
							return testCoord0;
						}
					}
					if (testCoord0.getRow() + 4 < 19) {
						MyCoordinate testCoordS1 = new MyCoordinate(i + 1, j);
						MyCoordinate testCoordS2 = new MyCoordinate(i + 2, j);
						MyCoordinate testCoordS3 = new MyCoordinate(i + 3, j);
						MyCoordinate testCoordS4 = new MyCoordinate(i + 4, j);
						if (b.pieceAt(testCoordS1) == Stone.EMPTY &&
								b.pieceAt(testCoordS2) == playerColor &&
								b.pieceAt(testCoordS3) == Stone.EMPTY &&
								b.pieceAt(testCoordS4) == Stone.EMPTY) {
							return testCoord0;
						}
					}					
					if (testCoord0.getRow() - 4 >= 0) {
						MyCoordinate testCoordN1 = new MyCoordinate(i - 1, j);
						MyCoordinate testCoordN2 = new MyCoordinate(i - 2, j);
						MyCoordinate testCoordN3 = new MyCoordinate(i - 3, j);
						MyCoordinate testCoordN4 = new MyCoordinate(i - 4, j);
						if (b.pieceAt(testCoordN1) == Stone.EMPTY &&
								b.pieceAt(testCoordN2) == playerColor &&
								b.pieceAt(testCoordN3) == Stone.EMPTY &&
								b.pieceAt(testCoordN4) == Stone.EMPTY) {
							return testCoord0;
						}
					}
				}
			}	
		}
		return null;
	}
	
	private MyCoordinate availableCapture() {
		for (int i = 0; i < 19; i++) {
			for (int j = 0; j < 19; j++) {
				MyCoordinate testCoord0 = new MyCoordinate(i, j);
				if (testCoord0.getColumn() + 3 < 19) {
					MyCoordinate testCoordE1 = new MyCoordinate(i, j + 1);
					MyCoordinate testCoordE2 = new MyCoordinate(i, j + 2);
					MyCoordinate testCoordE3 = new MyCoordinate(i, j + 3);
					if (b.pieceAt(testCoordE1) == oppColor &&
							b.pieceAt(testCoordE2) == oppColor &&
							b.pieceAt(testCoordE3) == playerColor) {
						return testCoord0;
					}
				}
				if (testCoord0.getColumn() - 3 >= 0) {
					MyCoordinate testCoordW1 = new MyCoordinate(i, j - 1);
					MyCoordinate testCoordW2 = new MyCoordinate(i, j - 2);
					MyCoordinate testCoordW3 = new MyCoordinate(i, j - 3);
					if (b.pieceAt(testCoordW1) == oppColor &&
							b.pieceAt(testCoordW2) == oppColor &&
							b.pieceAt(testCoordW3) == playerColor) {
						return testCoord0;
					}
				}
				if (testCoord0.getRow() + 3 < 19) {
					MyCoordinate testCoordS1 = new MyCoordinate(i + 1, j);
					MyCoordinate testCoordS2 = new MyCoordinate(i + 2, j);
					MyCoordinate testCoordS3 = new MyCoordinate(i + 3, j);
					if (b.pieceAt(testCoordS1) == oppColor &&
							b.pieceAt(testCoordS2) == oppColor &&
							b.pieceAt(testCoordS3) == playerColor) {
						return testCoord0;
					}
				}
				if (testCoord0.getRow() - 3 >= 0) {
					MyCoordinate testCoordN1 = new MyCoordinate(i - 1, j);
					MyCoordinate testCoordN2 = new MyCoordinate(i - 2, j);
					MyCoordinate testCoordN3 = new MyCoordinate(i - 3, j);
					if (b.pieceAt(testCoordN1) == oppColor &&
							b.pieceAt(testCoordN2) == oppColor &&
							b.pieceAt(testCoordN3) == playerColor) {
						return testCoord0;
					}
				}
			}
		}

		return null;
	}
	
	private MyCoordinate getRandomCoord() {
		MyCoordinate outputCoord = null;
		Random rand = new Random();
		boolean freeSpace = false;
		int attemptCount = 200;
		while (freeSpace == false && attemptCount > 0) {
			int row = rand.nextInt(19);
			int col = rand.nextInt(19);
			MyCoordinate tempCoordinate = new MyCoordinate(row, col);
			if (!isDefenselessCapture(tempCoordinate)) {
				outputCoord = tempCoordinate;
				freeSpace = b.isEmpty(outputCoord);
			}
			attemptCount--;
		}
		return outputCoord;
	}
	
	private boolean isDefenselessCapture(MyCoordinate coord) {
		MyCoordinate testCoordS = new MyCoordinate(coord.getRow() + 1, coord.getColumn());
		if (coord.getRow() + 1 < 19 && b.pieceAt(testCoordS) == playerColor) {
			MyCoordinate oppCoord1 = new MyCoordinate(coord.getRow() + 2, coord.getColumn());
			if (coord.getRow() + 2 < 19 && b.pieceAt(oppCoord1) == oppColor) {
				return true;
			}
			MyCoordinate oppCoord2 = new MyCoordinate(coord.getRow() - 1, coord.getColumn());
			if (coord.getRow() - 1 >= 0 && b.pieceAt(oppCoord2) == oppColor) {
				return true;
			}
		} 
		MyCoordinate testCoordN = new MyCoordinate(coord.getRow() - 1, coord.getColumn());
		if (coord.getRow() - 1 >= 0 && b.pieceAt(testCoordN) == playerColor) {
			MyCoordinate oppCoord1 = new MyCoordinate(coord.getRow() + 1, coord.getColumn());
			if (coord.getRow() + 1 < 19 && b.pieceAt(oppCoord1) == oppColor) {
				return true;
			}
			MyCoordinate oppCoord2 = new MyCoordinate(coord.getRow() - 2, coord.getColumn());
			if (coord.getRow() - 2 >= 0 && b.pieceAt(oppCoord2) == oppColor) {
				return true;
			}
		} 
		MyCoordinate testCoordE = new MyCoordinate(coord.getRow(), coord.getColumn() + 1);
		if (coord.getColumn() + 1 < 19 && b.pieceAt(testCoordE) == playerColor) {
			MyCoordinate oppCoord1 = new MyCoordinate(coord.getRow(), coord.getColumn() - 1);
			if (coord.getColumn() - 1 < 19 && b.pieceAt(oppCoord1) == oppColor) {
				return true;
			}
			MyCoordinate oppCoord2 = new MyCoordinate(coord.getRow() + 2, coord.getColumn());
			if (coord.getColumn() + 2 >= 0 && b.pieceAt(oppCoord2) == oppColor) {
				return true;
			}
		} 
		MyCoordinate testCoordW = new MyCoordinate(coord.getRow(), coord.getColumn() - 1);
		if (coord.getColumn() - 1 >= 0 && b.pieceAt(testCoordW) == playerColor) {
			MyCoordinate oppCoord1 = new MyCoordinate(coord.getRow(), coord.getColumn() - 2);
			if (coord.getColumn() - 2 < 19 && b.pieceAt(oppCoord1) == oppColor) {
				return true;
			}
			MyCoordinate oppCoord2 = new MyCoordinate(coord.getRow(), coord.getColumn() + 1);
			if (coord.getColumn() + 1 >= 0 && b.pieceAt(oppCoord2) == oppColor) {
				return true;
			}
		} 
		return false;
	}
	
	private MyCoordinate checkSeq (int seqLen, Stone player) {
		MyCoordinate outputCoord = null;
		for (int i = 0; i < 19; i++) {
			for (int j = 0; j < 19; j++) {
				MyCoordinate testCoord = new MyCoordinate(i, j);
				if (b.isEmpty(testCoord)) {
					if (checkSeqV(testCoord, seqLen, player) != null) {
						if (seqLen == 4) {
							outputCoord = finishFiveV(checkSeqV(testCoord, seqLen, player));
						} else if (seqLen == 3 && player == oppColor) {
							outputCoord = defendFourV(testCoord);
						} else if (seqLen == 3 && player == playerColor) {
							System.out.println("step one");
							if(makeFourV(testCoord) != null) {
								return testCoord;
							}
						}
					}
					if (checkSeqH(testCoord, seqLen, player) != null) {
						if (seqLen == 4) {
							outputCoord = finishFiveH(checkSeqH(testCoord, seqLen, player));
						} else if (seqLen == 3 && player == oppColor) {
							outputCoord = defendFourH(testCoord);
						} else if (seqLen == 3 && player == playerColor) {
							if(makeFourH(testCoord) != null) {
								return testCoord;
							}
						}
					}
				}
			}
		}
		return outputCoord;
	}
	
	private MyCoordinate checkSeqV(MyCoordinate coord, int k, Stone s) {
		MyCoordinate outputCoord = null;
		int consecutiveVertical = 0;
		for (int i = 1; i < k + 1; i++) {
			int row = coord.getRow() + i;
			int col = coord.getColumn();
			MyCoordinate c = new MyCoordinate(row, col);
			if (c.getRow() + i < 19 && c.getRow() + i >= 0) {
				if (b.pieceAt(c) == s) {
					consecutiveVertical++;
					if (consecutiveVertical >= k) {
						outputCoord = c;
					}
				} else {
					consecutiveVertical = 0;
				}
			}
		}
		for (int i = -1; i < -k + 1; i--) {
			int row = coord.getRow() + i;
			int col = coord.getColumn();
			MyCoordinate c = new MyCoordinate(row, col);
			if (c.getRow() + i < 19 && c.getRow() + i >= 0) {
				if (b.pieceAt(c) == s) {
					consecutiveVertical++;
					if (consecutiveVertical >= k) {
						outputCoord = c;
					}
				} else {
					consecutiveVertical = 0;
				}
			}
		}
		return outputCoord;
	}
	
	private MyCoordinate checkSeqH(MyCoordinate coord, int k, Stone s) {
		MyCoordinate outputCoord = null;
		int consecutiveHorizontal = 0;
		for (int i = 1; i < k + 1; i++) {
			int row = coord.getRow();
			int col = coord.getColumn() + i;
			MyCoordinate c = new MyCoordinate(row, col);
			if (c.getColumn() + i < 19 && c.getColumn() + i >= 0) {
				if (b.pieceAt(c) == s) {
					consecutiveHorizontal++;
					if (consecutiveHorizontal >= k) {
						outputCoord = c;
					}
				} else {
					consecutiveHorizontal = 0;
				}
			}
		}
		for (int i = -1; i < -k - 1; i--) {
			int row = coord.getRow();
			int col = coord.getColumn() + i;
			MyCoordinate c = new MyCoordinate(row, col);
			if (c.getColumn() + i < 19 && c.getColumn() + i >= 0) {
				if (b.pieceAt(c) == s) {
					consecutiveHorizontal++;
					if (consecutiveHorizontal >= k) {
						outputCoord = c;
					}
				} else {
					consecutiveHorizontal = 0;
				}
			}
		}
		return outputCoord;
	}
	
	private MyCoordinate finishFiveV(MyCoordinate inputCoord) {
		MyCoordinate outputCoord = null;
		MyCoordinate testOutput1 = null;
		MyCoordinate testOutput2 = null;
		if (inputCoord.getRow() + 1 < 19 && inputCoord.getRow() + 1 >= 0) {
			testOutput1 = new MyCoordinate(inputCoord.getRow() + 1, inputCoord.getColumn());
		}
		if (inputCoord.getRow() - 4 < 19 && inputCoord.getRow() - 4 >= 0) {
			testOutput2 = new MyCoordinate(inputCoord.getRow() - 4, inputCoord.getColumn());
		}
		if (testOutput1 != null) {
			if (b.isEmpty(testOutput1)) {
				outputCoord = testOutput1;
			}
		}
		if (testOutput2 != null) {
			if (b.isEmpty(testOutput2)) {
				outputCoord = testOutput2;
			}
		}
		return outputCoord;
	}
	
	private MyCoordinate finishFiveH(MyCoordinate inputCoord) {
		MyCoordinate outputCoord = null;
		MyCoordinate testOutput1 = null;
		MyCoordinate testOutput2 = null;
		if (inputCoord.getColumn() + 1 < 19 && inputCoord.getColumn() + 1 >= 0) {
			testOutput1 = new MyCoordinate(inputCoord.getRow(), inputCoord.getColumn() + 1);
		}
		if (inputCoord.getColumn() - 4 < 19 && inputCoord.getColumn() - 4 >= 0) {
			testOutput2 = new MyCoordinate(inputCoord.getRow(), inputCoord.getColumn() - 4);
		}
		if (testOutput1 != null) {
			if (b.isEmpty(testOutput1)) {
				outputCoord = testOutput1;
			}
		}
		if (testOutput2 != null) {
			if (b.isEmpty(testOutput2)) {
				outputCoord = testOutput2;
			}
		}
		if (outputCoord != null) {
			System.out.printf("%d %d\n", outputCoord.getRow(), outputCoord.getColumn());
		}
		return outputCoord;
	}
	
	private MyCoordinate defendFourV (MyCoordinate inputCoord) {
		MyCoordinate outputCoord = null;
		MyCoordinate testOutput1 = null;
		MyCoordinate testOutput2 = null;
		if (inputCoord.getRow() + 1 < 19 && inputCoord.getRow() + 1 >= 0) {
			testOutput1 = new MyCoordinate(inputCoord.getRow() + 1, inputCoord.getColumn());	
		}
		if (inputCoord.getRow() - 3 < 19 && inputCoord.getRow() - 3 >= 0) { 
			testOutput2 = new MyCoordinate(inputCoord.getRow() - 3, inputCoord.getColumn());
		}
		if (b.isEmpty(testOutput1) && b.isEmpty(testOutput2)) {
			outputCoord = testOutput1;
		}
		return outputCoord;
	}
	
	private MyCoordinate defendFourH (MyCoordinate inputCoord) {
		MyCoordinate outputCoord = null;
		MyCoordinate testOutput1 = null;
		MyCoordinate testOutput2 = null;
		if (inputCoord.getColumn() + 1 < 19 && inputCoord.getColumn() + 1 >= 0) {
			testOutput1 = new MyCoordinate(inputCoord.getRow(), inputCoord.getColumn() + 1);
		}
		if (inputCoord.getColumn() - 3 < 19 && inputCoord.getColumn() - 3 >= 0) {
			testOutput2 = new MyCoordinate(inputCoord.getRow(), inputCoord.getColumn() - 3);
			System.out.println(inputCoord.getColumn() - 3);
		}
		if (testOutput1 != null) {
			if (b.isEmpty(testOutput1)) {
				outputCoord = testOutput1;
			}
		}
		if (testOutput2 != null) {
			if (b.isEmpty(testOutput2)) {
				outputCoord = testOutput2;
			}
		}
		return outputCoord;
	}

	private MyCoordinate makeFourH(MyCoordinate inputCoord) {
		if (inputCoord.getColumn() - 1 >= 0 && inputCoord.getColumn() + 4 < 19) {
			MyCoordinate testCoordinateW1 = new MyCoordinate(inputCoord.getRow(), inputCoord.getColumn() - 1);
			MyCoordinate testCoordinateE1 = new MyCoordinate(inputCoord.getRow(), inputCoord.getColumn() + 1);
			MyCoordinate testCoordinateE2 = new MyCoordinate(inputCoord.getRow(), inputCoord.getColumn() + 2);
			MyCoordinate testCoordinateE3 = new MyCoordinate(inputCoord.getRow(), inputCoord.getColumn() + 3);
			MyCoordinate testCoordinateE4 = new MyCoordinate(inputCoord.getRow(), inputCoord.getColumn() + 4);
			if (b.pieceAt(testCoordinateW1) == Stone.EMPTY &&
					b.pieceAt(testCoordinateE1) == playerColor &&
					b.pieceAt(testCoordinateE2) == playerColor &&
					b.pieceAt(testCoordinateE3) == playerColor &&
					b.pieceAt(testCoordinateE4) == Stone.EMPTY) {
				return inputCoord;
			}
		}	
		return null;
	}
	
	private MyCoordinate makeFourV(MyCoordinate inputCoord) {
		if (inputCoord.getRow() - 1 >= 0 && inputCoord.getRow() + 4 < 19) {
			MyCoordinate testCoordinateN1 = new MyCoordinate(inputCoord.getRow() - 1, inputCoord.getColumn());
			MyCoordinate testCoordinateS1 = new MyCoordinate(inputCoord.getRow() + 1, inputCoord.getColumn());
			MyCoordinate testCoordinateS2 = new MyCoordinate(inputCoord.getRow() + 2, inputCoord.getColumn());
			MyCoordinate testCoordinateS3 = new MyCoordinate(inputCoord.getRow() + 3, inputCoord.getColumn());
			MyCoordinate testCoordinateS4 = new MyCoordinate(inputCoord.getRow() + 4, inputCoord.getColumn());
			if (b.pieceAt(testCoordinateN1) == Stone.EMPTY &&
					b.pieceAt(testCoordinateS1) == playerColor &&
					b.pieceAt(testCoordinateS2) == playerColor &&
					b.pieceAt(testCoordinateS3) == playerColor &&
					b.pieceAt(testCoordinateS4) == Stone.EMPTY) {
				return inputCoord;
			}
		}	
		return null;
	}
}
