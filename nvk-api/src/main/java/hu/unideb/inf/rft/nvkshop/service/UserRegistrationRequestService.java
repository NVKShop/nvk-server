package hu.unideb.inf.rft.nvkshop.service;

import hu.unideb.inf.rft.nvkshop.entities.security.UserPasswordRecovery;
import hu.unideb.inf.rft.nvkshop.entities.security.UserRegistrationRequest;
import hu.unideb.inf.rft.nvkshop.validation.exception.ValidationException;

public interface UserRegistrationRequestService {

	void remove(UserRegistrationRequest request);

	UserRegistrationRequest findByActivationCode(String activationCode);

	void attemptRegistration(UserRegistrationRequest request) throws ValidationException;

	UserRegistrationRequest findByEmail(String email);

	UserRegistrationRequest findByUserName(String userName);

	UserPasswordRecovery findUserPasswordRecoveryByActivationCode(String activationCode);
}
