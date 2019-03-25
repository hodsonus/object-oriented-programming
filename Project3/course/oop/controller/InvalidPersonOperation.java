package course.oop.controller;

public class InvalidPersonOperation extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5141205723291808496L;
	private String message;

	public InvalidPersonOperation(String message){
	    this.message = message;
	}

	public InvalidPersonOperation() {
		this.message  = "";
	}
	
	@Override
	public String toString() {
		return "InvalidPersonOperation: " + message;
	}
}
