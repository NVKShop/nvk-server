package hu.unideb.inf.nvkshop.web;

import lombok.Data;

@Data
public class PasswordRecoveryForm extends AbstractUserForm {

	private Long id;
	private String password;
	private String passwordConfirm;
	private String activationCode;

}
