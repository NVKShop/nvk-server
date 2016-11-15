package hu.unideb.inf.rft.nvkshop.validation.userregistration.rules;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import hu.unideb.inf.rft.nvkshop.entities.security.IdentifyableUserBaseEntity;
import hu.unideb.inf.rft.nvkshop.entities.security.UserRegistrationRequest;
import hu.unideb.inf.rft.nvkshop.service.IdentifyableUserBaseEntityService;
import hu.unideb.inf.rft.nvkshop.validation.ValidationRule;
import hu.unideb.inf.rft.nvkshop.validation.exception.ValidationViolation;
import hu.unideb.inf.rft.nvkshop.validation.userregistration.UserValidationViolations;

@Service
@Qualifier("userValidation")
public class UserNameValidationRule implements ValidationRule<UserRegistrationRequest> {

	@Autowired
	private IdentifyableUserBaseEntityService identifyableUserBaseEntityService;

	@Override
	public List<ValidationViolation> validate(UserRegistrationRequest entity) {

		IdentifyableUserBaseEntity abstractUser = identifyableUserBaseEntityService.findByUserName(entity.getUserName());
		
		if (abstractUser == null) {
			return Collections.emptyList();
		} else {
			return Arrays.asList(UserValidationViolations.USERNAME_EXISTS);
		}
	}

}
