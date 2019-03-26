package course.oop.other;

import java.io.Serializable;

/**
 * 
 */
public class AI extends Player {
	
//	private static final long serialVersionUID = 196L;

	public AI(String username, String marker) {
    	super(username, marker);
    }
	
	@Override
	public boolean isAI() {
		return true;
	}

    /**
     * @return
     */
    public Pair requestMove() {
        // TODO implement here
        return null;
    }

}