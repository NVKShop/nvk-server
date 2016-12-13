package hu.unideb.inf.nvkshop.web.user;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import hu.unideb.inf.nvkshop.web.AbstractNvkController;
import hu.unideb.inf.nvkshop.web.PasswordRecoveryForm;
import hu.unideb.inf.rft.nvkshop.service.DeletedEntityException;
import hu.unideb.inf.rft.nvkshop.service.UserService;

@Controller("userController")
@RequestMapping("/user")
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
	public String editForm(Model model, RedirectAttributes redAttrs) {

		// User user = userService.findById(authenticationUserId());

		UserForm form = new UserForm();
		addDatasForUser(form);
		// User user = userService.findById(form.getUserId());
		// if (user == null) {
		// redAttrs.addAttribute("errorMsg", "user.notValidUser");
		// return " redirect:/login";
		// }
		//
		// form.setFistName(user.getFirstName());
		// form.setLastName(user.getLastName());
		// form.setPhoneNumber(user.getPhoneNumber());
		// form.setEmail(user.getEmail());
		// form.setAddresses(user.getAddresses());

		model.addAttribute("userForm", form);
		model.addAttribute("addressForm", new AddressForm());
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

		if (errors.hasErrors()) {
			return "user/edit";
		}

		userService.editUserBasicDatas(form.getUserId(), form.getFirstName(), form.getLastName(), form.getPhoneNumber(),
				form.getLanguage());

		log.info("Submitting edit user form: id={}", form.getUserId());
		flashAttributes.addFlashAttribute("successMsg", "users.saved");
		return "redirect:/users/list.html";
	}

	@RequestMapping(value = "/edit", params = "addAddress", method = RequestMethod.POST, produces = "text/html")
	public String editForm(@ModelAttribute("userForm") UserForm form, Errors errors, Model model, RedirectAttributes redAttrs) {

		System.out.println("Here");
		Long id = authenticationUserId();

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newAddress.country", "validation.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newAddress.zipCode", "validation.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newAddress.street", "validation.required");

		if (errors.hasErrors()) {
			return "redirect:/user/edit";
		}

		// userService.addUserAddress(id, newAddress);

		return "user/edit";
	}

	@RequestMapping(value = "/edit", params = "newPassword", method = RequestMethod.POST, produces = "text/html")
	public String changePassword(@ModelAttribute("userForm") UserForm userForm, Errors errors, Model model, RedirectAttributes redAttrs) {

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
		addDatasForUser(form);

		model.addAttribute("passwordRecoveryForm", form);

		return "user/passwordrecover";
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
