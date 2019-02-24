package course.oop.other;

/**
 * 
 */
public class BasicTicTacToe extends TicTacToe<BottomBoard> {

    /**
     * Default constructor
     */
    public BasicTicTacToe() {
    }

    @Override
    public String getRules() {
        return "\n**************************************** RULES ****************************************\n" + 
        "Tic-tac-toe is a paper-and-pencil game for two players, X and O, who take" + 
        "turns marking the spaces in a 3×3 grid. The player who succeeds in placing three of" + 
        "their marks in a horizontal, vertical, or diagonal row wins the game.";
        
    }
    @Override
    public String play() {
        return "";
    }
}