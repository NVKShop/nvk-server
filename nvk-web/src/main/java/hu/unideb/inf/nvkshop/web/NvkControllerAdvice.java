package hu.unideb.inf.nvkshop.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class NvkControllerAdvice {
	/** SLF4J Logger */
	private final Logger log = LoggerFactory.getLogger(NvkControllerAdvice.class);

	/**
	 * Handle errors.
	 * 
	 * @param ex the exception
	 * @return the view name
	 */
	@ExceptionHandler(Exception.class)
	public String handleErrors(Exception ex) {

		log.error("Request failed", ex);
		return "error";
	}

}
