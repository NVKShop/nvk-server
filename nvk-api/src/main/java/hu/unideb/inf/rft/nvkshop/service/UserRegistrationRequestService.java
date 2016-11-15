package hu.unideb.inf.rft.nvkshop.service;

import hu.unideb.inf.rft.nvkshop.entities.security.UserRegistrationRequest;
import hu.unideb.inf.rft.nvkshop.validation.exception.ValidationException;

public interface UserRegistrationRequestService {

	void remove(UserRegistrationRequest request);
	
	UserRegistrationRequest findByActivationCode(String activationCode);

	void attemptRegistration(UserRegistrationRequest request) throws ValidationException;

	/**
	 * Send registration link in email by a request.
	 * 
	 * @param id the ID of the registrationRequest
	 */
	void sendRegistrationRequest(Long id);

}
