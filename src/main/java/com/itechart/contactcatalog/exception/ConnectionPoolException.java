package com.itechart.contactcatalog.exception;

public class ConnectionPoolException extends Exception {

	public ConnectionPoolException() {
		super();
	}

	public ConnectionPoolException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ConnectionPoolException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConnectionPoolException(String message) {
		super(message);
	}

	public ConnectionPoolException(Throwable cause) {
		super(cause);
	}
	
}
