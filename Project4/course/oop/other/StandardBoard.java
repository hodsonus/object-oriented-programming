package course.oop.other;

public abstract class StandardBoard<T> extends Board {
	
    protected T[][] grid;
    
    public StandardBoard(int desiredSize) {
    	super(desiredSize);
    }
	
	@Override
	protected GameStatus updateStatus(Coordinate lastMove) {
		if (!(lastMove instanceof OnePair)) throw new IllegalArgumentException();
		OnePair move = (OnePair)lastMove;
		
		int x,y;
		x = move.row;
		y = move.col;
		
		//check the row
		if( iterateTwoDimsGeneric(x,y, 0,-1, 0,1) ) return this.status;
		
		//check the column
		if( iterateTwoDimsGeneric(x,y, -1,0, 1,0) ) return this.status;
		
		//check the diagonal
		if( iterateTwoDimsGeneric(x,y, -1,-1, 1,1) ) return this.status;
		
		//check the other diagonal
		if( iterateTwoDimsGeneric(x,y, -1,1, 1,-1) ) return this.status;
		
		if (noVacancies()) return this.status;
		
		return status; //happens when there is not a victory or a draw
	}
	
	protected abstract boolean iterateTwoDimsGeneric(int x, int y, int a_x_diff, int a_y_diff, int b_x_diff, int b_y_diff);
	protected abstract boolean noVacancies();
}
