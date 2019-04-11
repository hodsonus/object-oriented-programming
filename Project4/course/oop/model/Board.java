package course.oop.model;

import course.oop.exceptions.GameNotInProgressException;
import course.oop.other.Coordinate;
import course.oop.other.GameStatus;
import course.oop.players.Player;
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
    
    public Player getPlayer() {
    	return winningPlayer;
    }
    	
	protected boolean isValidPos(int pos) {
		return pos >= 0 && pos < size;
	}
	
	public void quit() {
		if (this.status == GameStatus.quit) throw new GameNotInProgressException("Cannot quit a game that has been quit.");
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
	public abstract GridPane getGuiDisplay(boolean absoluteSquares, boolean colorSquares);
}