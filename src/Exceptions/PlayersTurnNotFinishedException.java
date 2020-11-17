package Exceptions;

public class PlayersTurnNotFinishedException extends Exception {

	private static final long serialVersionUID = 1L;

	public PlayersTurnNotFinishedException() {
		 
	}

	public PlayersTurnNotFinishedException(String message) {
		super(message);
		 
	}

	public PlayersTurnNotFinishedException(Throwable cause) {
		super(cause);
		 
	}

	public PlayersTurnNotFinishedException(String message, Throwable cause) {
		super(message, cause);
		 
	}

	public PlayersTurnNotFinishedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		 
	}

}
