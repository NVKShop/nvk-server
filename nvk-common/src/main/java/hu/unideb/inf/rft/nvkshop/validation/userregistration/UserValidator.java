package hu.unideb.inf.rft.nvkshop.validation.userregistration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import hu.unideb.inf.rft.nvkshop.entities.security.UserRegistrationRequest;
import hu.unideb.inf.rft.nvkshop.validation.ValidationRule;
import hu.unideb.inf.rft.nvkshop.validation.ValidationViolation;
import hu.unideb.inf.rft.nvkshop.validation.exception.ValidationException;

@Service
public class UserValidator {

	protected List<ValidationRule<UserRegistrationRequest>> rules;

	@Autowired
	@Qualifier("userValidation")
	public void setRules(List<ValidationRule<UserRegistrationRequest>> rules) {
		this.rules = rules;
	}

	public void validate(UserRegistrationRequest object) throws ValidationException {

		List<ValidationViolation> violations = new ArrayList<>();
		for (ValidationRule<UserRegistrationRequest> rule : this.rules) {
			violations.addAll(rule.validate(object));
		}

		if (!violations.isEmpty()) {
			throw new ValidationException(violations);
		}
	}
}
