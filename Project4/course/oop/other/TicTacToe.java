package course.oop.other;

import course.oop.other.exceptions.GameNotInProgressException;
import javafx.scene.layout.GridPane;

public abstract class TicTacToe {

	protected Board board;
    protected GameStatus status;
    protected Player winningPlayer;
    protected int size;

    public boolean isInProgress() {
		return status == GameStatus.ongoing;
	}
 
    public boolean isVictorious(Player didTheyWin) {
    	if (didTheyWin == null && this.winningPlayer == null) return true;
    	if (didTheyWin != null) return didTheyWin.equals(this.winningPlayer);
    	else return false;
    }
    
    public GameStatus getStatus() {
    	return this.status;
    }
    
    /* the game is over if player one won, player two won, the game ended in a draw, or was quit */
    public boolean isOver() {
    	return status == GameStatus.victory || status == GameStatus.draw || status == GameStatus.quit;
    }
        
	/* you should be able to quit the game ONLY if it is ongoing. if it is before the game or 
	 * after the game this method makes no sense */
    public void quitGame() {
		if (this.status == GameStatus.quit) throw new GameNotInProgressException();
       	board.quit();
       	updateStatus();
    }
    
    protected GameStatus updateStatus(Coordinate move) {
		this.status = board.updateStatus(move);
		winningPlayer = board.getWinningPlayer();
		return status;
	}
    
	protected GameStatus updateStatus() {
		this.status = board.getStatus();
		winningPlayer = board.getWinningPlayer();
		return status;
	}
	
	public static int randInt(int min, int max) {
	    return min + (int)(Math.random() * ((max - min) + 1));
	}
	
	public GridPane getGuiDisplay() {
		return board.getGuiDisplay(true);
	}	
	
	public String getDisplay() {
		if (board == null) throw new GameNotInProgressException();
		return board.toString();
	} 
	
	public boolean attemptRandomMove(Player currentPlayerObj) {
		return attemptMove(currentPlayerObj);
	}
    
    public abstract void resetGame(); // you should be able to reset the game if the game has ended or if the game is in progress
    public abstract boolean attemptMove(Player currentPlayerObj, Coordinate move);
	public abstract boolean attemptMove(Player currentPlayerObj);
    public abstract String getRules();
}