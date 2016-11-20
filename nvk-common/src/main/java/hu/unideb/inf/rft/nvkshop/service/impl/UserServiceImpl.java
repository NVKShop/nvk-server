package hu.unideb.inf.rft.nvkshop.service.impl;

import java.util.Date;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hu.unideb.inf.rft.nvkshop.LoggabeBaseServiceImpl;
import hu.unideb.inf.rft.nvkshop.entities.security.Language;
import hu.unideb.inf.rft.nvkshop.entities.security.User;
import hu.unideb.inf.rft.nvkshop.entities.security.UserRegistrationRequest;
import hu.unideb.inf.rft.nvkshop.repositories.UserDao;
import hu.unideb.inf.rft.nvkshop.service.BannedUserException;
import hu.unideb.inf.rft.nvkshop.service.DeletedEntityException;
import hu.unideb.inf.rft.nvkshop.service.InvalidAccessException;
import hu.unideb.inf.rft.nvkshop.service.UserRegistrationRequestService;
import hu.unideb.inf.rft.nvkshop.service.UserService;
import lombok.Setter;

@Setter
@Service
public class UserServiceImpl extends LoggabeBaseServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private UserRegistrationRequestService userRegistrationRequestSerivce;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public User findByUserName(String username) {
		User user = userDao.findByUserName(username);
		logger.debug("Finding user named {}", username);
		return user;
	}

	@Override
	@Transactional
	public User findById(Long id) {
		User user = userDao.findById(id);
		return user;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void activateRegistration(String activationCode) {

		UserRegistrationRequest request = userRegistrationRequestSerivce.findByActivationCode(activationCode);

		if (request == null) {
			throw new DeletedEntityException();
		}
		Mapper dozerMapper = new DozerBeanMapper();

		User u = dozerMapper.map(request, User.class);

		userDao.save(u);
		userRegistrationRequestSerivce.remove(request);
	}

	@Override
	@Transactional
	public void editUserBasicDatas(Long id, String firstName, String lastName, String phoneNumber, Language selectedLanguage) {

		User user = findById(id);
		if (user == null) {
			throw new DeletedEntityException();
		}
		if (user.getBanned().booleanValue()) {
			throw new BannedUserException();
		}

		// FIXME: not important
		if (!(selectedLanguage instanceof Language)) {
			throw new InvalidAccessException();
		}
		user.setFirstName(firstName);
		user.setLastName(lastName);
		// TODO: validate passwd
		user.setPhoneNumber(phoneNumber);
		user.setDateOfModification(new Date());
		user.setLanguage(selectedLanguage);
		logger.info("User modification saved id: {} ", id);
		userDao.saveAndFlush(user);

	}

	@Override
	public User findByEmail(String email) {
		return userDao.findByEmail(email);
	}

}
