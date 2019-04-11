package course.oop.exceptions;

public class GameInProgressException extends RuntimeException {

	private static final long serialVersionUID = 774824365823972441L;
	private String message;

	public GameInProgressException(String message){
	    this.message = message;
	}
	
	@Override
	public String toString() {
		return "GameInProgressException: " + message;
	}
}
