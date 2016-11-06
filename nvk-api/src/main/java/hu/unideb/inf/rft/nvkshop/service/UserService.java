package hu.unideb.inf.rft.nvkshop.service;

import hu.unideb.inf.rft.nvkshop.entities.User;

public interface UserService {

	public User findByUserName(String username);

}
