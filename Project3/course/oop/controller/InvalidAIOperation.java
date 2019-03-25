package course.oop.controller;

public class InvalidAIOperation extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1382118457863381126L;
	private String message;

	public InvalidAIOperation(String message){
	    this.message = message;
	}

	public InvalidAIOperation() {
		this.message  = "";
	}
	
	@Override
	public String toString() {
		return "InvalidAIOperation: " + message;
	}

}
