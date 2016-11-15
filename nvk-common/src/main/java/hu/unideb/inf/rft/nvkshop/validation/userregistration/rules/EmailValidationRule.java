package hu.unideb.inf.rft.nvkshop.validation.userregistration.rules;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import hu.unideb.inf.rft.nvkshop.entities.security.UserRegistrationRequest;
import hu.unideb.inf.rft.nvkshop.validation.ValidationRule;
import hu.unideb.inf.rft.nvkshop.validation.ValidationViolation;

@Service
@Qualifier("userValidation")
public class EmailValidationRule implements ValidationRule<UserRegistrationRequest> {

	private static final String EMAIL_REGEXP = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

	@Override
	public List<ValidationViolation> validate(UserRegistrationRequest entity) {

		if (entity.getEmail().matches(EMAIL_REGEXP)) {
			return Collections.EMPTY_LIST;
		} else {
			return Arrays.asList(new ValidationViolation("email address is not valid",
					"the given email address is not a valid one"));
		}

	}

}
