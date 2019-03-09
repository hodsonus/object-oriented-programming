package course.oop.other.exceptions;

public class InvalidMoveException extends RuntimeException {

	private static final long serialVersionUID = 1628594511919126856L;
	private String message;

	public InvalidMoveException(String message){
	    this.message = message;
	}

	public InvalidMoveException() {
		this.message  = "";
	}
	
	@Override
	public String toString() {
		return "InvalidMoveException: " + message;
	}
}
