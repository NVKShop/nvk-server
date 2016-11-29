package hu.unideb.inf.nvkshop.web;

import hu.unideb.inf.rft.nvkshop.entities.security.Language;
import lombok.Data;

@Data
public class UserForm {
	private long id;
	private String fistName;
	private String lastName;
	private String userName;
	private String phoneNumber;
	private Language language;

}
