package course.oop.other;

import course.oop.other.exceptions.InvalidMarkerException;

public abstract class Player {

    public Player(String username, String marker) {
    	if (marker.length() != 1) throw new InvalidMarkerException();
    	if (playerRecordExists(username)) restorePlayer(username, marker);
    	else createNewPlayer(username, marker);
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
    	savePlayerState();
    }

    public void incrLosses() {
    	losses++;
    	savePlayerState();
    }

    public void incrDraws() {
    	draws++;
    	savePlayerState();
    }

    public void setMarker(String marker) {
    	if (marker.length() != 1) throw new IllegalArgumentException("Marker length should be equal to 1.");
    	this.marker = marker;
    }

    public String getRecord() {
        return "Player " + username + ": " + wins + "-" + losses + "-" + draws;
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
		//they are the same player if they have the same record string. this implies that they have the same username, wins, losses, and draws
		if (this.getRecord().equals(otherPlayer.getRecord())) return true;
		else return false;
	}
	
	@Override
	public int hashCode() {	
		return username.hashCode() + wins + losses + draws;
	}
	
	/* behavioral method */
    
	private void createNewPlayer(String username, String marker) {
		this.username = username;
		this.marker = marker;
		this.wins = 0;
		this.losses = 0;
		this.draws = 0;
		savePlayerState();
	}
		
	//implement this and save to the record based system
	private void savePlayerState() {
		//save the username and the record only
	}
    private boolean playerRecordExists(String username) {
    	return false;
    }
    private void restorePlayer(String username, String marker) {
		// Auto-generated method stub
		
	}
}