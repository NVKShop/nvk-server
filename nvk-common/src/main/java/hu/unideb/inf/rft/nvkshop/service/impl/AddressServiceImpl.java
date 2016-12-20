package hu.unideb.inf.rft.nvkshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.unideb.inf.rft.nvkshop.entities.security.Address;
import hu.unideb.inf.rft.nvkshop.entities.security.User;
import hu.unideb.inf.rft.nvkshop.repositories.AddressDao;
import hu.unideb.inf.rft.nvkshop.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressDao addressDao;

	@Override
	public Address findById(Long id) {
		return addressDao.findOne(id);
	}

	@Override
	public Address getDefaultAddress(User u) {
		return addressDao.findByUserAndIsPrimary(u, true);
	}

	@Override
	public Address save(Address address) {
		return addressDao.save(address);
	}

}
