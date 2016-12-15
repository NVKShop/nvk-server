package hu.unideb.inf.rft.nvkshop.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Deleted entity exception.
 * 
 * @author FV
 *
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "The requested user cannot be found")
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
