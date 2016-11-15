
package hu.unideb.inf.rft.nvkshop.validation;

public class ValidationViolation {

	private String cause;
	private String message;

	public ValidationViolation(String cause, String message) {
		this.cause = cause;
		this.message = message;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
