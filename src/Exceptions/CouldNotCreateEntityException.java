package Exceptions;

public class CouldNotCreateEntityException extends Exception {

	public CouldNotCreateEntityException() {
		 
	}

	public CouldNotCreateEntityException(String message) {
		super(message);
		 
	}

	public CouldNotCreateEntityException(Throwable cause) {
		super(cause);
		 
	}

	public CouldNotCreateEntityException(String message, Throwable cause) {
		super(message, cause);
		 
	}

	public CouldNotCreateEntityException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		 
	}

}
