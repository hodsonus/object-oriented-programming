package course.oop.other;

public class Triple implements Coordinate {

    public Triple(int row, int col, int dep) {
    	this.row = row;
    	this.col = col;
    	this.dep = dep;
    }
    
    @Override
    public boolean equals(Object b) {
    	if (!(b instanceof Triple)) return false;
    	Triple otherTriplet = (Triple)(b);
    	return this.row==otherTriplet.row && this.col==otherTriplet.col && this.dep == otherTriplet.dep;
    }
    
    @Override
    public int hashCode() {
        return (32768*row+col)*32768 + dep;
    }
    
    public int row; // x
    public int col; // y
    public int dep; // z
}