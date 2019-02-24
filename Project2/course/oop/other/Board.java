package course.oop.other;

public abstract class Board<T> {

    /**
     * Default constructor
     */
    public Board() {
    }

    /**
     * 
     */
    protected T[] grid;

    /**
     * 
     */
    protected OnePair nextPos;


    /**
     * @return
     */
    public boolean checkValidMove() {
        // TODO implement here
        return false;
    }

    /**
     * @param move
     */
    public void executeMove(Pair move) {
        // TODO implement here
    }

    /**
     * @return
     */
    public String draw() {
        // TODO implement here
        return "";
    }

}