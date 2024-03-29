package course.oop.other;

import javafx.scene.layout.GridPane;

public class UltTicTacToe extends TicTacToe<TopBoard> {

    /**
     * Default constructor
     */
    public UltTicTacToe() {
    }

    @Override
    public String getRules() {
        return "\n**************************************** RULES ****************************************\n" + 
        "Hello! welcome to Ultimate Tic-Tac-Toe!\nUltimate Tic-Tac-Toe is a board game composed of nine " + 
        "tic-tac-toe boards arranged in a 3-by-3 grid. Players take turns playing in the smaller tic-tac-toe boards " + 
        "until one of them wins in the larger tic-tac-toe board.\nEach small 3-by-3 tic-tac-toe board is referred to " +
        "as a local board, and the larger 3-by-3 board is referred to as the global board\nThe game starts with X " + 
        "playing wherever they want in any of the 81 empty spots. This move 'sends' their opponent to its relative " + 
        "location. For example, if X played in the top right square of their local board, then O needs to play next " + 
        "in the local board at the top right of the global board. O can then play in any one of the nine available " + 
        "spots in that local board, each move sending X to a different local board.\nIf a move is played so that it " + 
        "is to win a local board by the rules of normal tic-tac-toe, then the entire local board is marked as a " + 
        "victory for the player in the global board.\nOnce the outcome of a local board is decided (win or draw), " + 
        "no more moves may be played in that board. If a player is sent to such a board, then that player may play " + 
        "in any other board.\nGame play ends when either a player wins the global board or there are no legal moves " + 
        "remaining, in which case the game is a draw.";
    }

	@Override
	public boolean attemptMove(Player currentPlayerObj, Pair pair) {
		// Auto-generated method stub
		return false;
	}

	@Override
	public boolean attemptMove(Player currentPlayerObj) {
		// Auto-generated method stub
		return false;
	}

	@Override
	public String getDisplay() {
		// Auto-generated method stub
		return null;
	}

	@Override
	protected GameStatus updateStatus(Pair pair) {
		// Auto-generated method stub
		//iterate over bottomboards and check the status
		return null;
	}

	@Override
	public void resetGame() {
		// Auto-generated method stub
		
	}

	@Override
	public void quitGame() {
		// Auto-generated method stub
		
	}

	@Override
	public GridPane getGuiDisplay() {
		// Auto-generated method stub
		return null;
	}
}