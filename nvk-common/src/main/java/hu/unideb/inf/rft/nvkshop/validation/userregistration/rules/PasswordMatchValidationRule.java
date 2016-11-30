package hu.unideb.inf.rft.nvkshop.validation.userregistration.rules;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import hu.unideb.inf.rft.nvkshop.entities.security.UserRegistrationRequest;
import hu.unideb.inf.rft.nvkshop.validation.ValidationRule;
import hu.unideb.inf.rft.nvkshop.validation.exception.ValidationViolation;
import hu.unideb.inf.rft.nvkshop.validation.userregistration.UserValidationViolations;

@Service
// @Qualifier("userValidation")
public class PasswordMatchValidationRule implements ValidationRule<UserRegistrationRequest> {

	@Override
	public List<ValidationViolation> validate(UserRegistrationRequest entity) {
		if (BCrypt.checkpw(entity.getPasswordConfirmation(), entity.getPassword())) {
			return Collections.EMPTY_LIST;
		} else {
			return Arrays.asList(UserValidationViolations.PASSWORD_MISMATCH);
		}
	}

}
