package course.oop.main;

import java.util.Scanner;
import course.oop.controller.*;
import course.oop.other.exceptions.InvalidMarkerException;
import course.oop.other.exceptions.TurnTimeoutException;

public class TTTDriver {

	private static void checkForInvalidLocation(boolean isSelectionValid) {
		if (!isSelectionValid) {
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

		// play game
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

		isSelectionValid = ticTacToe.setSelection(1, 1, 2);
		checkForInvalidLocation(isSelectionValid);
		System.out.println(ticTacToe.getGameDisplay());

		// determine winner
		int winner = ticTacToe.determineWinner();
		if (winner == 2) {
			System.out.println(player_2 + " won the game!!");
		} else {
			System.out.println("Failed Test Case");
		}
	}

	public static void myMain() {
		Scanner sc = new Scanner(System.in);
		final String outsideGameOptions = "1. Set player 1.\n" + "2. Set player 2.\n" + "3. Start new game.\n"
				+ "4. Quit the program.\n" + "Input the number of the choice that you desire.";
		final String inGameOptions = "1. Play turn.\n" + "2. Reset Game.\n" + "3. Quit Game.";
		final String ticTacToeMessage = "@@@@@@@  @@@   @@@@@@@     @@@@@@@   @@@@@@    @@@@@@@     @@@@@@@   @@@@@@   @@@@@@@@\n" +
								   "@@@@@@@  @@@  @@@@@@@@     @@@@@@@  @@@@@@@@  @@@@@@@@     @@@@@@@  @@@@@@@@  @@@@@@@@\n" + 
								   "  @@!    @@!  !@@            @@!    @@!  @@@  !@@            @@!    @@!  @@@  @@!     \n" +
								   "  !@!    !@!  !@!            !@!    !@!  @!@  !@!            !@!    !@!  @!@  !@!     \n" +
								   "  @!!    !!@  !@!            @!!    @!@!@!@!  !@!            @!!    @!@  !@!  @!!!:!  \n" +
								   "  !!!    !!!  !!!            !!!    !!!@!!!!  !!!            !!!    !@!  !!!  !!!!!:  \n" + 
								   "  !!:    !!:  :!!            !!:    !!:  !!!  :!!            !!:    !!:  !!!  !!:     \n" + 
								   "  :!:    :!:  :!:            :!:    :!:  !:!  :!:            :!:    :!:  !:!  :!:     \n" + 
								   "   ::     ::   ::: :::        ::    ::   :::   ::: :::        ::    ::::: ::   :: ::::\n" + 
								   "    :     :     :: :: :        :      :   : :   :: :: :        :      : :  :   : :: ::\n";
		final String victoryMessage = "@@@  @@@  @@@   @@@@@@@  @@@@@@@   @@@@@@   @@@@@@@   @@@ @@@  \n" +
								"@@@  @@@  @@@  @@@@@@@@  @@@@@@@  @@@@@@@@  @@@@@@@@  @@@ @@@  \n" +
								"@@!  @@@  @@!  !@@         @@!    @@!  @@@  @@!  @@@  @@! !@@  \n" +
								"!@!  @!@  !@!  !@!         !@!    !@!  @!@  !@!  @!@  !@! @!!  \n" +
								"@!@  !@!  !!@  !@!         @!!    @!@  !@!  @!@!!@!    !@!@!   \n" +
								"!@!  !!!  !!!  !!!         !!!    !@!  !!!  !!@!@!      @!!!   \n" +
								":!:  !!:  !!:  :!!         !!:    !!:  !!!  !!: :!!     !!:    \n" +
								" ::!!:!   :!:  :!:         :!:    :!:  !:!  :!:  !:!    :!:    \n" +
								"  ::::     ::   ::: :::     ::    ::::: ::  ::   :::     ::    \n" +
								"   :       :     :: :: :     :      : :  :    :   : :     :     ";
		final String drawMessage = "@@@@@@@   @@@@@@@    @@@@@@   @@@  @@@  @@@  \n" +
							 "@@@@@@@@  @@@@@@@@  @@@@@@@@  @@@  @@@  @@@  \n" +
							 "@@!  @@@  @@!  @@@  @@!  @@@  @@!  @@!  @@!  \n" +
							 "!@!  @!@  !@!  @!@  !@!  @!@  !@!  !@!  !@!  \n" +
							 "@!@  !@!  @!@!!@!   @!@!@!@!  @!!  !!@  @!@  \n" +
							 "!@!  !!!  !!@!@!    !!!@!!!!  !@!  !!!  !@!  \n" +
							 "!!:  !!!  !!: :!!   !!:  !!!  !!:  !!:  !!:  \n" +
							 ":!:  !:!  :!:  !:!  :!:  !:!  :!:  :!:  :!:  \n" +
							 ":::: ::   ::   :::  ::   :::   :::: :: :::   \n" +
							 ":: : :    :    : :   :   : :    :: :  : :    ";
		final String welcomeMessage = "@@@  @@@  @@@  @@@@@@@@  @@@        @@@@@@@   @@@@@@   @@@@@@@@@@   @@@@@@@@  \n" +
									  "@@@  @@@  @@@  @@@@@@@@  @@@       @@@@@@@@  @@@@@@@@  @@@@@@@@@@@  @@@@@@@@  \n" +
									  "@@!  @@!  @@!  @@!       @@!       !@@       @@!  @@@  @@! @@! @@!  @@!       \n" +
									  "!@!  !@!  !@!  !@!       !@!       !@!       !@!  @!@  !@! !@! !@!  !@!       \n" +
									  "@!!  !!@  @!@  @!!!:!    @!!       !@!       @!@  !@!  @!! !!@ @!@  @!!!:!    \n" +
									  "!@!  !!!  !@!  !!!!!:    !!!       !!!       !@!  !!!  !@!   ! !@!  !!!!!:    \n" +
									  "!!:  !!:  !!:  !!:       !!:       :!!       !!:  !!!  !!:     !!:  !!:       \n" +
									  ":!:  :!:  :!:  :!:        :!:      :!:       :!:  !:!  :!:     :!:  :!:       \n" +
									  " :::: :: :::    :: ::::   :: ::::   ::: :::  ::::: ::  :::     ::    :: ::::  \n" +
									  "   :: :  : :    : :: ::   : :: : :   :: :: :   : :  :    :      :    : :: ::  ";
		String choice;
		TTTControllerImpl ticTacToe = new TTTControllerImpl();
		;
		boolean player1isAI = true, player2isAI = true;
		
		System.out.println("\n" + ticTacToeMessage);

		System.out.println(
				"Welcome to Tic Tac Toe! Please read the following prompt and select a choice from the menu.\nThe choices are as follows:");
		while (true) {
			System.out.println(outsideGameOptions);
			System.out.print("choice: ");
			choice = sc.nextLine();
			if (choice.equals("1")) { // 1 - Set player 1.
				player1isAI = setPlayerX(1, sc, ticTacToe);
			} else if (choice.equals("2")) { // 2 - Set player 2.
				player2isAI = setPlayerX(2, sc, ticTacToe);
			} else if (choice.equals("3")) { // 3 - Start new game.
				String timerChoice;
				do {
					System.out.println("Would you like to play with a timer? Input \"yes\" or \"no\".");
					timerChoice = sc.nextLine();
				} while (!timerChoice.equals("yes") && !timerChoice.equals("no"));
				Integer timerLength = null;
				if (timerChoice.equals("yes")) {
					do {
						System.out.println("Input a valid integer to be the length of the timer.");
						try {
							timerLength = Integer.valueOf(sc.nextLine());
						} catch (NumberFormatException e) {
							timerLength = null;
							System.out.println("That was not a valid integer.");
						}

					} while (timerLength == null);
				} else {
					timerLength = 0;
				}
				ticTacToe.startNewGame(2, timerLength);
				boolean isSelectionValid, player1Turn = true;
				System.out.println("\n" + welcomeMessage + "\n");
				while (true) {
					System.out.println("\n" + ticTacToe.getGameDisplay());
					System.out.println(inGameOptions);
					System.out.print("choice: ");
					choice = sc.nextLine();
					if (choice.equals("1")) { // 3.1 - Play this turn.
						if (player1Turn) {
							if (player1isAI) {
								ticTacToe.writeStartTime();
								isSelectionValid = true;
								ticTacToe.makeAISelection(1);
							} else {
								try {
									ticTacToe.writeStartTime();
									isSelectionValid = getPlayerXsTurn(1, sc, ticTacToe);
								} catch (TurnTimeoutException e) {
									System.out.println("Player took too long to supply input! Forfeiting turn...");
									isSelectionValid = true;
								}
							}
							if (!isSelectionValid)
								System.out.println("That was not a valid selection.");
								continue;
						} else {
							if (player2isAI) {
								ticTacToe.writeStartTime();
								isSelectionValid = true;
								ticTacToe.makeAISelection(2);
							} else {
								try {
									ticTacToe.writeStartTime();
									isSelectionValid = getPlayerXsTurn(2, sc, ticTacToe);
								} catch (TurnTimeoutException e) {
									System.out.println("Player took too long to supply input! Forfeiting turn...");
									isSelectionValid = true;
								}
							}
							if (!isSelectionValid)
								continue;
						}
						player1Turn = !player1Turn;
						int winner = ticTacToe.determineWinner();
						if (winner != 0) {
							StringBuilder message = new StringBuilder("\n");
							System.out.println();
							if (winner == 1) {
								message.append(victoryMessage);
								message.append("\nPlayer 1 has won!\n");
							}
							if (winner == 2) {
								message.append(victoryMessage);
								message.append("\nPlayer 2 has won!\n");
							}
							if (winner == 3) {
								message.append(drawMessage);
								System.out.println("\nIt's a draw!\n");
							}
							System.out.println(message + "\n");
							System.out.println(ticTacToe.getGameDisplay());
							break;
						}
					} else if (choice.equals("2")) { // 3.2 - Reset the game.
						ticTacToe.resetGame();
						player1Turn = true;
					} else if (choice.equals("3")) { // 3.3 - Quit the game.
						ticTacToe.quitGame();
						break;
					}
				}
			} else if (choice.equals("4")) { // 4 - Quit the program.
				break;
			}
		}
	}

	private static boolean setPlayerX(int x, Scanner sc, TTTControllerImpl ticTacToe) {

		boolean isValid = false;
		boolean playerisAI = false;;
		do {
			try {
				System.out.println("Please input Player " + x
						+ "'s username on the first line and their marker on the second line. Note that their username can consist of any alphanumeric characters of any length. Their marker must consist of only ONE alphanumeric character.");
				System.out.print("username: ");
				String username = sc.nextLine();
				System.out.print("marker: ");
				String marker = sc.nextLine();
		
				String isAI;
				do {
					System.out.println("Should Player " + x + " be an AI? Type \"yes\" or \"no\".");
					System.out.print("choice: ");
					isAI = sc.nextLine();
				} while (!isAI.equals("yes") && !isAI.equals("no"));
		
				
				if (isAI.equals("yes"))
					playerisAI = true;
				else
					playerisAI = false;
		
				if (playerisAI) {
					ticTacToe.createAI(username, marker, x);
				} else {
					ticTacToe.createPlayer(username, marker, x);
				}
				isValid = true;
			}
			catch (InvalidMarkerException e) {
				System.out.println("That was not a valid marker for your player. Your marker must be of length exactly 1. Please try again.");
				isValid = false;
			}
		} while (!isValid);

		return playerisAI;
	}

	public static void main(String[] args) {
		myMain();
	}

	private static int getPos(Scanner sc) {
		Integer pos = null;
		while (pos == null) {
			try {
				pos = Integer.valueOf(sc.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("That was not a valid position");
				pos = null;
			}
		}
		return pos;
	}

	private static boolean getPlayerXsTurn(int x, Scanner sc, TTTControllerImpl ticTacToe) {
		if (x != 1 && x != 2)
			throw new IllegalArgumentException("Player number must be either 1 or 2. Player number supplied was: " + x);

		System.out.println("Player " + x + "'s turn!");
		System.out.println("Supply the row, followed by the column of your choice.");
		System.out.print("row: ");
		int row = getPos(sc);
		System.out.print("col: ");
		int col = getPos(sc);
		return ticTacToe.setSelection(row, col, x);
	}
}