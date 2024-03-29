package course.oop.other;

import java.util.List;
import course.oop.other.exceptions.GameNotInProgressException;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.geometry.HPos;
import javafx.scene.text.Text;


public class BottomBoard extends Board<Square> {

	public BottomBoard() {
		resetBoard();
	}

	@Override
	public boolean attemptMove(Player player, Pair move) {
		if (!(move instanceof OnePair))
			throw new IllegalArgumentException(
					"Move must be an instance of OnePair, only one grid is viable at this depth.");
		
		if (this.status != GameStatus.ongoing) throw new GameNotInProgressException();

		OnePair currDepthMove = (OnePair) move;

		int row, col;
		row = currDepthMove.row;
		col = currDepthMove.col;
		
		if (!isValidPos(row) || !isValidPos(col)) return false;
		
		boolean moveSuccess = grid[row][col].setPlayerOccupation(player);
		if (moveSuccess) updateStatus(move);
		return moveSuccess;
	}
	
	private boolean isValidPos(int pos) {
		return pos >= 0 && pos < 3;
	}

	@Override
	public String draw() {
		return toString();
	}
	
	private boolean checkSamePlayer(List<OnePair> lis) {
		
		Player comparePlayer = null, currPlayer = null;
		boolean samePlayer = true;
		//check THIS row
		if (grid[lis.get(0).row][lis.get(0).col].getPlayer() != null) {
			for (OnePair pair : lis) {
				//check each element to see if they are the same player
				currPlayer = grid[pair.row][pair.col].getPlayer();
				if (comparePlayer == null) comparePlayer = currPlayer;
				if ( !comparePlayer.equals(currPlayer) ) samePlayer = false;
			}
			if (samePlayer) {
				status = GameStatus.victory;
				winningPlayer = comparePlayer;
				return true;
			}
		}
		
		return false;
	}
	
	private boolean noVacancies() {
		boolean noVacancies = true;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (grid[i][j].getPlayer() == null) {
					noVacancies = false;
					break;
				}
			}
		}
		
		if (noVacancies) {
			status = GameStatus.draw;
			winningPlayer = null;
		}
		
		return noVacancies;
	}
	
	@Override
	protected GameStatus updateStatus(Pair lastMove) {
		OnePair move = (OnePair)(lastMove);

		//check THIS row
		List<OnePair> row = getRow(move);
		if (checkSamePlayer(row)) return this.status;
		
		//check THIS column
		List<OnePair> col = getCol(move);
		if (checkSamePlayer(col)) return this.status;
		
		//check diagonals (this move either belongs to the left diagonal, the right diagonal, both diagonals, or neither diagonals)
		List<List<OnePair>> diags = getDiag(move);
		for (List<OnePair> diag : diags) {
			if (checkSamePlayer(diag)) return this.status;
		}
		
		if (noVacancies()) return this.status;
		
		return status; //happens when there is not a victory or a draw
	}

	@Override
	public String toString() {
		StringBuilder drawing = new StringBuilder("Game Status: ");
		drawing.append(this.status.toString().toUpperCase());
		drawing.append("\n      |     |     \n   ");
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
		drawing.append("\n      |     |    ");
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
		status = GameStatus.ongoing;
	}

	public GameStatus getStatus() {
		return this.status;
	}
	
	public void quit() {
		if (this.status == GameStatus.quit) throw new GameNotInProgressException();
		this.status = GameStatus.quit;
	}

	@Override
	public GridPane getGuiDisplay() {
		
		GridPane guiRep = new GridPane();
	
		ColumnConstraints columnConst;/* = new ColumnConstraints();
		columnConst.setPercentWidth(75.0/3);*/
		RowConstraints rowConst;/* = new RowConstraints();
		rowConst.setPercentHeight(75/3);*/
		int cellSize = 100;
		
		for (int i = 0; i < 3; i++) {
			rowConst = new RowConstraints();
			rowConst.setMinHeight(cellSize);
			rowConst.setMaxHeight(cellSize);
			columnConst = new ColumnConstraints();
			columnConst.setMinWidth(cellSize);
			columnConst.setMaxWidth(cellSize);
			columnConst.setHalignment(HPos.CENTER);
			guiRep.getColumnConstraints().add(columnConst);
			guiRep.getRowConstraints().add(rowConst);
		}
		
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				Text currText = grid[i][j].getGuiDisplay();
				guiRep.add(currText,j,i);
			}
		}
		
		guiRep.setGridLinesVisible(true);   
		
		return guiRep;
	}
}