package course.oop.controller;

public class GameInProgressException extends RuntimeException {

	/**
	 * TODO, auto generated
	 */
	private static final long serialVersionUID = 774824365823972441L;
	private String message;

	public GameInProgressException(String message){
	    this.message = message;
	}

	public GameInProgressException() {
		this.message  = "";
	}
	
	@Override
	public String toString() {
		return "GameInProgressException: " + message;
	}
}
