package hu.unideb.inf.nvkshop.web.main;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import hu.unideb.inf.nvkshop.web.AbstractNvkController;
import hu.unideb.inf.nvkshop.web.RegistrationRequestForm;

@Controller("mainController")
public class MainController extends AbstractNvkController {
	@RequestMapping(value = "/main", method = RequestMethod.GET, produces = "text/html")
	public String main(Model model, RedirectAttributes redAttrs) {

		RegistrationRequestForm form = new RegistrationRequestForm();
		addDatasForUser(form);
		model.addAttribute("form", form);
		// log.info("Registration request handling.");
		return "main";
	}
}
