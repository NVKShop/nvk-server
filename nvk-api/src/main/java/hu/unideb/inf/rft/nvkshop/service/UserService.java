package hu.unideb.inf.rft.nvkshop.service;

import java.util.List;

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

	/**
	 * Check if a user password is the saved password.
	 * 
	 * @param userId the user id <code>not null</code>
	 * @param oldPassword the users's old password <code>not null</code>
	 * @return true if the password matches otherwise false
	 */
	boolean isMatchesForUserPassword(Long userId, String oldPassword);

	/**
	 * Reset an authenticated user password.
	 * 
	 * @param id the user id
	 * @param password the new password
	 */
	void resetPassword(Long id, String password);

	/**
	 * Return all addresses by user.
	 * 
	 * @param user the user
	 * @return the <code>list</code> of addresses
	 */
	List<Address> addressesByUser(User user);

	/**
	 * Set an user address as primary address. Also set the another addreses primary flag to false.
	 * 
	 * @param userId the userID
	 * @param addressId the addressID
	 */
	void savePrimaryAddress(Long userId, Long addressId);

	/**
	 * Delete a users address.
	 * 
	 * @param id the addres id
	 * @param userId the user id
	 */
	void deleteAddress(Long id, Long userId);

	/**
	 * Find an addres by id.
	 * 
	 * @param id the ID of the address.
	 * @return
	 */
	Address findAddressById(Long id);

	/**
	 * Update an existing address of an suer.
	 * 
	 * @param userId the user ID
	 * @param addressId the ID of the updateable address
	 * @param prototype the prototype address object
	 */
	void updateAddress(Long userId, Long addressId, Address prototype);

	void addAdmin();
}
