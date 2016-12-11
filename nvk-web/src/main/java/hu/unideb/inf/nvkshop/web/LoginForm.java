package hu.unideb.inf.nvkshop.web;

import lombok.Data;

@Data
public class LoginForm extends AbstractUserForm {

	private String j_username, j_password;
}
