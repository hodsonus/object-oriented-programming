package course.oop.other;

import course.oop.other.exceptions.GameNotInProgressException;

public class BasicTicTacToe extends TicTacToe<BottomBoard> {

    public BasicTicTacToe() {
    	board = new BottomBoard();
    	updateStatus();
    }
    
    @Override
    public void resetGame() {
    	board = new BottomBoard();
    	updateStatus();
    }
    
    @Override
    public void quitGame() {
		if (this.status == GameStatus.quit) throw new GameNotInProgressException();
       	board.quit();
       	updateStatus();
    }

    @Override
    public String getRules() {
        return "**************************************** RULES ****************************************\n" + 
        	   "Tic-tac-toe is a paper-and-pencil game for two players, X and O, who take" + 
        	   "turns marking the spaces in a 3×3 grid. The player who succeeds in placing three of" + 
        	   "their marks in a horizontal, vertical, or diagonal row wins the game.";
    }

	@Override
	public boolean attemptMove(Player currentPlayerObj, Pair pair) {
		if (status != GameStatus.ongoing) throw new GameNotInProgressException();
		boolean validMove = board.attemptMove(currentPlayerObj, pair);
		if (validMove) updateStatus(pair);
		return validMove;
	}

	@Override
	protected GameStatus updateStatus(Pair pair) {
		this.status = board.updateStatus(pair);
		winningPlayer = board.getWinningPlayer();
		return status;
	}
	
	protected GameStatus updateStatus() {
		this.status = board.getStatus();
		winningPlayer = board.getWinningPlayer();
		return status;
	}

	@Override
	public boolean attemptMove(Player currentPlayerObj) {
		int row, col, iter = 0, max_iter=10000;
		do {
			row = randInt(0,2);
			col = randInt(0,2);
			iter++;
		} while(   !attemptMove(currentPlayerObj, new OnePair(row, col)) && iter<max_iter   );
		return true;
	}

	@Override
	public String getDisplay() {
		if (board == null) throw new GameNotInProgressException();
		return board.toString();
	} 
	
	public static int randInt(int min, int max) {
	    return min + (int)(Math.random() * ((max - min) + 1));
	}
}