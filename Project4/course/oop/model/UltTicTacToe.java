package course.oop.model;

import course.oop.exceptions.GameNotInProgressException;
import course.oop.other.Coordinate;
import course.oop.other.GameStatus;
import course.oop.other.OnePair;
import course.oop.other.TwoPair;
import course.oop.players.Player;

public class UltTicTacToe extends TicTacToe {

    public UltTicTacToe(int desiredSize) {
		if (desiredSize != 3) throw new UnsupportedOperationException("At this time, there is no support for game size other than 3.");
		this.size = desiredSize;
		resetGame();
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
	public void resetGame() {
		this.board = new TopBoard(this.size);
		updateStatus();
	}    
 
	@Override
	public boolean attemptMove(Player currentPlayerObj, Coordinate pair) {
		
		if (status != GameStatus.ongoing) throw new GameNotInProgressException("Cannot attempt a move on a game that is not ongoing.");
		if (!(pair instanceof TwoPair)) throw new IllegalArgumentException(
				"Move must be an instance of TwoPair, only two grids are viable at this depth.");
		TwoPair bothMoves = (TwoPair)pair;
		boolean validMove = board.attemptMove(currentPlayerObj, bothMoves);
		if (validMove) {
			updateStatus(bothMoves.pair2);
		}
		
		return validMove;
	}

	@Override
	public boolean attemptMove(Player currentPlayerObj) {
		if (status != GameStatus.ongoing) throw new GameNotInProgressException("Cannot attempt a move on a game that is not ongoing.");
		int outerRow, outerCol, innerRow, innerCol, maxIter = 50000;
		int iter = 0;
		do {
			outerRow = randInt(0,size-1);
			outerCol = randInt(0,size-1);
			innerRow = randInt(0,size-1);
			innerCol = randInt(0,size-1);
			iter++;
		} while(   !attemptMove(currentPlayerObj, new TwoPair(new OnePair(outerRow, outerCol), new OnePair(innerRow, innerCol))) && iter<maxIter  );
		return true;
	}
}