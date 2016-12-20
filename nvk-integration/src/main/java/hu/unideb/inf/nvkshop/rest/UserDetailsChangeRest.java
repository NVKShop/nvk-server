package hu.unideb.inf.nvkshop.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import hu.unideb.inf.nvkshop.rest.vo.PublicAddress;
import hu.unideb.inf.nvkshop.rest.vo.UserDetailsChange;
import hu.unideb.inf.rft.nvkshop.entities.security.Address;
import hu.unideb.inf.rft.nvkshop.entities.security.User;
import hu.unideb.inf.rft.nvkshop.security.CustomUserDetails;
import hu.unideb.inf.rft.nvkshop.service.AddressService;
import hu.unideb.inf.rft.nvkshop.service.UserService;

@RestController
@RequestMapping(value = "/secured")
public class UserDetailsChangeRest {

	@Autowired
	private UserService userService;
	
	@Autowired
	private AddressService addressService;

	@RequestMapping(value = "/userDetailsChange", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void processDetailsChange(@RequestBody UserDetailsChange userDetailsChange) {
		userService.editUserBasicDatas(getAuthenticatedUserId().getId(), userDetailsChange.getFirstName(),
				userDetailsChange.getLastName(), userDetailsChange.getPhoneNumber(), userDetailsChange.getLanguage());
	}

	@RequestMapping(value="/addressDetailsChange",method=RequestMethod.POST)
	public void processAddressDetailsChange(@RequestBody PublicAddress address){
		Address oldAddress= addressService.getDefaultAddress(getAuthenticatedUserId());
		oldAddress.setCity(address.getCity());
		oldAddress.setCountry(address.getCountry());
		oldAddress.setHouseNumber(address.getHouseNumber());
		oldAddress.setPhoneNumber(address.getPhoneNumber());
		oldAddress.setStreet(address.getStreet());
		oldAddress.setZipCode(address.getZipCode());
		addressService.save(oldAddress);
	}
	
	private User getAuthenticatedUserId() {
		return ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
	}

}
