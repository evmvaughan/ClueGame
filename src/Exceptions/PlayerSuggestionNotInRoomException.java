package Exceptions;

public class PlayerSuggestionNotInRoomException extends Exception {

	private static final long serialVersionUID = 1L;

	public PlayerSuggestionNotInRoomException() {
		 
	}

	public PlayerSuggestionNotInRoomException(String message) {
		super(message);
		 
	}

	public PlayerSuggestionNotInRoomException(Throwable cause) {
		super(cause);
		 
	}

	public PlayerSuggestionNotInRoomException(String message, Throwable cause) {
		super(message, cause);
		 
	}

	public PlayerSuggestionNotInRoomException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		 
	}

}
