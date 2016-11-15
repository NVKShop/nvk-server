package hu.unideb.inf.rft.nvkshop.service;

/**
 * The NVK exception main class.
 * 
 * @author FV
 *
 */
public class NvkException extends RuntimeException {

	/** Serial number */
	private static final long serialVersionUID = 3344742703887286152L;

	public NvkException() {
		super();
	}

	public NvkException(String message) {
		super(message);
	}

	public NvkException(Throwable cause) {
		super(cause);
	}

	public NvkException(String message, Throwable cause) {
		super(message, cause);
	}

}
