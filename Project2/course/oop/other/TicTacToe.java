package course.oop.other;

import java.util.Scanner;

public abstract class TicTacToe<E> {

    private Scanner sc;

    public TicTacToe() {
//        sc = new Scanner(System.in);
    }

    /**
     * 
     */
    protected boolean player1Turn;

    /**
     * 
     */
    protected E board;

    /**
     * 
     */
    protected int secPerTurn;

    /**
     * 
     */
    protected int randomizeNthTurn;

    /**
     * @return
     */
    public boolean oneWon() {
        // TODO implement here
        return false;
    }

    /**
     * @return
     */
    public boolean twoWon() {
        // TODO implement here
        return false;
    }

    /**
     * @return
     */
    public boolean isOver() {
        // TODO implement here
        return false;
    }

    /**
     * @return
     */
    protected Pair querieMove() {
        // TODO implement here
        return null;
    }

    /**
     * 
     */
    protected void executeMove() {
        // TODO implement here
    }

    /**
     * 
     */
    public void startGame() {
        System.out.println("Would you like to see the rules?");
        String response = sc.nextLine().toLowerCase();
        if (response.equals("yes")) {
            System.out.println(getRules());
        }
        else if (response.equals("no")) {
            System.out.println("Okay, no problem!");
        }
        else {
            System.out.println("I didn't really understand that, but we're going to keep on going...");
        }
        this.play();
    }

    /**
     * 
     */
    protected void resetGame() {
        // TODO implement here
    }

    /**
     * 
     */
    protected void quitGame() {
        // TODO implement here
    }

    /**
     * 
     */
    protected void playAnimation() {
        // TODO implement here
    }

    /**
     * @return the String representing the rules
     */
    public abstract String getRules();

    protected abstract String play();

	public String getDisplay() {
		// TODO Auto-generated method stub
		return null;
	}
}