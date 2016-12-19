package hu.unideb.inf.nvkshop.rest;

import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hu.unideb.inf.nvkshop.rest.vo.PublicUser;
import hu.unideb.inf.rft.nvkshop.entities.security.Role;
import hu.unideb.inf.rft.nvkshop.entities.security.User;
import hu.unideb.inf.rft.nvkshop.security.CustomUserDetails;

@RestController
@RequestMapping(value = "/secured")
public class UserInformationRest {

	@RequestMapping(value = "/userInfromation", method = RequestMethod.GET)
	public PublicUser provideUserInformation() {
		User user = getLoggedinUser();
		return mapToPublicUser(user);
	}

	private User getLoggedinUser() {
		return ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
	}

	private PublicUser mapToPublicUser(User u) {
		return PublicUser.builder().email(u.getEmail()).phoneNumber(u.getPhoneNumber()).fistName(u.getFirstName())
				.lastName(u.getLastName()).roles(u.getRoles().stream().map((Role role) -> {
					return role.getRoleName();
				}).collect(Collectors.toList())).build();
	}

}
