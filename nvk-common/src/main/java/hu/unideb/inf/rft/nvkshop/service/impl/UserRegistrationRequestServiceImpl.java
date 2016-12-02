package hu.unideb.inf.rft.nvkshop.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hu.unideb.inf.rft.nvkshop.entities.security.UserRegistrationRequest;
import hu.unideb.inf.rft.nvkshop.eventhandling.EmailSendingEvent;
import hu.unideb.inf.rft.nvkshop.eventhandling.EventType;
import hu.unideb.inf.rft.nvkshop.logging.Log;
import hu.unideb.inf.rft.nvkshop.repositories.UserPasswordRecoveryDao;
import hu.unideb.inf.rft.nvkshop.repositories.UserRegistrationRequestDao;
import hu.unideb.inf.rft.nvkshop.service.Settings;
import hu.unideb.inf.rft.nvkshop.service.UserRegistrationRequestService;
import hu.unideb.inf.rft.nvkshop.validation.exception.ValidationException;
import hu.unideb.inf.rft.nvkshop.validation.userregistration.UserValidator;

@Service
public class UserRegistrationRequestServiceImpl implements UserRegistrationRequestService {

	@Autowired
	private Settings settings;

	@Log
	private Logger logger;

	@Autowired
	private UserRegistrationRequestDao userRegistrationRequestDao;

	@Autowired
	private UserValidator validator;

	@Autowired
	private UserPasswordRecoveryDao passwordRecoveryDao;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

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

		request = userRegistrationRequestDao.save(request);

		String activationCode = request.getActivationCode();
		String locale = settings.getDefaultLanguage();
		String url = settings.getBaseUrl() + "/registration/activation.html?activationCode=" + activationCode
				+ "&locale=" + locale;

		Map<String, Object> emailParameters = new HashMap<>();
		emailParameters.put("url", url);
		emailParameters.put("userName", request.getUserName());
		String email = request.getEmail();

		EmailSendingEvent activationEmailSenderEvent = new EmailSendingEvent(EventType.REGISTRATION_ACTIVATION,
				emailParameters, email);

		eventPublisher.publishEvent(activationEmailSenderEvent);
		logger.info("The user {} was successfully registrated with activation code: {}", request.getUserName(),
				request.getActivationCode());
	}

	

	@Override
	public void remove(UserRegistrationRequest request) {
		userRegistrationRequestDao.delete(request);
	}

	@Override
	public UserRegistrationRequest findByEmail(String email) {
		return userRegistrationRequestDao.findByEmail(email);
	}

	@Override
	public UserRegistrationRequest findByUserName(String userName) {
		return userRegistrationRequestDao.findByUserName(userName);
	}

}
