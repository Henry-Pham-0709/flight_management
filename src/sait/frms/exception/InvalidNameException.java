package sait.frms.exception;

public class InvalidNameException extends Exception {
	public InvalidNameException(String e) {
		super(e + " Must Be Entered");
	}
}
