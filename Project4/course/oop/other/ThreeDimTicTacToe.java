package course.oop.other;

import course.oop.other.exceptions.GameNotInProgressException;

public class ThreeDimTicTacToe extends TicTacToe {
	
    public ThreeDimTicTacToe(int desiredSize) {
		if (desiredSize < 3 || desiredSize > 5) throw new UnsupportedOperationException("At this time, there is no support for game size other than 3, 4, and 5.");
    	this.size = desiredSize;
    	resetGame();
    }

    @Override
    public String getRules() {
        return "\n**************************************** RULES ****************************************\n" + 
          "Hello! welcome to 3D Tic-Tac-Toe!\n3D Tic-Tac-Toe, also known by the trade name Qubic, is an abstract "
        + "strategy board game, generally for two players. It is similar in concept to traditional tic-tac-toe "
        + "but is played in a cubical array of cells, usually 4x4x4. Players take turns placing their markers in "
        + "blank cells in the array. The first player to achieve four of their own markers in a row wins. The winning "
        + "row can be horizontal, vertical, or diagonal on a single board as in regular tic-tac-toe, or vertically "
        + "in a column, or a diagonal line through four boards.";
    }
    
	@Override
	public void resetGame() {
		this.board = new ThreeDimBoard(size);
		updateStatus();
	}    
 
	@Override
	public boolean attemptMove(Player currentPlayerObj, Coordinate pair) {
		if (status != GameStatus.ongoing) throw new GameNotInProgressException();
		if (!(pair instanceof Triple)) throw new IllegalArgumentException(
				"Move must be an instance of Triple, only one grid is viable at this depth.");
		Triple move = (Triple)pair;
		boolean validMove = board.attemptMove(currentPlayerObj, move);
		if (validMove){
			updateStatus(move);
//			System.out.println(board);
		}
		return validMove;
	}

	@Override
	public boolean attemptMove(Player currentPlayerObj) {
		if (status != GameStatus.ongoing) throw new GameNotInProgressException();
		int row, col, dep, iter = 0, max_iter=10000;
		do {
			row = randInt(0,size-1);
			col = randInt(0,size-1);
			dep = randInt(0,size-1);
			iter++;
		} while(   !attemptMove(currentPlayerObj, new Triple(row, col, dep)) && iter<max_iter   );
		return true;
	}
}