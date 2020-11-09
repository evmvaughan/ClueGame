package Exceptions;

public class BadConfigFormatException extends Exception {

	public BadConfigFormatException() {
		 
	}

	public BadConfigFormatException(String message) {
		super(message);
		 
	}

	public BadConfigFormatException(Throwable cause) {
		super(cause);
		 
	}

	public BadConfigFormatException(String message, Throwable cause) {
		super(message, cause);
		 
	}

	public BadConfigFormatException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		 
	}

}
