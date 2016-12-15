package hu.unideb.inf.nvkshop.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import hu.unideb.inf.rft.nvkshop.service.UserPasswordRecoveryService;

@RestController
public class PasswordRecovery {

	@Autowired
	private UserPasswordRecoveryService userPasswordRecoveryService;

	@RequestMapping(value = "/forgottenPassword", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void createUserPasswordRecovery(@RequestParam("email") String email) {
		userPasswordRecoveryService.createUserPasswordRecoveryByEmail(email);
	}
}
