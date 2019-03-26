package course.oop.other;

import java.io.Serializable;

import course.oop.other.exceptions.InvalidMarkerException;

public abstract class Player implements Serializable {

	private static final long serialVersionUID = 196L;

	public Player(String username, String marker) {
    	this.wins = 0;
    	this.losses = 0;
    	this.draws = 0;
    	this.username = username;
    	setMarker(marker);
    }

	private int wins;
    private int losses;
    private int draws;
    private String username;
    private String marker;
    private boolean isAI;
    
    /* getters and setters */

    public String getMarker() {
        return marker;
    }
    
    public boolean isAI() {
    	return isAI;
    }
    
    public void incrWins() {
    	wins++;
    }

    public void incrLosses() {
    	losses++;
    }

    public void incrDraws() {
    	draws++;
    }

    public void setMarker(String marker) {
    	if (marker.length() != 1) throw new InvalidMarkerException("Marker length should be equal to 1.");
    	this.marker = marker;
    }

    public String getRecord() {
        return this.toString();
    }
    
    public int getWins() {
    	return wins;
    }

    public int getLosses() {
        return losses;
    }
  
    public int getDraws() {
        return draws;
    }
    
	public String getUsername() {
		return username;
	}
	
	/* overriden methods from Object */
	
	@Override
	public boolean equals(Object a) {
		if (!(a instanceof Player)) return false;
		Player otherPlayer = (Player)a;
		//they are the same player if they have the same username
		if (this.username.equals(otherPlayer.getUsername())) return true;
		else return false;
	}
	
	@Override
	public int hashCode() {	
		return username.hashCode() + wins + losses + draws;
	}
	
	@Override
	public String toString() {
		return username + " (" + wins + "-" + losses + "-" + draws + ")";
	}
}