package course.oop.other;

import java.util.ArrayList;
import java.util.List;

public abstract class Board<T> {

    public Board() {
    	nextPos = null;
    	winningPlayer = null;
    }

    protected T[][] grid;    
    protected OnePair nextPos;
    protected GameStatus status;
    protected Player winningPlayer;

    public Player getWinningPlayer() {
    	return winningPlayer;
    }
    
	protected abstract GameStatus updateStatus(Pair lastMove);
	
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
    
    public abstract String draw();
	public abstract void resetBoard();
	public abstract boolean attemptMove(Player player, Pair move);
}