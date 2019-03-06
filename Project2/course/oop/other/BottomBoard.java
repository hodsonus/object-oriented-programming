package course.oop.other;

import java.util.List;

public class BottomBoard extends Board<Square> {

	public BottomBoard() {
		resetBoard();
	}

	@Override
	public boolean attemptMove(Player player, Pair move) {
		if (!(move instanceof OnePair))
			throw new IllegalArgumentException(
					"Move must be an instance of OnePair, only one grid is viable at this depth.");

		OnePair currDepthMove = (OnePair) move;

		int row, col;
		row = currDepthMove.row;
		col = currDepthMove.col;
		
		
		boolean moveSuccess = grid[row][col].setPlayerOccupation(player);
		if (moveSuccess) updateStatus(move);
		return moveSuccess;
	}

	@Override
	public String draw() {
		return toString();
	}
	
	@Override
	public GameStatus updateStatus(Pair lastMove) {
		OnePair move = (OnePair)(lastMove);
						
		Player comparePlayer = null, currPlayer = null;
		boolean samePlayer = true;
		//check THIS row
		List<OnePair> row = getRow(move);
		for (OnePair pair : row) {
			//check each element to see if they are the same player
			currPlayer = grid[pair.row][pair.col].getPlayer();
			if (comparePlayer == null) comparePlayer = currPlayer;
			if ( !comparePlayer.equals(currPlayer) ) samePlayer = false;
		}
		if (samePlayer) {
			status = GameStatus.victory;
			winningPlayer = comparePlayer;
			return status;
		}
		
		comparePlayer = null;
		currPlayer = null;
		samePlayer = true;
		//check THIS column
		List<OnePair> col = getCol(move);
		for (OnePair pair : col) {
			//check each element to see if they are the same player
			currPlayer = grid[pair.row][pair.col].getPlayer();
			if (comparePlayer == null) comparePlayer = currPlayer;
			if ( !comparePlayer.equals(currPlayer) ) samePlayer = false;
		}
		if (samePlayer) {
			status = GameStatus.victory;
			winningPlayer = comparePlayer;
			return status;
		}
		
		//check diagonals (this move either belongs to the left diagonal, the right diagonal, both diagonals, or neither diagonals)
		List<List<OnePair>> diags = getDiag(move);
		for (List<OnePair> diag : diags) {
			comparePlayer = null;
			currPlayer = null;
			samePlayer = true;
			for (OnePair pair : diag) {
				//check each element to see if they are the same player
				currPlayer = grid[pair.row][pair.col].getPlayer();
				if (comparePlayer == null) comparePlayer = currPlayer;
				if ( !comparePlayer.equals(currPlayer) ) samePlayer = false;
				
			}
			if (samePlayer) {
				status = GameStatus.victory;
				winningPlayer = comparePlayer;
				return status;
			}
		}
		
		return status; //happens when there is not a victory
	}

	@Override
	public String toString() {
		StringBuilder drawing = new StringBuilder("      |     |     \n   ");
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				drawing.append(grid[i][j].toString());
				if (j != grid[i].length - 1) {
					drawing.append("  |  ");
				}
			}

			if (i != grid.length - 1) {
				drawing.append("\n _____|_____|_____\n      |     |     \n   ");
			}
		}
		drawing.append("\n      |     |    \n");
		return drawing.toString();
	}

	@Override
	public void resetBoard() {
		grid = new Square[3][3];
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				grid[i][j] = new Square();
			}
		}
	}
}