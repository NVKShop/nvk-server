package hu.unideb.inf.nvkshop.rest;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hu.unideb.inf.nvkshop.rest.vo.PublicAddress;
import hu.unideb.inf.nvkshop.rest.vo.PublicUser;
import hu.unideb.inf.rft.nvkshop.entities.security.Address;
import hu.unideb.inf.rft.nvkshop.entities.security.Role;
import hu.unideb.inf.rft.nvkshop.entities.security.User;
import hu.unideb.inf.rft.nvkshop.security.CustomUserDetails;
import hu.unideb.inf.rft.nvkshop.service.AddressService;

@RestController
@RequestMapping(value = "/secured")
public class UserInformationRest {

	@Autowired
	private AddressService addressService;

	@RequestMapping(value = "/userInformation", method = RequestMethod.GET)
	public PublicUser provideUserInformation() {
		User user = getLoggedinUser();
		return mapToPublicUser(user);
	}

	private User getLoggedinUser() {
		return ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
	}

	private PublicUser mapToPublicUser(User u) {
		Address address = addressService.getDefaultAddress(u);
		return PublicUser.builder().email(u.getEmail()).phoneNumber(u.getPhoneNumber()).firstName(u.getFirstName())
				.lastName(u.getLastName()).roles(u.getRoles().stream().map((Role role) -> {
					return role.getRoleName();
				}).collect(Collectors.toList()))
				.address(address == null ? null
						: PublicAddress.builder().city(address.getCity()).country(address.getCountry())
								.phoneNumber(address.getPhoneNumber()).street(address.getStreet())
								.zipCode(address.getZipCode()).houseNumber(address.getHouseNumber()).id(address.getId()).build())
				.build();
	}

}
