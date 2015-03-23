package org.interview.oauth.twitter;

public class TwitterException extends Exception {

	public TwitterException() {
		super();
	}

	public TwitterException(final String message) {
		super(message);
	}

	public TwitterException(final String message, final Throwable t) {
		super(message, t);
	}

	public TwitterException(final Throwable t) {
		super(t);
	}
}
