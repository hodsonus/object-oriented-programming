package course.oop.other;

public class AI extends Player {

	private static final long serialVersionUID = 6999478579199047626L;

	public AI(String username, String marker) {
    	super(username, marker);
    }
	
	@Override
	public boolean isAI() {
		return true;
	}
}