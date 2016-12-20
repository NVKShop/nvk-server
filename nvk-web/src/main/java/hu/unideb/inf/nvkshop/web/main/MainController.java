package hu.unideb.inf.nvkshop.web.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import hu.unideb.inf.nvkshop.web.AbstractNvkController;
import hu.unideb.inf.nvkshop.web.user.UserForm;
import hu.unideb.inf.rft.nvkshop.entities.product.Category;
import hu.unideb.inf.rft.nvkshop.entities.product.Product;
import hu.unideb.inf.rft.nvkshop.service.CategoryService;
import hu.unideb.inf.rft.nvkshop.service.ProductService;

@Controller("mainController")
public class MainController extends AbstractNvkController {

	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;

	@RequestMapping(value = "/main", method = RequestMethod.GET, produces = "text/html")
	public String main(@RequestParam(value = "categoryId", required = false) Long id, Model model, RedirectAttributes redAttrs) {
		UserForm form = new UserForm();
		addDatasForUser(form, id);

		if (id != null) {
			Category cat = categoryService.findById(id.longValue());
			form.setProducts(productService.findByCategory(cat));
		} else {
			// TODO: Trimthis
			form.setProducts(productService.findAll());

		}
		model.addAttribute("form", form);

		return "main";
	}

	@RequestMapping(value = "/main/product/{id}/picture", method = RequestMethod.GET)
	public ResponseEntity<byte[]> imageOfProduct(@PathVariable long id) {

		Product product = productService.findById(id);
		byte[] bytes = product.getPictureAsByte();

		// Set headers
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);

		return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/product", method = RequestMethod.GET, produces = "text/html")
	public String product(@RequestParam(value = "id", required = true) Long id, Model model, RedirectAttributes redAttrs) {
		UserForm form = new UserForm();
		addDatasForUser(form, null);

		form.setProduct(productService.findById(id));

		model.addAttribute("form", form);

		return "product";
	}

}
