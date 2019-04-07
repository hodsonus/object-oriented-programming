package course.oop.other;

public class Person extends Player {

	private static final long serialVersionUID = -2970541628412585499L;

	public Person(String username, String marker) {
		super(username, marker);
	}

	@Override
	public boolean isAI() {
		return false;
	}
}