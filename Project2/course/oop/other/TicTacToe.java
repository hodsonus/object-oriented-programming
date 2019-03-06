package course.oop.other;

import java.util.Scanner;

import course.oop.other.exceptions.GameInProgressException;
import course.oop.other.exceptions.GameNotStartedException;

public abstract class TicTacToe<E> {

    private Scanner sc;
    protected E board;
    protected GameStatus status;
    protected Player winningPlayer;

    public TicTacToe() {
        this(new Scanner(System.in));
    }
    
    public TicTacToe(Scanner sc) {
    	this.sc = sc;
    	this.status = GameStatus.before;
    }

    public boolean isInProgress() {
		return status == GameStatus.ongoing;
	}
 
    public boolean isVictorious(Player didTheyWin) {
    	if (didTheyWin == null && this.winningPlayer == null) return true;
    	if (didTheyWin != null) return didTheyWin.equals(this.winningPlayer);
    	else return false;
    }
    
    /* the game is over if player one won, player two won, the game ended in a draw, or was quit */
    public boolean isOver() {
    	return status == GameStatus.victory || status == GameStatus.draw || status == GameStatus.quit;
    }
    
    public void startGame() {
    	if (status != GameStatus.before) throw new GameInProgressException();
//        System.out.println("Would you like to see the rules?");
//        String response = sc.nextLine().toLowerCase();
//        if (response.equals("yes")) {
//            System.out.println(getRules());
//        }
//        else if (response.equals("no")) {
//            System.out.println("Okay, no problem!");
//        }
//        else {
//            System.out.println("I didn't really understand that, but we're going to keep on going...");
//        }
    	status = GameStatus.ongoing;
    }    
    
	/* you should be able to reset the game if the game has ended or if the game is in progress */
    protected void resetGame() {
    	if (status == GameStatus.before) throw new GameInProgressException();
        status = GameStatus.before;
    }
    
	/* you should be able to quit the game ONLY if it is ongoing. if it is before the game or 
	 * after the game this method makes no sense */
    protected void quitGame() {
    	if (status != GameStatus.ongoing) throw new GameNotStartedException();
    	status = GameStatus.quit;
    }
    
    
    protected abstract GameStatus updateStatus(Pair pair);
    public abstract boolean attemptMove(Player currentPlayerObj, Pair pair);
	public abstract boolean attemptMove(Player currentPlayerObj);
    public abstract String getRules();
	public abstract String getDisplay();
}