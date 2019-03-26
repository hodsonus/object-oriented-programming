package course.oop.other;

public class AI extends Player {
	
	public AI(String username, String marker) {
    	super(username, marker);
    }
	
	@Override
	public boolean isAI() {
		return true;
	}
}