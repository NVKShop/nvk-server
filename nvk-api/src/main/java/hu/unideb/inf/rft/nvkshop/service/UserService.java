package hu.unideb.inf.rft.nvkshop.service;

import hu.unideb.inf.rft.nvkshop.entities.security.User;

public interface UserService {

	User findByUserName(String username);

	void activateRegistration(String activationCode);

	/**
	 * Find user by id
	 * 
	 * @param id the ID of the user
	 * @return the user or <code>null</code>
	 */
	User findById(Long id);

	User findByEmail(String email);
}
