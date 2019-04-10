package course.oop.other;

import course.oop.other.exceptions.GameNotInProgressException;

public class BasicTicTacToe extends TicTacToe {
	
	public BasicTicTacToe(int desiredSize) {
		if (desiredSize < 3 || desiredSize > 5) throw new UnsupportedOperationException("At this time, there is no support for game size other than 3, 4, and 5.");
		this.size = desiredSize;
		resetGame();
	}
	    
    @Override
    public String getRules() {
        return "**************************************** RULES ****************************************\n" + 
        	   "Hello! welcome to Basic Tic-Tac-Toe!\nTic-Tac-Toe is a paper-and-pencil game for two players, X and O, who take" + 
        	   "turns marking the spaces in a 3×3 grid. The player who succeeds in placing three of" + 
        	   "their marks in a horizontal, vertical, or diagonal row wins the game.";
    }
    
    @Override
    public void resetGame() {
    	this.board = new BottomBoard(this.size);
    	updateStatus();
    }
    
	@Override
	public boolean attemptMove(Player currentPlayerObj, Coordinate pair) {
		if (status != GameStatus.ongoing) throw new GameNotInProgressException();
		if (!(pair instanceof OnePair)) throw new IllegalArgumentException(
				"Move must be an instance of OnePair, only one grid is viable at this depth.");
		OnePair move = (OnePair)pair;
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
		int row, col, iter = 0, max_iter=10000;
		do {
			row = randInt(0,size);
			col = randInt(0,size);
			iter++;
		} while(   !attemptMove(currentPlayerObj, new OnePair(row, col)) && iter<max_iter   );
		return true;
	}
}