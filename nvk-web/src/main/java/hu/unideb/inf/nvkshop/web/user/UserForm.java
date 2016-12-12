package hu.unideb.inf.nvkshop.web.user;

import java.util.List;

import hu.unideb.inf.nvkshop.web.AbstractUserForm;
import hu.unideb.inf.rft.nvkshop.entities.security.Address;
import hu.unideb.inf.rft.nvkshop.entities.security.Language;
import lombok.Data;

@Data
public class UserForm extends AbstractUserForm {
	private String fistName;
	private String lastName;
	private String userName;
	private String phoneNumber;
	private Language language;

	private List<Address> addresses;
	private Address newAddress;

}
