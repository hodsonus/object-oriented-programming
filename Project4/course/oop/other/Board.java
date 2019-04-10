package course.oop.other;

import course.oop.other.exceptions.GameNotInProgressException;
import javafx.scene.layout.GridPane;

public abstract class Board {
	
    protected GameStatus status;
    protected Player winningPlayer;
    protected int size;
    
    public Board(int desiredSize) {
    	this.size = desiredSize;
    	this.winningPlayer = null;
    	resetBoard();
    }
    
    public Player getWinningPlayer() {
    	return winningPlayer;
    }
    	
	protected boolean isValidPos(int pos) {
		return pos >= 0 && pos < size;
	}
	
	public void quit() {
		if (this.status == GameStatus.quit) throw new GameNotInProgressException();
		this.status = GameStatus.quit;
	}
	
	public String draw() {
		return toString();
	}
    
	public GameStatus getStatus() {
		return this.status;
	}
	
	protected abstract GameStatus updateStatus(Coordinate lastMove);
	public abstract void resetBoard();
	public abstract boolean attemptMove(Player player, Coordinate move);
	public abstract GridPane getGuiDisplay(boolean absoluteSquares);
}