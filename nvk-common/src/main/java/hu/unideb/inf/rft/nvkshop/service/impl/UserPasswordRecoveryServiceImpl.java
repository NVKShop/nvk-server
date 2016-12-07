package hu.unideb.inf.rft.nvkshop.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hu.unideb.inf.rft.nvkshop.entities.security.User;
import hu.unideb.inf.rft.nvkshop.entities.security.UserPasswordRecovery;
import hu.unideb.inf.rft.nvkshop.eventhandling.EmailSendingEvent;
import hu.unideb.inf.rft.nvkshop.eventhandling.EventType;
import hu.unideb.inf.rft.nvkshop.repositories.UserDao;
import hu.unideb.inf.rft.nvkshop.repositories.UserPasswordRecoveryDao;
import hu.unideb.inf.rft.nvkshop.service.DeletedEntityException;
import hu.unideb.inf.rft.nvkshop.service.Settings;
import hu.unideb.inf.rft.nvkshop.service.UserPasswordRecoveryService;
import hu.unideb.inf.rft.nvkshop.service.UserService;

@Service
public class UserPasswordRecoveryServiceImpl extends AbstrackNvkService implements UserPasswordRecoveryService {

	@Autowired
	private UserPasswordRecoveryDao passwordRecoveryDao;

	@Autowired
	private UserService userService;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private Settings settings;

	@Autowired
	private UserDao userDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void createUserPasswordRecoveryByEmail(String email) {
		UserPasswordRecovery recovery = new UserPasswordRecovery();
		UUID uuid = UUID.randomUUID();
		recovery.setActivationCode(uuid.toString());

		User user = userService.findByEmail(email);

		if (user == null) {
			throw new DeletedEntityException();
		}
		String activationCode = recovery.getActivationCode();
		String locale = settings.getDefaultLanguage();
		String url = settings.getBaseUrl() + "/passwordrecover.html?activationCode=" + activationCode + "&locale=" + locale;

		Map<String, Object> emailParameters = new HashMap<>();
		emailParameters.put("url", url);
		emailParameters.put("userName", user.getUserName());
		recovery.setDueDate(dateOf(now().plusDays(30)));
		recovery.setUser(user);
		passwordRecoveryDao.save(recovery);

		EmailSendingEvent event = new EmailSendingEvent(EventType.PASSWORD_RECOVERY, emailParameters, email);
		publisher.publishEvent(event);
	}

	@Override
	@Transactional(readOnly = true)
	public UserPasswordRecovery findUserPasswordRecoveryByActivationCode(String activationCode) {

		UserPasswordRecovery passwordRecovery = passwordRecoveryDao.findByActivationCode(activationCode);

		if (passwordRecovery == null || passwordRecovery.getDueDate().before(new Date())) {
			throw new DeletedEntityException();
		}

		return passwordRecovery;
	}

	@Override
	@Transactional
	public void resetPassword(String activationCode, String password) {
		UserPasswordRecovery passwordRecovery = passwordRecoveryDao.findByActivationCode(activationCode);

		if (passwordRecovery == null || passwordRecovery.getDueDate().before(new Date())) {
			throw new DeletedEntityException();
		}

		User user = userDao.findOne(passwordRecovery.getUser().getId());

		if (user == null) {
			throw new DeletedEntityException();
		}

		user.setPassword(password);
		userDao.saveAndFlush(user);

	}

}
