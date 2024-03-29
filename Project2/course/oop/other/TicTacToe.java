package course.oop.other;

public abstract class TicTacToe<E> {

    protected E board;
    protected GameStatus status;
    protected Player winningPlayer;

    public TicTacToe() {}

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
    
	/* you should be able to reset the game if the game has ended or if the game is in progress */
    public abstract void resetGame();
    
	/* you should be able to quit the game ONLY if it is ongoing. if it is before the game or 
	 * after the game this method makes no sense */
    public abstract void quitGame();
    
    protected abstract GameStatus updateStatus(Pair pair);
    public abstract boolean attemptMove(Player currentPlayerObj, Pair pair);
	public abstract boolean attemptMove(Player currentPlayerObj);
    public abstract String getRules();
	public abstract String getDisplay();
}