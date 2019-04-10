package course.oop.other;

import course.oop.other.exceptions.GameNotInProgressException;
import javafx.geometry.HPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

public class ThreeDimBoard extends Board {
	
    protected Square[][][] grid;  
    private int size;
        
    public ThreeDimBoard(int size) {
    	super();
    	this.size = size;
    	resetBoard();
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
		
		if (this.status != GameStatus.ongoing) throw new GameNotInProgressException();

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
		status = GameStatus.ongoing;
	}
	
	/* checks columns, rows, and depths. call using 0,y,z,1,0,0 */
	private boolean checkStraights(int x, int y, int z, int a, int b, int c) {
		Player comparePlayer, currPlayer;
		boolean samePlayer;
	
		// hold y and z constant, iterate over x
		comparePlayer = grid[x][y][z].getPlayer();
		samePlayer = true;
		x+=a;
		y+=b;
		z+=c;
		while ( isValidPos(x) && isValidPos(y) && isValidPos(z) ) {
			currPlayer = grid[x][y][z].getPlayer();
			if (comparePlayer == null || currPlayer == null || !comparePlayer.equals(currPlayer)) {
				samePlayer = false;
				break;
			}
			x+=a;
			y+=b;
			z+=c;
		}
		if (samePlayer) {
			this.status = GameStatus.victory;
			winningPlayer = comparePlayer;
		}
		
		return samePlayer;
	}

	@Override
	protected GameStatus updateStatus(Coordinate lastMove) {
		if (!(lastMove instanceof Triple)) throw new IllegalArgumentException();
		Triple move = (Triple)lastMove;
		
		int x,y,z;
		x = move.row;
		y = move.col;
		z = move.dep;
		
		Player comparePlayer, currPlayer;
		boolean samePlayer;
	
		// hold y and z constant, iterate over x
		comparePlayer = grid[0][y][z].getPlayer();
		samePlayer = true;
		for (int i = 1; i < size; i++) {
			currPlayer = grid[i][y][z].getPlayer();
			if (comparePlayer == null || currPlayer == null || !comparePlayer.equals(currPlayer)) {
				samePlayer = false;
				break;
			}
		}
		if (samePlayer) {
			this.status = GameStatus.victory;
			winningPlayer = comparePlayer;
			return this.status;
		}
			
		// hold x and z constant, iterate over y
		comparePlayer = grid[x][0][z].getPlayer();
		samePlayer = true;
		for (int j = 1; j < size; ++j) {
			currPlayer = grid[x][j][z].getPlayer();
			if (comparePlayer == null || currPlayer == null || !comparePlayer.equals(currPlayer)) {
				samePlayer = false;
				break;
			}
		}
		if (samePlayer) {
			this.status = GameStatus.victory;
			winningPlayer = comparePlayer;
			return this.status;
		}

		//hold x and y constant, iterate over z
		comparePlayer = grid[x][y][0].getPlayer();
		samePlayer = true;
		for (int k = 1; k < size; ++k) {
			currPlayer = grid[x][y][k].getPlayer();
			if (comparePlayer == null || currPlayer == null || !comparePlayer.equals(currPlayer)) {
				samePlayer = false;
				break;
			}
		}
		if (samePlayer) {
			this.status = GameStatus.victory;
			winningPlayer = comparePlayer;
			return this.status;
		}
				
		
		comparePlayer = grid[x][y][z].getPlayer();
		int a_x, a_y, a_z, b_x, b_y, b_z, totalChecks;


		// fix Z, vary X,Y
		// Case 1: add 1,1 to a and add -1,-1 to b
		// Case 2: add 1,-1 to a and -1,1 to b
		a_x = x+1; a_y = y+1;
		b_x = x-1; b_y = y-1;
		samePlayer = true;
		totalChecks = 1;
		//while either position are valid
		while (  (isValidPos(a_x) && isValidPos(a_y)) || (isValidPos(b_x) && isValidPos(b_y))  ) {
			if ((isValidPos(a_x) && isValidPos(a_y))) {
				currPlayer = grid[a_x][a_y][z].getPlayer();
				if (currPlayer == null || !currPlayer.equals(comparePlayer)) {
					samePlayer = false;
					break;
				}
				else {
					++a_x;
					++a_y;
					++totalChecks;
				}
			}
			if ((isValidPos(b_x) && isValidPos(b_y))) {
				currPlayer = grid[b_x][b_y][z].getPlayer();
				if (currPlayer == null || !currPlayer.equals(comparePlayer)) {
					samePlayer = false;
					break;
				}
				else {
					--b_x;
					--b_y;
					++totalChecks;
				}
			}
		}
		if (samePlayer && totalChecks == size) {
			this.status = GameStatus.victory;
			winningPlayer = comparePlayer;
			return this.status;
		}
		
		
		
		// TODO, fix Y, vary X,Z
		// TODO, fix X, vary Y,Z
		
		
		
		
		
		
		// TODO, check 4 primary diagonals from top corner to bottom corner

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
	public GridPane getGuiDisplay(boolean absoluteSquares) {
		
		//TODO, fix this to look prettier for sizes larger than 3
		
		int totalRepresentationSize = 300;
		// see the comment regarding row constraints for an explanation on 
		int rowHeight = totalRepresentationSize/this.size;
		
		GridPane guiRep = new GridPane();
		
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
					Text currSquare = grid[i][j][k].getGuiDisplay();
					sliceRep.add(currSquare,j,i);
				}
			}
			
			// apply rotations to give the illusion that it is in 3D space
			sliceRep.getTransforms().add(new Rotate(-30, Rotate.X_AXIS)); //rotate away from us
			sliceRep.getTransforms().add(new Rotate(-20, Rotate.Y_AXIS)); 
			sliceRep.getTransforms().add(new Rotate(-20, Rotate.Z_AXIS));
			
			sliceRep.setGridLinesVisible(true);
			
			//always the 0th column, kth row
			guiRep.add(sliceRep, 0, k);
		}
				
		return guiRep;
	}
	
	@Override
	public String toString() {
		StringBuilder drawing = new StringBuilder("Game Status: ");
		drawing.append(this.status.toString().toUpperCase());
		
		// slice the cube from top layer to the bottom layer by starting at k=size-1 and moving to the bottom
		for (int k = size-1; k >= 0; --k) {
			drawing.append("\n      |     |     \n   ");
			for (int i = 0; i < size; ++i) {
				for (int j = 0; j < size; ++j) {
					drawing.append(grid[i][j][k].toString());
					if (j != grid[i].length - 1) {
						drawing.append("  |  ");
					}
				}
	
				if (i != grid.length - 1) {
					drawing.append("\n _____|_____|_____\n      |     |     \n   ");
				}
			}
			drawing.append("\n      |     |    ");
		}
		return drawing.toString();
	}
}