package hu.unideb.inf.nvkshop.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("loginController")
public class LoginController {

	/** Login controller */
	public LoginController() {
	}

	@RequestMapping("/login.html")
	public String login(@RequestParam(required = false) boolean failure, Model model) {

		model.addAttribute(new LoginForm());
		model.addAttribute("failure", failure);
		return "login";
	}

}
