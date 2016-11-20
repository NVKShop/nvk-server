package hu.unideb.inf.rft.nvkshop.service;

/**
 * Invalid access exception for throwing when an unexpected arguments given by the user input.
 * 
 * @author FV
 *
 */
public class InvalidAccessException extends NvkException {

	/** Serial */
	private static final long serialVersionUID = -2557502127303456548L;

	public InvalidAccessException() {
		// TODO Auto-generated constructor stub
	}

	public InvalidAccessException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public InvalidAccessException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidAccessException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
