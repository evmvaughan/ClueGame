package Exceptions;

public class NotPlayersTurnException extends Exception {

	private static final long serialVersionUID = 1L;

	public NotPlayersTurnException() {
		 
	}

	public NotPlayersTurnException(String message) {
		super(message);
		 
	}

	public NotPlayersTurnException(Throwable cause) {
		super(cause);
		 
	}

	public NotPlayersTurnException(String message, Throwable cause) {
		super(message, cause);
		 
	}

	public NotPlayersTurnException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		 
	}

}
