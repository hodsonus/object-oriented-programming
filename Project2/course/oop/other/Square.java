package course.oop.other;

public class Square {

    /**
     * Default constructor
     */
    public Square(SquareStatus status) {
    }

    private boolean isOccupiedP1;
    private boolean isOccupiedP2;
    private boolean isNeutral;
    private boolean isUnpickable;

    /**
     * @return the status of Square
     */
    public SquareStatus getStatus() {
        if (isUnpickable) {
            return SquareStatus.unpickable;
        }
        else if (isNeutral) {
            return SquareStatus.neutral;
        }
        else if (isOccupiedP1) {
            return SquareStatus.occupiedP1;
        }
        else if (isOccupiedP2) {
            return SquareStatus.occupiedP2;
        }
        else {
            return SquareStatus.vacant;
        }
    }

    /**
     * @param status will become the status of the Square
     */
    public void setStatus(SquareStatus status) {
        
        isOccupiedP1 = false;
        isOccupiedP2 = false;
        isNeutral = false;
        isUnpickable = false;

        if (status == SquareStatus.unpickable) {

            isUnpickable = true;
        }
        else if (status == SquareStatus.neutral) {

            isNeutral = true;
        }
        else if (status == SquareStatus.occupiedP1) {

            isOccupiedP1 = true;
        }
        else if (status == SquareStatus.occupiedP2) {

            isOccupiedP2 = true;
        }

        //else, status == SquareStatus.vacant, and we do nothing more
    }
}