package hu.unideb.inf.nvkshop.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import hu.unideb.inf.rft.nvkshop.service.AlreadyLoggedInException;

@Controller("loginControllerForMobile")
@RequestMapping("/mobile")
public class LoginControllerForMobile extends AbstractNvkController {

	/** Login controller */
	public LoginControllerForMobile() {
	}

	@RequestMapping("/login")
	public String login(@RequestParam(required = false) boolean failure, Model model, RedirectAttributes redirectAttributes) {
		LoginForm form = new LoginForm();

		try {
			addDatasForUserInLogin(form);

		} catch (AlreadyLoggedInException ex) {
			redirectAttributes.addFlashAttribute("warningMsg", "warning.alreadyLoggedIn");
			return "redirect:/mobile/main.html";
		}
		model.addAttribute(form);
		model.addAttribute("failure", failure);
		return "mobile/login";
	}

	@RequestMapping(value = "/main", method = RequestMethod.GET, produces = "text/html")
	public String main(Model model, RedirectAttributes redAttrs) {

		RegistrationRequestForm form = new RegistrationRequestForm();
		addDatasForUser(form, null);
		model.addAttribute("form", form);
		// log.info("Registration request handling.");
		return "mobile/main";
	}

}
