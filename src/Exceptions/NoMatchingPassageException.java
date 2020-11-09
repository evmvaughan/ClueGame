package Exceptions;

public class NoMatchingPassageException extends Exception {

	public NoMatchingPassageException() {
		 
	}

	public NoMatchingPassageException(String message) {
		super(message);
		 
	}

	public NoMatchingPassageException(Throwable cause) {
		super(cause);
		 
	}

	public NoMatchingPassageException(String message, Throwable cause) {
		super(message, cause);
		 
	}

	public NoMatchingPassageException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		 
	}

}
