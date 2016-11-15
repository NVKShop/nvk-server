package hu.unideb.inf.rft.nvkshop.service.impl;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hu.unideb.inf.rft.nvkshop.LoggabeBaseServiceImpl;
import hu.unideb.inf.rft.nvkshop.entities.security.User;
import hu.unideb.inf.rft.nvkshop.entities.security.UserRegistrationRequest;
import hu.unideb.inf.rft.nvkshop.repositories.UserDao;
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
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void activateRegistration(String activationCode) {
		UserRegistrationRequest request = userRegistrationRequestSerivce.findByActivationCode(activationCode);

		Mapper dozerMapper = new DozerBeanMapper();

		User u = dozerMapper.map(request, User.class);

		userDao.save(u);
	}

}
