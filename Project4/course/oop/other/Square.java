package course.oop.other;

import javafx.scene.text.Text;

public class Square {

    public Square() {
    	status = SquareStatus.vacant;
    	player = null;
    }
    
    public Square(SquareStatus status) {
    	if (status == SquareStatus.occupied) throw new IllegalArgumentException("Square occupation cannot be occupied by a player by default.");
    	this.status = status;
    }
    
    private SquareStatus status;
    private Player player;

    /**
     * @return the status of Square
     */
    public SquareStatus getStatus() {
        return status;
    }

    public boolean setPlayerOccupation(Player player) {
    	if (status != SquareStatus.vacant) return false;
    	status = SquareStatus.occupied;
    	this.player = player;
    	return true;
    }
    
    public Player getPlayer() {
    	return this.player;
    }
    
    @Override
    public String toString() {
    	if (player == null) return "-";
    	return player.getMarker();
    }

	public Text getGuiDisplay() {
		if (player == null) return new Text("-");
		else {
			return new Text(player.getMarker());
		}
	}
}