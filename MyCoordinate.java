
public class MyCoordinate implements Coordinate {

	int row;
	int col;
	
	public MyCoordinate(int input_row, int input_col) {
		row = input_row;
		col = input_col;
	}
	@Override
	public int getRow() {
		return row;
	}

	@Override
	public int getColumn() {
		// TODO Auto-generated method stub
		return col;
	}

}
