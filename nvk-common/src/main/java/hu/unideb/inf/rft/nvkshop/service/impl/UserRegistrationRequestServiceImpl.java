package hu.unideb.inf.rft.nvkshop.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hu.unideb.inf.rft.nvkshop.LoggabeBaseServiceImpl;
import hu.unideb.inf.rft.nvkshop.entities.security.UserRegistrationRequest;
import hu.unideb.inf.rft.nvkshop.repositories.UserRegistrationRequestDao;
import hu.unideb.inf.rft.nvkshop.service.UserRegistrationRequestService;
import hu.unideb.inf.rft.nvkshop.validation.exception.ValidationException;
import hu.unideb.inf.rft.nvkshop.validation.userregistration.UserValidator;
import lombok.Setter;

@Service
@Setter
public class UserRegistrationRequestServiceImpl extends LoggabeBaseServiceImpl
		implements UserRegistrationRequestService {

	@Autowired
	private UserRegistrationRequestDao userRegistrationRequestDao;

	@Autowired
	private UserValidator validator;

	@Override
	public UserRegistrationRequest findByActivationCode(String activationCode) {
		return userRegistrationRequestDao.findByActivationCode(activationCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void attemptRegistration(UserRegistrationRequest request) throws ValidationException {
		request.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
		request.setActivationCode(UUID.randomUUID().toString());

		validator.validate(request);

		userRegistrationRequestDao.save(request);
	}

}
