package com.itechart.contactcatalog.exception;

public class UploadException extends Exception {

	public UploadException() {
	}

	public UploadException(String message) {
		super(message);
	}

	public UploadException(Throwable cause) {
		super(cause);
	}

	public UploadException(String message, Throwable cause) {
		super(message, cause);
	}

	public UploadException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
