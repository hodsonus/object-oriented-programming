
import java.util.*;

/**
 * 
 */
public abstract class TicTacToe<E> {

    /**
     * Default constructor
     */
    public TicTacToe() {
    }

    /**
     * 
     */
    private boolean player1Turn;

    /**
     * 
     */
    private E board;

    /**
     * 
     */
    private int secPerTurn;

    /**
     * 
     */
    private int randomizeNthTurn;

    /**
     * 
     */
    private Player player1;

    /**
     * 
     */
    private Player player2;

    /**
     * @return
     */
    private boolean oneWon() {
        // TODO implement here
        return false;
    }

    /**
     * @return
     */
    private boolean twoWon() {
        // TODO implement here
        return false;
    }

    /**
     * @return
     */
    private boolean gameOver() {
        // TODO implement here
        return false;
    }

    /**
     * @return
     */
    private Pair querieMove() {
        // TODO implement here
        return null;
    }

    /**
     * 
     */
    private void executeMove() {
        // TODO implement here
    }

    /**
     * 
     */
    public void startGame() {
        // TODO implement here
    }

    /**
     * 
     */
    public void resetGame() {
        // TODO implement here
    }

    /**
     * 
     */
    public void quitGame() {
        // TODO implement here
    }

    /**
     * 
     */
    private void playAnimation() {
        // TODO implement here
    }

    /**
     * @return
     */
    public String getRules() {
        // TODO implement here
        return "";
    }

}