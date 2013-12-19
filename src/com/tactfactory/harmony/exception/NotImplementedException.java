package com.tactfactory.harmony.exception;

/**
 * Exception used when a method has not been implemented yet.
 */
public class NotImplementedException extends RuntimeException {
	/**
	 * Serial version UID.
	 */
	private static final long	serialVersionUID	= 3948550626851159546L;

	/**
	 * Constructor.
	 * @param message The exception message
	 */
	public NotImplementedException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * @param message The exception message
	 * @param throwable The associated Throwable
	 */
	public NotImplementedException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
