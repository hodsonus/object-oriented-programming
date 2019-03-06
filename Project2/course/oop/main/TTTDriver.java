package course.oop.main;

import course.oop.controller.*;
import course.oop.other.BottomBoard;

public class TTTDriver {

	private static void checkForInvalidLocation(boolean isSelectionValid) {
		if(!isSelectionValid) {
			System.out.println("Invalid location selected by player");
		}
	}
	
	public static void sampleTestCase() {
		TTTControllerImpl ticTacToe = new TTTControllerImpl();
		String player_1 = "Ashley";
		String player_2 = "James";
		ticTacToe.createPlayer(player_1, "O", 1);
		ticTacToe.createPlayer(player_2, "X", 2);
		boolean isSelectionValid = true;
		
		// initialize
		ticTacToe.startNewGame(2, 0);
		System.out.println(ticTacToe.getGameDisplay());
		
		//play game
		isSelectionValid = ticTacToe.setSelection(0, 0, 2);
		checkForInvalidLocation(isSelectionValid);
		System.out.println(ticTacToe.getGameDisplay());
		isSelectionValid = ticTacToe.setSelection(0, 2, 1);
		checkForInvalidLocation(isSelectionValid);
		System.out.println(ticTacToe.getGameDisplay());
		isSelectionValid = ticTacToe.setSelection(1, 0, 2);
		checkForInvalidLocation(isSelectionValid);
		System.out.println(ticTacToe.getGameDisplay());
		isSelectionValid = ticTacToe.setSelection(1, 2, 1);
		checkForInvalidLocation(isSelectionValid);
		System.out.println(ticTacToe.getGameDisplay());
		isSelectionValid = ticTacToe.setSelection(2, 0, 2);
		checkForInvalidLocation(isSelectionValid);
		System.out.println(ticTacToe.getGameDisplay());
		
		//determine winner
		int winner = ticTacToe.determineWinner();
		if(winner==2) {
			System.out.println(player_2 + " won the game!!");
		}else {
			System.out.println("Failed Test Case");
		}
	}
	
	public static void myMain() {
		BottomBoard uut = new BottomBoard();
		System.out.println(uut);
	}
	
	public static void main(String[] args) {
		sampleTestCase();
	}
	
}