package course.oop.other;

public abstract class Player {

    /**
     * Default constructor
     */
    public Player() {
    }

    /**
     * 
     */
    private int wins;

    /**
     * 
     */
    private int losses;

    /**
     * 
     */
    private int draws;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private String marker;
    
    private boolean isAI;


    /**
     * @return
     */
    public String getMarker() {
        // TODO implement here
        return "";
    }
    
    public boolean isAI() {
    	return isAI;
    }

    /**
     * 
     */
    public void incrWins() {
        // TODO implement here
    }

    /**
     * 
     */
    public void incrLosses() {
        // TODO implement here
    }

    /**
     * 
     */
    public void incrDraws() {
        // TODO implement here
    }

    /**
     * @param marker
     */
    public void setMarker(String marker) {
        // TODO implement here
    }

    /**
     * @return
     */
    public Pair requestMove() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public String getRecord() {
        // TODO implement here
        return "";
    }

    /**
     * @return
     */
    public int getWins() {
        // TODO implement here
        return 0;
    }

    /**
     * @return
     */
    public int getLosses() {
        // TODO implement here
        return 0;
    }

    /**
     * @return
     */
    public int getDraws() {
        // TODO implement here
        return 0;
    }

}