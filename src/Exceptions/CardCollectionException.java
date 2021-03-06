package Exceptions;

public class CardCollectionException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public CardCollectionException() {
		 
	}

	public CardCollectionException(String message) {
		super(message);
		 
	}

	public CardCollectionException(Throwable cause) {
		super(cause);
		 
	}

	public CardCollectionException(String message, Throwable cause) {
		super(message, cause);
		 
	}

	public CardCollectionException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		 
	}

}
