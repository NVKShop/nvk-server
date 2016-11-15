package hu.unideb.inf.rft.nvkshop.service;

/**
 * Deleted entity exception.
 * 
 * @author FV
 *
 */
public class DeletedEntityException extends NvkException {

	/** Serial number */
	private static final long serialVersionUID = -7391330687729326813L;

	/**
	 * Deleted entity exception.
	 */
	public DeletedEntityException() {
		super();
	}

	/**
	 * Deleted entity exception.
	 * 
	 * @param message the message
	 */
	public DeletedEntityException(String message) {
		super(message);
	}
}
