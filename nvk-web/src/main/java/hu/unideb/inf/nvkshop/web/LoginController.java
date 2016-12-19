package hu.unideb.inf.nvkshop.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import hu.unideb.inf.rft.nvkshop.service.AlreadyLoggedInException;

@Controller("loginController")
public class LoginController extends AbstractNvkController {

	/** Login controller */
	public LoginController() {
	}

	@RequestMapping("/login.html")
	public String login(@RequestParam(required = false) boolean failure, Model model, RedirectAttributes redirectAttributes) {
		LoginForm form = new LoginForm();

		try {
			addDatasForUserInLogin(form);

		} catch (AlreadyLoggedInException ex) {
			redirectAttributes.addFlashAttribute("warningMsg", "warning.alreadyLoggedIn");
			return "redirect:/main.html";
		}
		model.addAttribute(form);
		model.addAttribute("failure", failure);
		return "login";
	}

}
