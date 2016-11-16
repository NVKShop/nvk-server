package hu.unideb.inf.nvkshop.web;

import lombok.Data;

@Data
public class PasswordRecoveryForm {

	private Long id;
	private String password;
	private String passwordAgain;

}
