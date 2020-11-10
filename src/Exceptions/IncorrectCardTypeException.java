package Exceptions;

public class IncorrectCardTypeException extends Exception {

	private static final long serialVersionUID = 1L;

	public IncorrectCardTypeException() {
		 
	}

	public IncorrectCardTypeException(String message) {
		super(message);
		 
	}

	public IncorrectCardTypeException(Throwable cause) {
		super(cause);
		 
	}

	public IncorrectCardTypeException(String message, Throwable cause) {
		super(message, cause);
		 
	}

	public IncorrectCardTypeException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		 
	}

}
