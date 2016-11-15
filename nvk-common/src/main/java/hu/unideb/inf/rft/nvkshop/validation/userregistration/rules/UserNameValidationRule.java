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

@Service
@Qualifier("userValidation")
public class UserNameValidationRule implements ValidationRule<UserRegistrationRequest> {

	@Autowired
	private UserRegistrationRequestService userRegistrationRequestService;

	@Autowired
	private UserService userService;

	@Override
	public List<ValidationViolation> validate(UserRegistrationRequest entity) {
		UserRegistrationRequest registrationRequest = userRegistrationRequestService
				.findByUserName(entity.getUserName());

		User user = userService.findByUserName(entity.getUserName());
		if (registrationRequest == null && user == null) {
			return Collections.emptyList();
		} else {
			return Arrays.asList(UserValidationViolations.USERNAME_EXISTS);
		}
	}

}
