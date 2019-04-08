package course.oop.other;

import java.util.ArrayList;
import java.util.List;

import course.oop.other.exceptions.GameNotInProgressException;
import javafx.scene.layout.GridPane;

public abstract class Board<T> {
	
    protected T[][] grid;    
    protected GameStatus status;
    protected Player winningPlayer;


    public Board() {
    	resetBoard();
    	winningPlayer = null;
    }
    
    public Player getWinningPlayer() {
    	return winningPlayer;
    }
    	
	protected boolean isValidPos(int pos) {
		return pos >= 0 && pos < 3;
	}
	
	public void quit() {
		if (this.status == GameStatus.quit) throw new GameNotInProgressException();
		this.status = GameStatus.quit;
	}
	
	public String draw() {
		return toString();
	}
    
	public GameStatus getStatus() {
		return this.status;
	}
	
    protected List<OnePair> getRow(OnePair pair) {
		if      (RowColDia.rowA.contains(pair)) return RowColDia.rowA;
		else if (RowColDia.rowB.contains(pair)) return RowColDia.rowB;
		else if (RowColDia.rowC.contains(pair)) return RowColDia.rowC;
		return null; // this should NEVER happen. null pointer exceptions here imply something else is VERY wrong with the state elsewhere
    }
	
    protected List<OnePair> getCol(OnePair pair) {
		if      (RowColDia.colA.contains(pair)) return RowColDia.colA;
		else if (RowColDia.colB.contains(pair)) return RowColDia.colB;
		else if (RowColDia.colC.contains(pair)) return RowColDia.colC;
		return null; // this should NEVER happen. null pointer exceptions here imply something else is VERY wrong with the state elsewhere
    }
	
    protected List<List<OnePair>> getDiag(OnePair pair) {
    	ArrayList<List<OnePair>> diagList = new ArrayList<List<OnePair>>();
		if      (RowColDia.diagA.contains(pair)) diagList.add(RowColDia.diagA);
		else if (RowColDia.diagB.contains(pair)) diagList.add(RowColDia.diagB);
		return diagList;
    }
	
	protected GameStatus updateStatus(OnePair lastMove) {
		
		//check THIS row
		List<OnePair> row = getRow(lastMove);
		if (checkSamePlayer(row)) return this.status;
		
		//check THIS column
		List<OnePair> col = getCol(lastMove);
		if (checkSamePlayer(col)) return this.status;
		
		//check diagonals (this move either belongs to the left diagonal, the right diagonal, both diagonals, or neither diagonals)
		List<List<OnePair>> diags = getDiag(lastMove);
		for (List<OnePair> diag : diags) {
			if (checkSamePlayer(diag)) return this.status;
		}
		
		if (noVacancies()) return this.status;
		
		return status; //happens when there is not a victory or a draw
	}

	protected abstract boolean checkSamePlayer(List<OnePair> lis); 
	protected abstract boolean noVacancies();
	public abstract void resetBoard();
	public abstract boolean attemptMove(Player player, Coordinate move);
	public abstract GridPane getGuiDisplay(boolean scaleSquares);
}