package Exceptions;

public class TooManyCardsInCollectionException extends Exception {

	private static final long serialVersionUID = 1L;

	public TooManyCardsInCollectionException() {
		 
	}

	public TooManyCardsInCollectionException(String message) {
		super(message);
		 
	}

	public TooManyCardsInCollectionException(Throwable cause) {
		super(cause);
		 
	}

	public TooManyCardsInCollectionException(String message, Throwable cause) {
		super(message, cause);
		 
	}

	public TooManyCardsInCollectionException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		 
	}

}
