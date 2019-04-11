package course.oop.exceptions;

public class InvalidMarkerException extends RuntimeException {

	private static final long serialVersionUID = -7846734642612681441L;
	private String message;

	public InvalidMarkerException(String message){
	    this.message = message;
	}
	
	@Override
	public String toString() {
		return "InvalidMarkerException: " + message;
	}
}
