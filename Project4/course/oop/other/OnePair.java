package course.oop.other;

public class OnePair implements Coordinate {

    public OnePair(int row, int col) {
    	this.row = row;
    	this.col = col;
    }
    
    @Override
    public boolean equals(Object b) {
    	if (!(b instanceof OnePair)) return false;
    	OnePair otherPair = (OnePair)(b);
    	return this.row==otherPair.row && this.col==otherPair.col;
    }
    
    @Override
    public int hashCode() {
        return 32768*row+col;
    }
    
    public int col;
    public int row;
}