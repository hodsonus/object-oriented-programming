package course.oop.exceptions;

public class InvalidAIOperationException extends RuntimeException {

	private static final long serialVersionUID = -1382118457863381126L;
	private String message;

	public InvalidAIOperationException(String message){
	    this.message = message;
	}
	
	@Override
	public String toString() {
		return "InvalidAIOperation: " + message;
	}
}
