package course.oop.other;

/**
 * This class is used to rpeersent a move in the TicTacToe game, with
 * pair1 corresponding to the position in the first level and pair2
 * corresponding to the move on the nested game.
 */
public class TwoPair implements Coordinate {

    /**
     * constructor
     */
    public TwoPair(OnePair pair1, OnePair pair2) {
        this.pair1 = pair1;
        this.pair2 = pair2;
    }

    /**
     * the first coordinate
     */
    public OnePair pair1;

    /**
     * the next, nested, coordingate
     */
    public OnePair pair2;
}