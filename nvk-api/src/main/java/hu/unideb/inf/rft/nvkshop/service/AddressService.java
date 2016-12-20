package hu.unideb.inf.rft.nvkshop.service;

import hu.unideb.inf.rft.nvkshop.entities.security.Address;
import hu.unideb.inf.rft.nvkshop.entities.security.User;

public interface AddressService {

	Address findById(Long id);
	
	Address getDefaultAddress(User u);
	
	Address save(Address address);
}
