package hu.unideb.inf.nvkshop.web.main;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import hu.unideb.inf.nvkshop.web.AbstractNvkController;
import hu.unideb.inf.nvkshop.web.user.UserForm;
import hu.unideb.inf.rft.nvkshop.entities.product.Item;
import hu.unideb.inf.rft.nvkshop.entities.product.Order;
import hu.unideb.inf.rft.nvkshop.entities.product.Product;
import hu.unideb.inf.rft.nvkshop.entities.security.Address;
import hu.unideb.inf.rft.nvkshop.entities.security.User;
import hu.unideb.inf.rft.nvkshop.service.AddressService;
import hu.unideb.inf.rft.nvkshop.service.CategoryService;
import hu.unideb.inf.rft.nvkshop.service.ProductService;
import hu.unideb.inf.rft.nvkshop.service.UserService;

@Controller("mainController")
@SessionAttributes(types = { UserForm.class })
public class MainController extends AbstractNvkController {

	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private AddressService addressService;
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/main-welcome", method = RequestMethod.GET, produces = "text/html")
	public String mainWelcome(Model model, RedirectAttributes redAttrs) {

		UserForm form = new UserForm();
		addDatasForUser(form, null);

		model.addAttribute("form", form);
		return "main";
	}

	@RequestMapping(value = "/main", method = RequestMethod.GET, produces = "text/html")
	public String main(@ModelAttribute(value = "form") UserForm form, @RequestParam(value = "categoryId", required = false) Long id,
			Model model, RedirectAttributes redAttrs) {
		addDatasForUser(form, id);

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
	public String product(@ModelAttribute("form") UserForm form, @RequestParam(value = "id", required = true) Long id, Model model,
			RedirectAttributes redAttrs) {
		addDatasForUser(form, null);

		form.setProduct(productService.findById(id));

		model.addAttribute("form", form);

		return "product";
	}

	@RequestMapping(value = "/product", method = RequestMethod.POST, produces = "text/html")
	public String productSubmit(@ModelAttribute("form") UserForm form, Model model, RedirectAttributes redAttrs) {

		if (form.getCart() == null) {
			form.setCart(new LinkedList<Item>());
		}
		if (form.getQty() != null && form.getQty() > 0) {
			form.setTotalPrice(new Double(0));
			if (form.getProduct().getId() != null) {
				Product product = productService.findById(form.getProduct().getId().longValue());
				Item item1 = new Item(product, form.getQty());

				form.getCart().add(item1);
				for (Item item : form.getCart()) {
					form.setTotalPrice(form.getTotalPrice() + (item.getProduct().getPrice() * item.getQuantity()));
				}
			}
		}
		return "product";
	}

	@RequestMapping(value = "/orders", method = RequestMethod.GET, produces = "text/html")
	public String orders(@ModelAttribute("form") UserForm form, Model model, RedirectAttributes redAttrs) {

		return "orders";
	}

	@RequestMapping(value = "/orders", method = RequestMethod.POST, produces = "text/html")
	public String orderSubmit(@ModelAttribute("form") UserForm form, Model model, RedirectAttributes redAttrs) {

		form.setTotalPrice(new Double(0));
		for (Item item : form.getCart()) {
			form.setTotalPrice(form.getTotalPrice() + (item.getProduct().getPrice() * item.getQuantity()));
		}

		return "orders";
	}

	@RequestMapping(value = "/orders", params = "addOrder", method = RequestMethod.POST, produces = "text/html")
	public String orderFinalizeOrder(@ModelAttribute("form") UserForm form, Model model, RedirectAttributes redAttrs,
			SessionStatus status) {

		long id = authenticationUserId();
		User user = userService.findById(id);
		Address primary = addressService.getDefaultAddress(user);
		Order order = new Order();
		order.setAddress(primary);
		order.setItems(form.getCart());
		order.setUser(user);
		productService.placeOrder(order);

		status.setComplete();

		form = new UserForm();
		addDatasForUser(form, null);

		return "redirect:/order-success";
	}

	@RequestMapping(value = "/order-success", method = RequestMethod.GET, produces = "text/html")
	public String orderFinalizeOrder(Model model, RedirectAttributes redAttrs) {

		UserForm form = new UserForm();
		addDatasForUser(form, null);
		redAttrs.addFlashAttribute("successMsg", "order.placed");

		model.addAttribute("form", form);
		return "redirect:/main.html";
	}

}
