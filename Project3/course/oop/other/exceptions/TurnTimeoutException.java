package course.oop.other.exceptions;

public class TurnTimeoutException extends RuntimeException {

	private static final long serialVersionUID = -3850828551779468365L;
	private String message;

	public TurnTimeoutException(String message){
	    this.message = message;
	}

	public TurnTimeoutException() {
		this.message  = "";
	}
	
	@Override
	public String toString() {
		return "TurnTimeoutException: " + message;
	}
}
