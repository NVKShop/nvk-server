package hu.unideb.inf.nvkshop.web;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import hu.unideb.inf.rft.nvkshop.entities.security.User;
import hu.unideb.inf.rft.nvkshop.service.UserService;

@Controller("userController")
@RequestMapping("/users")
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
	public String editForm(@RequestParam("id") long id, Model model, RedirectAttributes redAttrs) {

		User user = userService.findById(id);

		if (user == null) {
			redAttrs.addAttribute("errorMsg", "user.notValidUser");
			return " redirect:/login";
		}
		Mapper dz = new DozerBeanMapper();

		UserForm form = dz.map(user, UserForm.class);
		model.addAttribute("form", form);
		return "users/edit";
	}

	/**
	 * Submit user edit form.
	 * 
	 * @param form the form
	 * @param errors the errors
	 * @param flashAttributes the flash attributes
	 * @return the view name
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST, produces = "text/html")
	public String editFormSubmit(@ModelAttribute("form") UserForm form, Errors errors, RedirectAttributes flashAttributes) {

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "validation.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "validation.required");

		if (errors.hasErrors()) {
			return "users/edit";
		}

		userService.editUserBasicDatas(form.getId(), form.getFistName(), form.getLastName(), form.getPhoneNumber(), form.getLanguage());

		log.info("Submitting edit user form: id={}", form.getId());
		flashAttributes.addFlashAttribute("successMsg", "users.saved");
		return "redirect:/users/list.html";
	}
}
