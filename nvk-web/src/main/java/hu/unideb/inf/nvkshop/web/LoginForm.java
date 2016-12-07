package hu.unideb.inf.nvkshop.web;

import lombok.Data;

@Data
public class LoginForm extends AbstractUserForm {

	private String userName, password;

	public LoginForm() {
	}

	public LoginForm(String userName) {
		this.userName = userName;
	}
}
