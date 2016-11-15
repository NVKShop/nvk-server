package hu.unideb.inf.nvkshop.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Registration controller
 * 
 * @author FV
 *
 */
@Controller
public class RegistrationController extends AbstractNvkController {

	/** Registration controller */
	public RegistrationController() {
	}

	/** SLF4J Logger */
	private final Logger log = LoggerFactory.getLogger(RegistrationController.class);

	/**
	 * User registration request handler method.
	 * 
	 * @param model the model
	 * @return the view name
	 */
	@RequestMapping(value = "/registration", method = RequestMethod.GET, produces = "text/html")
	public String registration(Model model) {

		RegistrationRequestForm form = new RegistrationRequestForm();
		model.addAttribute("registrationForm", form);
		log.info("Registration request handling.");
		return "registration";
	}

	/**
	 * Create user registration.
	 * 
	 * @param form the form
	 * @param errors the errors
	 * @param model the model
	 * @param redirectAttrs the redirect attributes
	 * @return the view name
	 */
	@RequestMapping(value = "/registration", method = RequestMethod.POST, produces = "text/html")
	public String registrationSubmit(RegistrationRequestForm form, Errors errors, Model model, RedirectAttributes redirectAttrs) {

		log.info("Registration request submit.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "validation.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "validation.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "validation.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "validation.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConfirm", "validation.required");

		if (errors.hasErrors()) {
			return "registration";
		}

		//
		// try {
		// log.info("Try to register user.");
		// // userService.registerUser(form.getName(), form.getEmail(), form.getPassword());
		//
		// } catch (EntityConflictionException ex) {
		// }
		//
		// redirectAttrs.addFlashAttribute("successMsg", "registration.success");
		return "redirect:/login.html";

	}

	/**
	 * Activate user registration.
	 * 
	 * @param form the form
	 * @param errors the errors
	 * @param model the model
	 * @param redirectAttrs the redirect attributes
	 * @return the view name
	 */
	@RequestMapping(value = "/registration/activation", method = RequestMethod.GET, produces = "text/html")
	public String activateRegistration(@RequestParam("token") String token, Model model, RedirectAttributes redirectAttrs) {
		//
		// log.info("Try to activate registraion. Token={}", token);
		// if (!StringUtils.hasText(token)) {
		// redirectAttrs.addFlashAttribute("errorMsg", "registration.invalidToken");
		// } else {
		//
		// if (user == null) {
		// redirectAttrs.addFlashAttribute("errorMsg", "registration.invalidToken");
		// } else if (user.getStatus() != UserStatus.ACTIVATION_LINK_SENT) {
		// redirectAttrs.addFlashAttribute("errorMsg", "registration.userAlreadyActivatedOrDeleted");
		// } else {
		// try {
		// userService.activateUserRegistration(user);
		// redirectAttrs.addFlashAttribute("successMsg", "registration.activationSuccess");
		// } catch (Exception e) {
		// redirectAttrs.addFlashAttribute("errorMsg", "registration.invalidToken");
		// }
		// }
		//
		// }
		return "redirect:/login.html";
	}

}
