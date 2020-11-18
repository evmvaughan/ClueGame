package Exceptions;

public class BadConfigFormatException extends Exception {

	private static final long serialVersionUID = 1L;

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
