package hu.unideb.inf.nvkshop.web;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import hu.unideb.inf.rft.nvkshop.entities.security.UserRegistrationRequest;
import hu.unideb.inf.rft.nvkshop.service.UserRegistrationRequestService;
import hu.unideb.inf.rft.nvkshop.service.UserService;
import hu.unideb.inf.rft.nvkshop.validation.exception.ValidationException;
import hu.unideb.inf.rft.nvkshop.validation.exception.ValidationViolation;
import hu.unideb.inf.rft.nvkshop.validation.userregistration.UserValidationViolations;

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

	@Autowired
	private UserRegistrationRequestService registrationService;

	@Autowired
	private UserService userService;

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

		Mapper dz = new DozerBeanMapper();
		UserRegistrationRequest request = dz.map(form, UserRegistrationRequest.class);

		try {
			registrationService.attemptRegistration(request);
		} catch (ValidationException e) {
			for (ValidationViolation violation : e.getViolations()) {
				if (violation instanceof UserValidationViolations) {
					switch ((UserValidationViolations) violation) {
					case EMAIL_EXISTS:
						errors.rejectValue("email", "registration.emailAlreadyRegistered");
					case EMAIL_NOT_VALID:
						errors.rejectValue("email", "registration.emailNotValid");
					case USERNAME_EXISTS:
						errors.rejectValue("username", "registration.userNameAlreadyRegistered");
					case PASSWORD_MISMATCH:
						errors.rejectValue("password", "registration.passwordsNotMatch");
						errors.rejectValue("passwordConfirm", "registration.passwordsNotMatch");
					}
				}
			}
			return "registration";
		}
		return "redirect:/login.html";
	}

	/**
	 * Activate user registration by the activation code URL code.
	 * 
	 * @param form the form
	 * @param errors the errors
	 * @param model the model
	 * @param redirectAttrs the redirect attributes
	 * @return the view name
	 */
	@RequestMapping(value = "/registration/activation", method = RequestMethod.GET, produces = "text/html")
	public String activateRegistration(@RequestParam("activationCode") String activationCode, Model model,
			RedirectAttributes redirectAttrs) {

		log.info("Try to activate registraion. ActivationCode ={}", activationCode);
		if (!StringUtils.hasText(activationCode)) {
			redirectAttrs.addFlashAttribute("errorMsg", "registration.invalidToken");
		} else {
			try {
				userService.activateRegistration(activationCode);
				redirectAttrs.addFlashAttribute("successMsg", "registration.activationSuccess");
			} catch (Exception e) {
				redirectAttrs.addFlashAttribute("errorMsg", "registration.invaliActivationCode");
			}
		}
		return "redirect:/login.html";
	}

}
