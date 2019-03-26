package course.oop.other;

import java.io.Serializable;

/**
 * 
 */
public class Person extends Player {

//	private static final long serialVersionUID = 196L;

	public Person(String username, String marker) {
		super(username, marker);
	}

	@Override
	public boolean isAI() {
		return false;
	}

	public Pair requestMove() {
        // TODO implement here
        return null;
    }

}