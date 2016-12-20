package hu.unideb.inf.nvkshop.web.user;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import hu.unideb.inf.nvkshop.web.AbstractNvkController;
import hu.unideb.inf.nvkshop.web.PasswordRecoveryForm;
import hu.unideb.inf.rft.nvkshop.entities.security.Address;
import hu.unideb.inf.rft.nvkshop.entities.security.User;
import hu.unideb.inf.rft.nvkshop.service.DeletedEntityException;
import hu.unideb.inf.rft.nvkshop.service.UserService;

@Controller("userController")
@RequestMapping("/user")
@SessionAttributes(types = { UserForm.class })
public class UserController extends AbstractNvkController {

	@Autowired
	UserService userService;

	/** SLF4J Logger */
	private final Logger log = LoggerFactory.getLogger(UserController.class);

	/**
	 * Display edit user form.
	 * 
	 * @param id the user id
	 * @param model the model
	 * @return the view name
	 */
	@RequestMapping(value = "/edit", produces = "text/html")
	public String editForm(@ModelAttribute("form") UserForm form, Model model, RedirectAttributes redAttrs) {

		User user = userService.findById(authenticationUserId());

		// User user = userService.findById(form.getUserId());
		if (user == null) {
			redAttrs.addAttribute("errorMsg", "user.notValidUser");
			return " redirect:/login";
		}

		form.setFirstName(user.getFirstName());
		form.setLastName(user.getLastName());
		form.setPhoneNumber(user.getPhoneNumber());
		form.setEmail(user.getEmail());

		model.addAttribute("addresses", user.getAddresses());
		model.addAttribute("addressForm", new AddressForm());
		// TODO: maybe add form.
		return "user/edit";
	}

	/**
	 * Submit user edit form.
	 * 
	 * @param form the form
	 * @param errors the errors
	 * @param flashAttributes the flash attributes
	 * @return the view name
	 */
	@RequestMapping(value = "/edit", params = "save", method = RequestMethod.POST, produces = "text/html")
	public String editFormSubmit(@ModelAttribute("form") UserForm form, Errors errors, RedirectAttributes flashAttributes) {

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "validation.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "validation.required");

		// FIXME: conversion error with ENUM. Maybe occured with the different packages ?
		//
		// if (errors.hasErrors()) {
		// return "user/edit";
		// }

		try {
			userService.editUserBasicDatas(authenticationUserId(), form.getFirstName(), form.getLastName(), form.getPhoneNumber(),
					form.getLanguage());
		} catch (ConstraintViolationException ex) {
			// TODO set this
		}

		log.info("Submitting edit user form: id={}", form.getUserId());
		flashAttributes.addFlashAttribute("successMsg", "users.saved");
		return "redirect:/user/edit.html";
	}

	@RequestMapping(value = "/edit", params = "addAddress", method = RequestMethod.POST, produces = "text/html")
	public String editFormAddAddress(@ModelAttribute("form") UserForm form, Errors errors, Model model, RedirectAttributes redAttrs) {

		Long id = authenticationUserId();

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newAddress.country", "validation.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newAddress.zipCode", "validation.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newAddress.street", "validation.required");

		if (errors.hasErrors()) {
			return "redirect:/user/edit";
		}

		userService.addUserAddress(id, form.getNewAddress());

		return "redirect:/user/edit";
	}

	@RequestMapping(value = "/edit", params = "newPassword", method = RequestMethod.POST, produces = "text/html")
	public String changePassword(@ModelAttribute("form") UserForm userForm, Errors errors, Model model, RedirectAttributes redAttrs) {

		Long id = authenticationUserId();

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "validation.required");

		if (errors.hasErrors()) {
			redAttrs.addFlashAttribute("errorMsg", "user.wrongPassword");
			return "redirect:/user/edit.html";
		}

		userForm.getPassword();
		if (!userService.isMatchesForUserPassword(id, userForm.getPassword())) {
			redAttrs.addFlashAttribute("errorMsg", "user.wrongPassword");
			return "redirect:/user/edit.html";

		}
		PasswordRecoveryForm form = new PasswordRecoveryForm();
		addDatasForUser(form, null);

		model.addAttribute("passwordRecoveryForm", form);

		return "user/passwordrecover";
	}

	@RequestMapping(value = "/setprimaryaddress", method = RequestMethod.GET, produces = "text/html")
	public String savePrimaryAddress(@ModelAttribute("form") UserForm userForm, @RequestParam("id") long addressId, Errors errors,
			Model model, RedirectAttributes redAttrs) {

		Long id = authenticationUserId();
		try {
			userService.savePrimaryAddress(id, addressId);

		} catch (Exception e) {

		}

		return "redirect:/user/edit";
	}

	@RequestMapping(value = "/address", method = RequestMethod.GET, produces = "text/html")
	public String address(@ModelAttribute("form") UserForm userForm, @RequestParam("id") long addressId, Errors errors, Model model,
			RedirectAttributes redAttrs) {

		Long id = authenticationUserId();
		Address address = new Address();

		try {
			address = userService.findAddressById(addressId);
		} catch (Exception e) {

		}
		AddressForm form = new AddressForm(address);
		addDatasForUser(form, null);
		model.addAttribute("addressForm", form);

		return "user/address";
	}
	//
	// @RequestMapping(value = "/address", method = RequestMethod.GET, produces = "text/html")
	// public String addressEdit(@RequestParam("id") long addressId, Errors errors, Model model, RedirectAttributes redAttrs) {
	// Address address = new Address();
	// authenticationUserId();
	// try {
	// address = userService.findAddressById(addressId);
	// } catch (Exception e) {
	//
	// }
	// AddressForm form = new AddressForm(address);
	// model.addAttribute("addressForm", form);
	//
	// return "user/address";
	// }

	@RequestMapping(value = "/address", method = RequestMethod.POST, produces = "text/html")
	public String addressEditSubmit(@ModelAttribute("addressForm") AddressForm form, Errors errors, Model model,
			RedirectAttributes redAttrs) {
		authenticationUserId();

		try {
			userService.updateAddress(authenticationUserId(), form.getAddress().getId(), form.getAddress());
		} catch (DeletedEntityException ex) {

		}
		redAttrs.addFlashAttribute("successMsg", "address.saved");
		return "redirect:/user/edit";
	}

	@RequestMapping(value = "/deleteaddress", method = RequestMethod.GET, produces = "text/html")
	public String deleteAddress(@ModelAttribute("form") UserForm userForm, @RequestParam("id") long addressId, Errors errors, Model model,
			RedirectAttributes redAttrs) {

		Long id = authenticationUserId();
		userService.deleteAddress(addressId, id);

		return "redirect:/user/edit";
	}

	@RequestMapping(value = "/passwordrecover", params = "in", method = RequestMethod.POST, produces = "text/html")
	public String changePasswordSubmit(@ModelAttribute("passwordRecoveryForm") PasswordRecoveryForm form, Errors errors, Model model,
			RedirectAttributes redAttrs) {

		Long id = authenticationUserId();

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "validation.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConfirm", "validation.required");

		if (errors.hasErrors()) {
			return "passwordrecover";
		}
		if (!form.getPassword().equals(form.getPasswordConfirm()))

			errors.rejectValue("passwordConfirm", "validation.passwordsNotMatch");

		if (errors.hasErrors()) {
			return "passwordrecover";
		}
		try {
			userService.resetPassword(id, form.getPassword());
			// log.info("Set new password for user. Id = {}", form.getId());

		} catch (DeletedEntityException ex) {
			redAttrs.addFlashAttribute("errorMsg", "registration.permamentlyDeletedUser");
			return "redirect:/login.html";
		}
		redAttrs.addFlashAttribute("successMsg", "registration.passwordReseted");
		return "redirect:/logout";
	}

}
