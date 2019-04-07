package course.oop.other.exceptions;

public class GameNotInProgressException extends RuntimeException {

	private static final long serialVersionUID = 1628594511919126856L;
	private String message;

	public GameNotInProgressException(String message){
	    this.message = message;
	}

	public GameNotInProgressException() {
		this.message  = "";
	}
	
	@Override
	public String toString() {
		return "GameNotInProgressException: " + message;
	}
}