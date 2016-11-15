package hu.unideb.inf.rft.nvkshop.validation.exception;

import java.util.List;

import hu.unideb.inf.rft.nvkshop.validation.ValidationViolation;

public class ValidationException extends Exception {

	private static final long serialVersionUID = 1L;
	
	List<ValidationViolation> violations;

	public ValidationException(List<ValidationViolation> violations) {
		this.violations = violations;
	}

	public List<ValidationViolation> getViolations() {
		return violations;
	}

}
