/**
 * You can and should have other members (properties or functions) 
 * in your controller, but the functions provided in 
 * TTTControllerInterface.java are required.
 * 
 * Create a class called TTTControllerImpl.java and implement the 
 * methods in this interface.
 * 
 */

package course.oop.controller;

import course.oop.other.Coordinate;
import course.oop.other.GameType;
import course.oop.players.Player;

public interface TTTControllerInterface {
	
	/**
	 * Initialize or reset game board. Set each entry back to a default value.
	 * 
	 * @param timeoutInSecs     Allow for a user's turn to time out. Any
	 * 						    int <=0 means no timeout.  Any int > 0 means to time out
	 * 						    in the given number of seconds.
	 * @param typeToInstantiate Must be valid and non null. The type of game that is instantiated
	 * 							will be of this type.
	 * @param sizeOfGame        Passes the size of the board to the constructor - the constructor
	 * 							will throw an exception if it is not a valid size.
	 */
	void startNewGame(int timeoutInSecs, GameType typeToInstantiate, int sizeOfGame);


	/**
	 * Create a player with specified username, marker, 
	 * and player number (either 1 or 2) 
	 * 
	 * @param username
	 * @param marker
	 * @param playerNum
	 * 
	 * @return 
	 */
	Player createPlayer(String username, String marker, int playerNum);
	
	/**
	 * Allow user to specify location for marker.  
	 * Return true if the location is valid and available.
	 * 
	 * @param pos           Must be valid. The exact type of game that is instantiated will
	 * 						throw exceptions if Coordinate is not the proper type of move 
	 * @param currentPlayer Must be valid. 1 = player1; 2 = player2
	 * 
	 * @return if it was valid or not
	 */
	boolean setSelection(Coordinate pos, int currentPlayer);
	
	/**
	 * Determines if there is a winner and returns the following:
	 * 
	 * 0=no winner / game in progress / not all spaces have been selected; 
	 * 1=player1; 
	 * 2=player2; 
	 * 3= tie/no more available locations
	 * 
	 * @return
	 */
	int determineWinner();

	/**
	 * Return a printable display of current selections.
	 * Shows 3x3 (or nxn) board with each players marker.
	 * Alias for toString()
	 * 
	 * @return
	 */
	String getGameDisplay();
}