package hu.unideb.inf.rft.nvkshop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * This is a abstract class to create logger for service implementation classes. It is advised to inherit from this class. 
 *
 */
public abstract class LoggabeBaseServiceImpl {

	protected Logger logger;

	public LoggabeBaseServiceImpl() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

}
