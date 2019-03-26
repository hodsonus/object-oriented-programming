package course.oop.other;

public class Person extends Player {

	public Person(String username, String marker) {
		super(username, marker);
	}

	@Override
	public boolean isAI() {
		return false;
	}
}