package hu.unideb.inf.nvkshop.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import hu.unideb.inf.rft.nvkshop.service.DeletedEntityException;
import hu.unideb.inf.rft.nvkshop.service.UserPasswordRecoveryService;

@RestController
public class PasswordRecoveryRest {

	@Autowired
	private UserPasswordRecoveryService userPasswordRecoveryService;

	@RequestMapping(value = "/forgottenPassword", method = RequestMethod.POST)
	public ResponseEntity<?> createUserPasswordRecovery(@RequestParam("email") String email) {
		try {
			userPasswordRecoveryService.createUserPasswordRecoveryByEmail(email);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (DeletedEntityException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
