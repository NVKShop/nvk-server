package hu.unideb.inf.rft.nvkshop.validation.userregistration.rules;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import hu.unideb.inf.rft.nvkshop.entities.security.User;
import hu.unideb.inf.rft.nvkshop.entities.security.UserRegistrationRequest;
import hu.unideb.inf.rft.nvkshop.service.UserRegistrationRequestService;
import hu.unideb.inf.rft.nvkshop.service.UserService;
import hu.unideb.inf.rft.nvkshop.validation.ValidationRule;
import hu.unideb.inf.rft.nvkshop.validation.exception.ValidationViolation;
import hu.unideb.inf.rft.nvkshop.validation.userregistration.UserValidationViolations;
import lombok.Setter;

@Service
@Qualifier("userValidation")
@Setter
public class EmailUniquenessValidationRule implements ValidationRule<UserRegistrationRequest> {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRegistrationRequestService userRegistrationRequestService;

	@Override
	public List<ValidationViolation> validate(UserRegistrationRequest entity) {
		UserRegistrationRequest registrationRequest = userRegistrationRequestService.findByEmail(entity.getEmail());

		User user = userService.findByEmail(entity.getEmail());

		if (registrationRequest == null && user == null) {
			return Collections.EMPTY_LIST;
		} else {
			return Arrays.asList(UserValidationViolations.EMAIL_EXISTS);
		}
	}

}
