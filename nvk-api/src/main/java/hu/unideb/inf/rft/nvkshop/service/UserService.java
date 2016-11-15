package hu.unideb.inf.rft.nvkshop.service;


import hu.unideb.inf.rft.nvkshop.entities.security.User;

public interface UserService {

	User findByUserName(String username);

	void activateRegistration(String activationCode);
	
	User findByEmail(String email);
	
}
