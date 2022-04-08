	package sait.frms.exception;

public class InvalidCitizenshipException extends Exception {
	public InvalidCitizenshipException(String e) {
		super(e + " Must Be Entered");
	}
}
