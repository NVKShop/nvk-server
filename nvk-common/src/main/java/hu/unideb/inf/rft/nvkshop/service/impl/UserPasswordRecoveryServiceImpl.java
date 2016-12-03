package hu.unideb.inf.rft.nvkshop.service.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hu.unideb.inf.rft.nvkshop.entities.security.User;
import hu.unideb.inf.rft.nvkshop.entities.security.UserPasswordRecovery;
import hu.unideb.inf.rft.nvkshop.repositories.UserPasswordRecoveryDao;
import hu.unideb.inf.rft.nvkshop.service.DeletedEntityException;
import hu.unideb.inf.rft.nvkshop.service.UserPasswordRecoveryService;
import hu.unideb.inf.rft.nvkshop.service.UserService;

@Service
public class UserPasswordRecoveryServiceImpl implements UserPasswordRecoveryService {

	@Autowired
	private UserPasswordRecoveryDao passwordRecoveryDao;

	@Autowired
	private UserService userService;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void createUserPasswordRecoveryByEmail(String email) {
		UserPasswordRecovery recovery = new UserPasswordRecovery();
		UUID uuid = UUID.randomUUID();
		recovery.setActivationCode(uuid.toString());

		User user = userService.findByEmail(email);

		recovery.setUser(user);
		
		passwordRecoveryDao.save(recovery);
	}

	@Override
	@Transactional(readOnly = true)
	public UserPasswordRecovery findUserPasswordRecoveryByActivationCode(String activationCode) {

		UserPasswordRecovery passwordRecovery = passwordRecoveryDao.findByActivationCode(activationCode);

		if (passwordRecovery == null || passwordRecovery.getDueDate().after(new Date())) {
			throw new DeletedEntityException();
		}

		return passwordRecovery;
	}

}
