package hu.unideb.inf.rft.nvkshop.service;

import hu.unideb.inf.rft.nvkshop.entities.security.UserPasswordRecovery;

public interface UserPasswordRecoveryService {

	UserPasswordRecovery findUserPasswordRecoveryByActivationCode(String activationCode);

	void createUserPasswordRecoveryByEmail(String email);

}
