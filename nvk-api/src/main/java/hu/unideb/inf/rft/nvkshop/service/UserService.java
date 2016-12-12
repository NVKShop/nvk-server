package hu.unideb.inf.rft.nvkshop.service;

import hu.unideb.inf.rft.nvkshop.entities.security.Address;
import hu.unideb.inf.rft.nvkshop.entities.security.Language;
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

	/**
	 * Find user by email
	 * 
	 * @param email the email
	 * @return the user or <code>null<
	 */
	User findByEmail(String email);

	/**
	 * Editing user basic informations
	 * 
	 * @param id the user id
	 * @param firstName the first name to change
	 * @param lastName the last name to change
	 * @param phoneNumber the phoneNumber to change
	 * @param language the user selected language of the system
	 */
	void editUserBasicDatas(Long id, String firstName, String lastName, String phoneNumber, Language language);

	/**
	 * Find user by name.
	 * 
	 * @param userName the user name
	 * @return the user or <code>null</code>
	 */
	User findByName(String userName);

	/**
	 * Add new address to a user.
	 * 
	 * @param id the usre id
	 * @param newAddress the new address
	 */
	void addUserAddress(long id, Address newAddress);
}
