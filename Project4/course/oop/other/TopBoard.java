package course.oop.other;

import course.oop.other.exceptions.GameNotInProgressException;
import javafx.geometry.HPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class TopBoard extends StandardBoard<BottomBoard> {
	
    private OnePair lastBottomMove;

    public TopBoard(int desiredSize) {
    	super(desiredSize);
    	lastBottomMove = null;
    }
    
	@Override
	public boolean attemptMove(Player player, Coordinate move) {
		
		if (!(move instanceof TwoPair))
			throw new IllegalArgumentException(
					"Move must be an instance of TwoPair, only two grids are viable at this depth.");
		
		if (this.status != GameStatus.ongoing) throw new GameNotInProgressException();

		TwoPair currbothMoves = (TwoPair) move;
		OnePair currTopMove = currbothMoves.pair1;
		OnePair currBottomMove = currbothMoves.pair2;

		if (lastBottomMove != null) {
			if (grid[lastBottomMove.row][lastBottomMove.col].getStatus() != GameStatus.ongoing) {
				lastBottomMove = null;
			}
			else if (!lastBottomMove.equals(currTopMove)) {
				return false;
			}
		}
		
		int row, col;
		row = currTopMove.row;
		col = currTopMove.col;
		
		if (!isValidPos(row) || !isValidPos(col)) return false;
		if (grid[row][col].getStatus() != GameStatus.ongoing) return false;
		
		boolean moveSuccess = grid[row][col].attemptMove(player, currBottomMove);
		
		if (moveSuccess) {
			updateStatus(currTopMove);
			lastBottomMove = currBottomMove;
		}
		return moveSuccess;
	}

	@Override
	public void resetBoard() {
		grid = new BottomBoard[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				grid[i][j] = new BottomBoard(size);
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
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				GridPane currPane = grid[i][j].getGuiDisplay(false);
				if (grid[i][j].getStatus() != GameStatus.ongoing) currPane.setStyle("-fx-background-color: #CD6155;");
				else if (lastBottomMove != null && i == lastBottomMove.row && j == lastBottomMove.col) currPane.setStyle("-fx-background-color: #D98880;");
				guiRep.add(currPane,j,i);
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
				if (grid[i][j].getStatus() == GameStatus.ongoing) {
					noVacancies = false;
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
		
		drawing.append("\n");
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				drawing.append("Row: " + i + ", Col: " + j + "\t");
				drawing.append(grid[i][j].toString());
				drawing.append("\n");
			}
			drawing.append("\n\n");
		}
		return drawing.toString();
	}

	@Override
	protected boolean iterateTwoDimsGeneric(int x, int y, int a_x_diff, int a_y_diff, int b_x_diff, int b_y_diff) {
		if (!isValidPos(x) || !isValidPos(y)) throw new IllegalArgumentException("Invalid starting coordinate provided to function.");
		
		Player currPlayer, comparePlayer = grid[x][y].getWinningPlayer();
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
				currPlayer = grid[a_x][a_y].getWinningPlayer();
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
				currPlayer = grid[b_x][b_y].getWinningPlayer();
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