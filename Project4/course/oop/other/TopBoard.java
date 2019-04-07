package course.oop.other;

import java.util.List;

import course.oop.other.exceptions.GameNotInProgressException;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;

public class TopBoard extends Board<BottomBoard> {
	
    private OnePair lastBottomMove;

    public TopBoard() {
    	super();
    	lastBottomMove = null;
    }
    
	@Override
	public boolean attemptMove(Player player, Pair move) {
		
		if (!(move instanceof TwoPair))
			throw new IllegalArgumentException(
					"Move must be an instance of TwoPair, only two grids are viable at this depth.");
		
		if (this.status != GameStatus.ongoing) throw new GameNotInProgressException();

		TwoPair currbothMoves = (TwoPair) move;
		OnePair currTopMove = currbothMoves.pair1;
		OnePair currBottomMove = currbothMoves.pair2;

		if (lastBottomMove != null && !lastBottomMove.equals(currTopMove)) {
			return false;
		}
		
		int row, col;
		row = currBottomMove.row;
		col = currBottomMove.col;
		
		if (!isValidPos(row) || !isValidPos(col)) return false;
		
		if (grid[row][col].getWinningPlayer() != null) return false;
		boolean moveSuccess = grid[row][col].attemptMove(player, currTopMove);
		
		if (moveSuccess) {
			updateStatus(currBottomMove);
			if (grid[row][col].getWinningPlayer() == null) this.lastBottomMove = currBottomMove;
			else this.lastBottomMove = null;
		}
		return moveSuccess;
	}

	@Override
	public void resetBoard() {
		grid = new BottomBoard[3][3];
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				grid[i][j] = new BottomBoard();
			}
		}
		status = GameStatus.ongoing;
	}
	
	@Override
	public GridPane getGuiDisplay(boolean scaleSqaures) {

		GridPane guiRep = new GridPane();
		
		if (scaleSqaures) {
			ColumnConstraints columnConst;/* = new ColumnConstraints();
			columnConst.setPercentWidth(75.0/3);*/
			RowConstraints rowConst;/* = new RowConstraints();
			rowConst.setPercentHeight(75/3);*/
			int cellSize = 100/9;
			
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
		}
			
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				GridPane currPane = grid[i][j].getGuiDisplay(true);
				guiRep.add(currPane,j,i);
			}
		}
		
//		guiRep.setGridLinesVisible(true);   
		
		return guiRep;
	}
	
	@Override
	protected boolean checkSamePlayer(List<OnePair> lis) {
		
		Player comparePlayer = null, currPlayer = null;
		boolean samePlayer = true;
		if (grid[lis.get(0).row][lis.get(0).col].getWinningPlayer() != null) {
			for (OnePair pair : lis) {
				//check each element to see if they are the same player
				currPlayer = grid[pair.row][pair.col].getWinningPlayer();
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

	@Override
	protected boolean noVacancies() {
		boolean noVacancies = true;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (grid[i][j].getWinningPlayer() == null) {
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
}