package hu.unideb.inf.rft.nvkshop.service;

/**
 * Application settings.
 * 
 * @author FV
 *
 */
public class Settings {

	private String baseUrl;
	private String defaultLanguage;
	private String fromAddress;

	public Settings() {
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getDefaultLanguage() {
		return defaultLanguage;
	}

	public void setDefaultLanguage(String defaultLanguage) {
		this.defaultLanguage = defaultLanguage;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

}
