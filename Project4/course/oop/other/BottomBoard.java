package course.oop.other;

import course.oop.other.exceptions.GameNotInProgressException;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.geometry.HPos;
import javafx.scene.text.Text;

public class BottomBoard extends StandardBoard<Square> {

	public BottomBoard(int desiredSize) {
		super(desiredSize);
	}

	@Override
	public boolean attemptMove(Player player, Coordinate move) {
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
		if (moveSuccess) updateStatus(currDepthMove);
		return moveSuccess;
	}
	
	@Override
	public void resetBoard() {
		grid = new Square[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				grid[i][j] = new Square();
			}
		}
		status = GameStatus.ongoing;
	}
	
	@Override
	public GridPane getGuiDisplay(boolean absoluteSquares) {
		
		GridPane guiRep = new GridPane();

		ColumnConstraints columnConst;
		RowConstraints rowConst;
		int cellSize = 100;
		for (int i = 0; i < size; i++) {
			
			rowConst = new RowConstraints();
			columnConst = new ColumnConstraints();
			
			if (absoluteSquares) {
				rowConst.setMinHeight(cellSize);
				rowConst.setMaxHeight(cellSize);
				columnConst.setMinWidth(cellSize);
				columnConst.setMaxWidth(cellSize);
			}			
			else {
				rowConst.setPercentHeight(100/size);
				columnConst.setPercentWidth(100/size);
			}
			
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
		
	@Override
	protected boolean noVacancies() {
		boolean noVacancies = true;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (grid[i][j].getStatus() == SquareStatus.vacant) {
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
	public String toString() {
		StringBuilder drawing = new StringBuilder("Game Status: ");
		drawing.append(this.status.toString().toUpperCase());
		drawing.append("\n      |     |     \n   ");
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
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
	protected boolean iterateTwoDimsGeneric(int x, int y, int a_x_diff, int a_y_diff, int b_x_diff, int b_y_diff) {
		
		if (!isValidPos(x) || !isValidPos(y)) throw new IllegalArgumentException("Invalid starting coordinate provided to function.");
				
		Player currPlayer, comparePlayer = grid[x][y].getPlayer();
		int a_x, a_y, b_x, b_y, totalChecks;
		boolean samePlayer;
		
		a_x = x + a_x_diff;
		a_y = y + a_y_diff;
		b_x = x + b_x_diff;
		b_y = y + b_y_diff;
		samePlayer = true;
		totalChecks = 1;

		while (  (isValidPos(a_x) && isValidPos(a_y)) || (isValidPos(b_x) && isValidPos(b_y))  ) {
			if (isValidPos(a_x) && isValidPos(a_y)) {
				currPlayer = grid[a_x][a_y].getPlayer();
				if (currPlayer == null || !currPlayer.equals(comparePlayer)) {
					samePlayer = false;
					break;
				}
				else {
					a_x += a_x_diff;
					a_y += a_y_diff;
					++totalChecks;
				}
			}
			if (isValidPos(b_x) && isValidPos(b_y)) {
				currPlayer = grid[b_x][b_y].getPlayer();
				if (currPlayer == null || !currPlayer.equals(comparePlayer)) {
					samePlayer = false;
					break;
				}
				else {
					b_x += b_x_diff;
					b_y += b_y_diff;
					++totalChecks;
				}
			}
		}
		if (samePlayer && totalChecks == this.size) {
			this.status = GameStatus.victory;
			winningPlayer = comparePlayer;
			return true;
		}
		
		return false;
	}
}