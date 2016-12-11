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

import hu.unideb.inf.rft.nvkshop.entities.security.UserPasswordRecovery;
import hu.unideb.inf.rft.nvkshop.entities.security.UserRegistrationRequest;
import hu.unideb.inf.rft.nvkshop.service.DeletedEntityException;
import hu.unideb.inf.rft.nvkshop.service.UserPasswordRecoveryService;
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
@Controller("registrationController")
public class RegistrationController extends AbstractNvkController {

	/** Registration controller */
	@Autowired
	private UserRegistrationRequestService registrationService;

	@Autowired
	private UserPasswordRecoveryService userPasswordRecoveryService;

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
		// addTestDatasForUser(form);
		addDatasForUser(form);
		model.addAttribute("registrationRequestForm", form);
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
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "validation.required");
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
						errors.rejectValue("userName", "registration.userNameAlreadyRegistered");
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
				redirectAttrs.addFlashAttribute("errorMsg", "registration.invalidActivationCode");
			}
		}
		return "redirect:/login.html";
	}

	/**
	 * Password recovery step 1.
	 * 
	 * @param errors the errors
	 * @param model the model
	 * @return
	 */
	@RequestMapping(value = "/lostpassword", method = RequestMethod.GET, produces = "text/html")
	public String lostPassword(Model model) {
		RegistrationRequestForm form = new RegistrationRequestForm();
		log.info("Password recovery step 1.");
		model.addAttribute("registrationRequestForm", form);
		return "lostpassword";

	}

	@RequestMapping(value = "/lostpassword", method = RequestMethod.POST, produces = "text/html")
	public String lostPasswordSubmit(RegistrationRequestForm form, Errors errors, Model model, RedirectAttributes redirectAttrs) {

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "validation.required");

		if (errors.hasErrors()) {
			return "lostpassword";
		}

		try {
			log.info("Check and delete old recoveries. Email = {}", form.getEmail());
			userPasswordRecoveryService.findAndDeleteOldRecoveries(form.getEmail());
			log.info("Try recover user password. Email = {}", form.getEmail());
			userPasswordRecoveryService.createUserPasswordRecoveryByEmail(form.getEmail());
		} catch (DeletedEntityException ex) {
			errors.rejectValue("email", "validation.notARegisteredEmail");
		}

		if (errors.hasErrors()) {
			return "lostpassword";
		}

		redirectAttrs.addFlashAttribute("successMsg", "registration.passwordRecoverySent");
		return "redirect:/login.html";

	}

	@RequestMapping(value = "/passwordrecover", method = RequestMethod.GET, produces = "text/html")
	public String passwordRecover(@RequestParam("activationCode") String activationCode, Model model, RedirectAttributes redirectAttrs) {

		UserPasswordRecovery passwordRecovery;
		log.info("Password recovery step 2.");
		try {
			passwordRecovery = userPasswordRecoveryService.findUserPasswordRecoveryByActivationCode(activationCode);
		} catch (DeletedEntityException ex) {
			redirectAttrs.addFlashAttribute("errorMsg", "validation.invalidOrExpiredToken");
			return "redirect:/login.html";
		}

		PasswordRecoveryForm form = new PasswordRecoveryForm();
		form.setActivationCode(activationCode);
		model.addAttribute("passwordRecoveryForm", form);
		return "passwordrecover";

	}

	@RequestMapping(value = "/passwordrecover", method = RequestMethod.POST, produces = "text/html")
	public String passwordRecoverSubmit(PasswordRecoveryForm form, Errors errors, Model model, RedirectAttributes redirectAttrs) {
		log.info("Password recovery step 3.");

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
			System.out.println(form.getActivationCode());
			userPasswordRecoveryService.resetPassword(form.getActivationCode(), form.getPassword());
			// log.info("Set new password for user. Id = {}", form.getId());

		} catch (DeletedEntityException ex) {
			redirectAttrs.addFlashAttribute("errorMsg", "registration.permamentlyDeletedUser");
			return "redirect:/login.html";
		}
		redirectAttrs.addFlashAttribute("successMsg", "registration.passwordReseted");
		return "redirect:/login.html";

	}

	@RequestMapping(value = "/test", method = RequestMethod.GET, produces = "text/html")
	public String loginTest(Model model) {
		model.addAttribute("UserId", authenticationUserId());
		return "test";
	}

}
