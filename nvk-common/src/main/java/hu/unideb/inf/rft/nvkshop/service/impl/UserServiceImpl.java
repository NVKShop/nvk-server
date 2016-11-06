package hu.unideb.inf.rft.nvkshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hu.unideb.inf.rft.nvkshop.entities.User;
import hu.unideb.inf.rft.nvkshop.repositories.UserDao;
import hu.unideb.inf.rft.nvkshop.service.UserService;
import lombok.Setter;

@Setter
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public User findByUserName(String username) {
		User user = userDao.findByUserName(username);
		return user;
	}

}
