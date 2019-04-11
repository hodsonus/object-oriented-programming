package course.oop.exceptions;

public class InvalidPersonOperationException extends RuntimeException {

	private static final long serialVersionUID = -5141205723291808496L;
	private String message;

	public InvalidPersonOperationException(String message){
	    this.message = message;
	}

	@Override
	public String toString() {
		return "InvalidPersonOperation: " + message;
	}
}
