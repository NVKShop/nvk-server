package hu.unideb.inf.rft.nvkshop.service;

/**
 * 
 * Banned user exception. Throw when a user try to do somethign with a banned user.
 * 
 * @author FV
 *
 */
public class BannedUserException extends NvkException {

	/** Serial */
	private static final long serialVersionUID = 6127072444396766791L;

	public BannedUserException() {
	}

	public BannedUserException(String message) {
		super(message);
	}

	public BannedUserException(Throwable cause) {
		super(cause);
	}

	public BannedUserException(String message, Throwable cause) {
		super(message, cause);
	}

}
