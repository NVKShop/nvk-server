package hu.unideb.inf.rft.nvkshop.service.impl;

import java.util.Date;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hu.unideb.inf.rft.nvkshop.entities.security.Address;
import hu.unideb.inf.rft.nvkshop.entities.security.Language;
import hu.unideb.inf.rft.nvkshop.entities.security.User;
import hu.unideb.inf.rft.nvkshop.entities.security.UserRegistrationRequest;
import hu.unideb.inf.rft.nvkshop.repositories.AddressDao;
import hu.unideb.inf.rft.nvkshop.repositories.UserDao;
import hu.unideb.inf.rft.nvkshop.service.DeletedEntityException;
import hu.unideb.inf.rft.nvkshop.service.InvalidAccessException;
import hu.unideb.inf.rft.nvkshop.service.UserRegistrationRequestService;
import hu.unideb.inf.rft.nvkshop.service.UserService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Setter
@Service("userService")
@Slf4j
public class UserServiceImpl extends AbstrackNvkService implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private AddressDao addressDao;

	@Autowired
	private UserRegistrationRequestService userRegistrationRequestSerivce;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public User findByUserName(String username) {
		User user = userDao.findByUserName(username);
		log.debug("Finding user named {}", username);
		return user;
	}

	@Override
	@Transactional
	public User findById(Long id) {
		User user = userDao.getOne(id);
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
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void editUserBasicDatas(Long id, String firstName, String lastName, String phoneNumber, Language selectedLanguage) {

		User user = findById(id);
		if (user == null) {
			throw new DeletedEntityException();
		}
		// if he is banned, login must be denied
		// FIXME: not important
		// if (user.getBanned()) {
		// throw new BannedUserException();
		// }

		if (!(selectedLanguage instanceof Language)) {
			throw new InvalidAccessException();
		}
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setPhoneNumber(phoneNumber);
		user.setDateOfModification(new Date());
		user.setLanguage(selectedLanguage);

		log.info("User modification saved id: {} ", id);
		userDao.saveAndFlush(user);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void addUserAddress(long id, Address newAddress) {

		User user = findById(id);
		if (user == null) {
			throw new DeletedEntityException();
		}

		Address address = new Address();
		address.setCountry(newAddress.getCountry());
		address.setDateOfCreation(dateOf(now()));
		address.setPrimary(false);
		address.setRecipient(newAddress.getRecipient());
		address.setStreet(newAddress.getStreet());
		address.setUser(user);
		log.info("New address uploaded by user id = {}", user.getId());

		addressDao.saveAndFlush(address);

	}

	@Override
	@Transactional(readOnly = true)
	public boolean isMatchesForUserPassword(Long userId, String oldPassword) {
		// Assert.assertNotNull(userId);
		// Assert.assertNotNull(oldPassword);

		User user = userDao.findOne(userId);
		if (user == null) {
			throw new DeletedEntityException();
		}

		return BCrypt.checkpw(oldPassword, user.getPassword());

	}

	@Override
	@Transactional
	public void resetPassword(Long id, String password) {

		User user = userDao.findOne(id);
		if (user == null) {
			throw new DeletedEntityException();
		}

		user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
		userDao.saveAndFlush(user);
	}

	@Override
	public User findByEmail(String email) {
		return userDao.findByEmail(email);
	}

	@Override
	public User findByName(String userName) {
		return userDao.findByUserName(userName);
	}

}
