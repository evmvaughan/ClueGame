package Exceptions;

public class GameIsNotRunningException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public GameIsNotRunningException() {
		 
	}

	public GameIsNotRunningException(String message) {
		super(message);
		 
	}

	public GameIsNotRunningException(Throwable cause) {
		super(cause);
		 
	}

	public GameIsNotRunningException(String message, Throwable cause) {
		super(message, cause);
		 
	}

	public GameIsNotRunningException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		 
	}

}
