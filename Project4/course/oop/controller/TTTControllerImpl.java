package course.oop.controller;

import course.oop.other.*;
import course.oop.other.exceptions.GameInProgressException;
import course.oop.other.exceptions.TurnTimeoutException;
import javafx.scene.layout.GridPane;

public class TTTControllerImpl implements TTTControllerInterface {
	
	private Player player1, player2;
	private TicTacToe game;
	private int timeoutInSecs;
	private long startTime;
    private ExistingPlayers exP;
	
	public TTTControllerImpl() {
		this.player1 = null;
		this.player2 = null;
		this.game = null;
		this.exP = ExistingPlayers.getInstance();
	}

	/**
	 * Initialize or reset game board. Set each entry back to a default value.
	 * 
	 * @param numPlayers Must be valid. 2 = two human players, 1 = human plays against computer
	 * @param timeoutInSecs Allow for a user's turn to time out. Any
	 * 						int <=0 means no timeout.  Any int > 0 means to time out
	 * 						in the given number of seconds.
	 */
	@Override
	public void startNewGame(int timeoutInSecs, GameType typeToInstantiate, int sizeOfGame) {
		
		this.timeoutInSecs = timeoutInSecs;
		fillEmptyPlayers();
				
		if (typeToInstantiate == GameType.Ultimate){
			game = new UltTicTacToe(sizeOfGame);
		}
		else if (typeToInstantiate == GameType.Basic) {
			game = new BasicTicTacToe(sizeOfGame);
		}
		else if (typeToInstantiate == GameType.ThreeDim) {
			game = new ThreeDimTicTacToe(sizeOfGame);
		}
		
		writeStartTime();
    }
	
	public void quitGame() {
		game.quitGame();
		player1.incrDraws();
		player2.incrDraws();
	}
	
	public void resetGame() {
		game.resetGame();
		player1.incrDraws();
		player2.incrDraws();
	}
	
	private void fillEmptyPlayers() {
		if (player1 == null) player1 = new AI("SuperDuperAIOne", "@");
		if (player2 == null) player2 = new AI("SuperDuperAITwo", "$");
	}
	
	private boolean validNumPlayer(int numPlayers) {
		return numPlayers == 1 || numPlayers == 2;
	}

	/**
	 * Create a player with specified username, marker, 
	 * and player number (either 1 or 2) 
	 * 
	 * @param username
	 * @param marker
	 * @param playerNum
	 */
	@Override
	public Player createPlayer(String username, String marker, int playerNum) {
		
		if (game != null && game.getStatus() == GameStatus.ongoing) throw new GameInProgressException();
		if (!validNumPlayer(playerNum)) throw new IllegalArgumentException();
		
		if (playerNum == 1)  {
			player1 = new Person(username, marker);
			exP.add(player1);
			exP.savePlayers();
			return player1;
		}
		else  {
			player2 = new Person(username, marker);
			exP.add(player2);
			exP.savePlayers();
			return player2;
		}
	}
	
	public Player createAI(String username, String marker, int playerNum) {
		
		if (game != null && game.getStatus() == GameStatus.ongoing) throw new GameInProgressException();
		if (!validNumPlayer(playerNum)) throw new IllegalArgumentException();
		
		if (playerNum == 1) {
			player1 = new AI(username, marker);
			exP.add(player1);
			exP.savePlayers();
			return player1;
		}
		else {
			player2 = new AI(username, marker);
			exP.add(player2);
			exP.savePlayers();
			return player2;
		}
	}
	
	public Player useExistingPlayer(Player a, int playerNum) {
		if (game != null && game.getStatus() == GameStatus.ongoing) throw new GameInProgressException();
		if (!validNumPlayer(playerNum)) throw new IllegalArgumentException();

		if (playerNum == 1) {
			player1 = a;
		}
		else {
			player2 = a;
		}
		return a;
	}

	/**
	 * Allow user to specify location for marker.  
	 * Return true if the location is valid and available.
	 * 
	 * @param row Must be valid. 0,1,2
	 * @param col Must be valid. 0,1,2
	 * @param currentPlayer Must be valid. 1 = player1; 2 = player2
	 * @return
	 */
	@Override
	public boolean setSelection(Coordinate pos, int currentPlayer) {
		if (!validUserTurnLength()) throw new TurnTimeoutException("User took too long to provide input to program.");
		if (!validNumPlayer(currentPlayer)) throw new IllegalArgumentException();
		
		Player currentPlayerObj;
		
		if (currentPlayer == 1) {
			if (player1 instanceof AI) throw new InvalidAIOperation();
			currentPlayerObj = player1;
		}
		else { //currentPlayer == 2
			if (player2 instanceof AI) throw new InvalidAIOperation();
			currentPlayerObj = player2;
		}

		return game.attemptMove(currentPlayerObj, pos);
	}
	
	public void makeAISelection(int currentPlayer) {
		if (!validUserTurnLength()) throw new TurnTimeoutException("User took too long to provide input to program.");
		if (!validNumPlayer(currentPlayer)) throw new IllegalArgumentException();
		
		Player currentPlayerObj;
		
		if (currentPlayer == 1) {
			if (player1 instanceof Person) throw new InvalidPersonOperation();
			currentPlayerObj = player1;
		}
		else { //currentPlayer == 2
			if (player2 instanceof Person) throw new InvalidPersonOperation();
			currentPlayerObj = player2;
		}
		writeStartTime();
		game.attemptMove(currentPlayerObj);
	}

	/**
	 * Determines if there is a winner and returns the following:
	 * 
	 * 0=no winner / game in progress / not all spaces have been selected; 
	 * 1=player1; 
	 * 2=player2; 
	 * 3=tie/no more available locations
	 * 
	 * @return
	 */
	@Override
	public int determineWinner() {
		if (!game.isOver()) return 0;
		if (game.isOver()) {
			if (game.isVictorious(player1)) return 1;
			if (game.isVictorious(player2)) return 2;
		}
		return 3;
	}

	/**
	 * Return a printable display of current selections.
	 * Shows 3x3 (or nxn) board with each players marker.
	 * 
	 * @return
	 */
	@Override
	public String getGameDisplay() {
		return game.getDisplay();
	}
	
	public void writeStartTime() {
		startTime = System.nanoTime();
	}
	
	private boolean validUserTurnLength() {
		if (timeoutInSecs <= 0) return true;
		return (System.nanoTime() - startTime)/(Math.pow(10, 9)) < timeoutInSecs;
	}

	public GridPane getGUIDisplay() {
		return game.getGuiDisplay();
	}

	public Player getPlayer(int i) {
		if (!validNumPlayer(i)) throw new IllegalArgumentException();
		if (i == 1) return player1;
		return player2;
	}
}
