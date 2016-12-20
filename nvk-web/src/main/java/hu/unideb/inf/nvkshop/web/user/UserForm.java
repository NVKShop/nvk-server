package hu.unideb.inf.nvkshop.web.user;

import java.util.List;

import hu.unideb.inf.nvkshop.web.AbstractUserForm;
import hu.unideb.inf.rft.nvkshop.entities.product.Item;
import hu.unideb.inf.rft.nvkshop.entities.security.Address;
import lombok.Data;

@Data
public class UserForm extends AbstractUserForm {
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String email;
	private String password;

	private List<Address> addresses;
	private Address newAddress;
	private List<Item> cart;

}
