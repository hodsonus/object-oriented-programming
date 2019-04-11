package course.oop.model;

import java.util.HashSet;
import course.oop.exceptions.GameNotInProgressException;
import course.oop.other.Axis;
import course.oop.other.Coordinate;
import course.oop.other.GameStatus;
import course.oop.other.SquareStatus;
import course.oop.other.Triple;
import course.oop.players.Player;
import javafx.geometry.HPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

public class ThreeDimBoard extends Board {
	
    protected Square[][][] grid;  
    private HashSet<Triple> winningMove;
        
    public ThreeDimBoard(int desiredSize) {
    	super(desiredSize);
    }
    
    @Override
	protected boolean isValidPos(int pos) {
		return pos >= 0 && pos < size;
	}

	@Override
	public boolean attemptMove(Player player, Coordinate move) {
		if (!(move instanceof Triple))
			throw new IllegalArgumentException(
					"Move must be an instance of Triple, only one three dimensional grid is viable at this depth.");
		
		if (this.status != GameStatus.ongoing) throw new GameNotInProgressException("Cannot attempt a move if the game is not ongoing.");

		Triple currDepthMove = (Triple) move;

		int row, col, dep;
		row = currDepthMove.row;
		col = currDepthMove.col;
		dep = currDepthMove.dep;
		
		if (!isValidPos(row) || !isValidPos(col)|| !isValidPos(dep)) return false;
		
		boolean moveSuccess = grid[row][col][dep].setPlayerOccupation(player);
		if (moveSuccess) updateStatus(currDepthMove);
		return moveSuccess;
	}
	
	@Override
	public void resetBoard() {
		grid = new Square[size][size][size];
		for (int i = 0; i < size; ++i) {
			for (int j = 0; j < size; ++j) {
				for (int k = 0; k < size; ++k) {
					grid[i][j][k] = new Square();
				}
			}
		}
		winningMove = new HashSet<Triple>();
		status = GameStatus.ongoing;
	}
	
	private boolean iterateThreeDimsGeneric(int x, int y, int z, int a_x_diff, int a_y_diff, int a_z_diff, int b_x_diff, int b_y_diff, int b_z_diff) {
		
		if (!isValidPos(x) || !isValidPos(y) || !isValidPos(z)) throw new IllegalArgumentException("Invalid starting coordinate provided to function.");

		Player currPlayer, comparePlayer = grid[x][y][z].getPlayer();
		winningMove.add(new Triple(x,y,z));
		int a_x, a_y, a_z, b_x, b_y, b_z, totalChecks;
		boolean samePlayer;

		a_x = x + a_x_diff;
		a_y = y + a_y_diff;
		a_z = z + a_z_diff;
		b_x = x + b_x_diff;
		b_y = y + b_y_diff;
		b_z = z + b_z_diff;
		samePlayer = true;
		totalChecks = 1;
		//while either position are valid
		while (  (isValidPos(a_x) && isValidPos(a_y) && isValidPos(a_z)) || (isValidPos(b_x) && isValidPos(b_y) && isValidPos(b_z))  ) {
			if (isValidPos(a_x) && isValidPos(a_y) && isValidPos(a_z)) {
				currPlayer = grid[a_x][a_y][a_z].getPlayer();
				winningMove.add(new Triple(a_x,a_y,a_z));
				if (currPlayer == null || !currPlayer.equals(comparePlayer)) {
					samePlayer = false;
					break;
				}
				else {
					a_x += a_x_diff;
					a_y += a_y_diff;
					a_z += a_z_diff;
					++totalChecks;
				}
			}
			if (isValidPos(b_x) && isValidPos(b_y) && isValidPos(b_z)) {
				currPlayer = grid[b_x][b_y][b_z].getPlayer();
				winningMove.add(new Triple(b_x,b_y,b_z));
				if (currPlayer == null || !currPlayer.equals(comparePlayer)) {
					samePlayer = false;
					break;
				}
				else {
					b_x += b_x_diff;
					b_y += b_y_diff;
					b_z += b_z_diff;
					++totalChecks;
				}
			}
		}
		if (samePlayer && totalChecks == this.size) {
			this.status = GameStatus.victory;
			winningPlayer = comparePlayer;
			return true;
		}
		else {
			winningMove = new HashSet<Triple>();
		}
		
		return false;
	}
	
	private boolean checkDiags(int x, int y, int z, Axis axisToFix) {
				
		if (axisToFix == Axis.X) { // fix x
			if(iterateThreeDimsGeneric(x,y,z, 0,1,1, 0,-1,-1)) return true;
			if (iterateThreeDimsGeneric(x,y,z, 0,-1,1, 0,1,-1)) return true;
		}
		else if (axisToFix == Axis.Y) { // fix y
			if(iterateThreeDimsGeneric(x,y,z, 1,0,1, -1,0,-1)) return true;
			if (iterateThreeDimsGeneric(x,y,z, -1,0,1, 1,0,-1)) return true;
		}
		else if (axisToFix == Axis.Z) { // fix Z
			if(iterateThreeDimsGeneric(x,y,z, 1,1,0, -1,-1,0)) return true;
			if (iterateThreeDimsGeneric(x,y,z, -1,1,0, 1,-1,0)) return true;
		}
		else throw new IllegalArgumentException("Invalid axis to fix supplied to function.");

		return false;
	}

	@Override
	protected GameStatus updateStatus(Coordinate lastMove) {
		if (!(lastMove instanceof Triple)) throw new IllegalArgumentException();
		Triple move = (Triple)lastMove;
		
		int x,y,z;
		x = move.row;
		y = move.col;
		z = move.dep;
		
		//check verticals, horizontals, and depths
		if( iterateThreeDimsGeneric(x,y,z, -1, 0, 0,  1,0,0) ) return this.status;
		if( iterateThreeDimsGeneric(x,y,z,  0,-1, 0,  0,1,0) ) return this.status;
		if( iterateThreeDimsGeneric(x,y,z,  0, 0,-1,  0,0,1) ) return this.status;

		//check the corner to corner on the same plane
		if (checkDiags(x,y,z,Axis.Z)) return this.status; // hold z constant, vary x and y
		if (checkDiags(x,y,z,Axis.Y)) return this.status; // hold y constant, vary x and z
		if (checkDiags(x,y,z,Axis.X)) return this.status; // hold x constant, vary y and z
		
		// check the main 4 diagonals for victory
		if( iterateThreeDimsGeneric(x,y,z, -1,-1,-1,  1, 1,1) ) return this.status;
		if( iterateThreeDimsGeneric(x,y,z, -1, 1,-1,  1,-1,1) ) return this.status;
		if( iterateThreeDimsGeneric(x,y,z,  1, 1,-1, -1,-1,1) ) return this.status;
		if( iterateThreeDimsGeneric(x,y,z,  1,-1,-1, -1, 1,1) ) return this.status;

		// if we have gotten this far, there is not a winner at this point. iterate over the entire cube, checking for 'vacancies'. if a single vacancy exists, the game is still ongoing
		// if a vacancy DOES NOT exist, then the game is a draw. update the status and return
		boolean noVacancies = true;
		for (int i = 0; i < size; ++i) {
			for (int j = 0; j < size; ++j) {
				for (int k = 0; k < size; ++k) {
					if (grid[i][j][k].getStatus() == SquareStatus.vacant) {
						noVacancies = false;
					}
				}
			}
		}
		if (noVacancies) {
			this.status = GameStatus.draw;
			this.winningPlayer = null;
			return this.status;
		}
		
		// if we have gotten THIS far, that means their is not a victory or a draw and the game is ongoing
		return status;
	}

	@Override
	public GridPane getGuiDisplay(boolean absoluteSquares, boolean colorSquares) {
							
		GridPane guiRep = new GridPane();
		
		double totalRepresentationSize = 300;
		
		// see the comment regarding row constraints for an explanation on 
		double rowHeight = totalRepresentationSize/this.size;
		
		double sizeOfEachGap;
		if (size == 3) sizeOfEachGap = 0;
		else sizeOfEachGap = (1 + (size-4)*1.5)*rowHeight*1/3;		
		guiRep.setVgap(sizeOfEachGap);
		/* add constraints for rows on the top level of the representation, each 
		 * row height should be equal and thus should be the result of the division
		 * between 300 (the total size of the representation) and the number of 
		 * rows (the size) */
		RowConstraints rowConst;
		for (int row = 0; row < size; row++) {
			rowConst = new RowConstraints();
			if (absoluteSquares) {
				rowConst.setMinHeight(rowHeight);
				rowConst.setMaxHeight(rowHeight);
			}			
			else {
				throw new UnsupportedOperationException("Nesting behavior is not"
						+ "implemented for three dimensional boards yet.");
			}
			guiRep.getRowConstraints().add(rowConst);
		}
		
		/* following above's logic, there will be only one column so it should be constrained by 
		 * the total size of the representation of the board*/
		ColumnConstraints columnConst = new ColumnConstraints();
		columnConst.setMinWidth(totalRepresentationSize);
		columnConst.setMaxWidth(totalRepresentationSize);
		columnConst.setHalignment(HPos.CENTER);
		guiRep.getColumnConstraints().add(columnConst);
		
		Text currSquare;

		// add each slice of the cube to the representation in the below for loop
		for (int k = 0; k < size; ++k) {
			
			GridPane sliceRep = new GridPane(); // the representation for an individual slice
			
			for (int cell = 0; cell < size; ++cell) {
				rowConst = new RowConstraints();
				columnConst = new ColumnConstraints();
				rowConst.setPercentHeight(100/this.size);
				columnConst.setPercentWidth(100/this.size);
				columnConst.setHalignment(HPos.CENTER);
				sliceRep.getColumnConstraints().add(columnConst);
				sliceRep.getRowConstraints().add(rowConst);
			}
			
			for (int i = 0; i < size; ++i) {
				for (int j = 0; j < size; ++j) {
					currSquare = grid[i][j][k].getGuiDisplay();
					if (winningMove.contains(new Triple(i,j,k))) currSquare.setFill(Color.web("#CD6155"));
					sliceRep.add(currSquare,j,i);
				}
			}
			
			// apply rotations to give the illusion that it is in 3D space
			sliceRep.getTransforms().add(new Rotate(-55, Rotate.X_AXIS)); //rotate away from us
			sliceRep.getTransforms().add(new Rotate(-10, Rotate.Y_AXIS)); 
			sliceRep.getTransforms().add(new Rotate(-20, Rotate.Z_AXIS));
			
			sliceRep.setGridLinesVisible(true);
			
			//always the 0th column, kth row
			guiRep.add(sliceRep, 0, k);
		}
				
		return guiRep;
	}
	
//	@Override
//	public String toString() {
//		StringBuilder drawing = new StringBuilder("Game Status: ");
//		drawing.append(this.status.toString().toUpperCase());
//		
//		// slice the cube from top layer to the bottom layer by starting at k=size-1 and moving to the bottom
//		for (int k = size-1; k >= 0; --k) {
//			drawing.append("\n      |     |     \n   ");
//			for (int i = 0; i < size; ++i) {
//				for (int j = 0; j < size; ++j) {
//					drawing.append(grid[i][j][k].toString());
//					if (j != grid[i].length - 1) {
//						drawing.append("  |  ");
//					}
//				}
//	
//				if (i != grid.length - 1) {
//					drawing.append("\n _____|_____|_____\n      |     |     \n   ");
//				}
//			}
//			drawing.append("\n      |     |    ");
//		}
//		return drawing.toString();
//	}
}