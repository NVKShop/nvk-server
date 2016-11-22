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

	/**
	 * Send registration link in email by a request.
	 * 
	 * @param id the ID of the registrationRequest
	 */
	void sendRegistrationRequest(Long id);

	/**
	 * Find unexpired user password recovery by activation code.
	 * 
	 * @param activationCode the activation code not <code>null</code>
	 * @return the password recovery entity or empty reference
	 */
	UserPasswordRecovery findUserPasswordRecoveryByActivationCode(String activationCode);

}
