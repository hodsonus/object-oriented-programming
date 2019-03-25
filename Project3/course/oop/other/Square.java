package course.oop.other;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class Square {

    public Square() {
    	status = SquareStatus.vacant;
    	player = null;
    	updateGuiDisplay();
    }
    
    public Square(SquareStatus status) {
    	if (status == SquareStatus.occupied) throw new IllegalArgumentException("Square occupation cannot be occupied by a player by default.");
    	this.status = status;
    	updateGuiDisplay();
    }
    
    private SquareStatus status;
    private Player player;
    private Text rep;

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
    	updateGuiDisplay();
    	return true;
    }
    
    public Player getPlayer() {
    	return this.player;
    }
    
    @Override
    public String toString() {
    	if (player == null) return "@";
    	return player.getMarker();
    }
    
    public void updateGuiDisplay() {
    	
		if (player == null) {
			if (rep == null || !rep.getText().equals("-")) { //if the representation is null or the representation isnt equal to the space character
				rep = new Text("-");
			}
			else return;
		}
		else {
			String marker = player.getMarker();
			if (rep == null || !rep.getText().equals(marker)) { //if the representation is null or the representation isnt equal to the player's marker
				rep = new Text(player.getMarker());
			}
			else return;
		}
		
        EventHandler<MouseEvent> markerHandler = new EventHandler<MouseEvent>() { 
            @Override 
            public void handle(MouseEvent e) { 
            	Node source = (Node)e.getSource() ;
                Integer col = GridPane.getColumnIndex(source);
                Integer row = GridPane.getRowIndex(source);
                System.out.println("Cell clicked: " + row + "," + col);
         	}
         };
         
         rep.addEventFilter(MouseEvent.MOUSE_CLICKED, markerHandler);
    }

	public Text getGuiDisplay() {
		return rep;
	}
}