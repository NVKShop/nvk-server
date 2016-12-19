package hu.unideb.inf.nvkshop.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import hu.unideb.inf.nvkshop.web.AbstractNvkController;
import hu.unideb.inf.rft.nvkshop.entities.product.Category;
import hu.unideb.inf.rft.nvkshop.entities.product.Product;
import hu.unideb.inf.rft.nvkshop.service.CategoryService;
import hu.unideb.inf.rft.nvkshop.service.ProductService;
import hu.unideb.inf.rft.nvkshop.validation.exception.ValidationException;

@Controller("adminController")
@RequestMapping("/admin")
public class AdminController extends AbstractNvkController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductService productService;

	@RequestMapping(value = "/categories", method = RequestMethod.GET, produces = "text/html")
	public String categoryForm(Model model, RedirectAttributes redAttrs) {
		CategoryForm form = new CategoryForm();
		form.setRootCategories(categoryService.findRootCategories());
		model.addAttribute("form", form);
		return "admin/categories";
	}

	@RequestMapping(value = "/categories", produces = "text/html", method = RequestMethod.POST)
	public String categoryFormAddNewCategory(@ModelAttribute("form") CategoryForm form, Model model, Errors errors,
			RedirectAttributes redAttrs) {

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newCategoryName", "validation.required");

		try {
			categoryService.addCategory(form.getNewCategoryName(), null);
		} catch (ValidationException e) {
			errors.reject("newCategoryName", "category.notValidCategory");
		}
		return "redirect:/admin/categories";
	}

	@RequestMapping(value = "/category/{id}/edit", produces = "text/html")
	public String categorySubForm(@PathVariable("id") long id, Model model, RedirectAttributes redAttrs) {
		CategoryForm form = new CategoryForm();

		Category cat = categoryService.findById(id);
		if (cat == null) {
			// TODO: log or smtg
			return "redirect:/admin/categories";
		}
		form.setCategory(cat);
		model.addAttribute("form", form);
		return "admin/category";

	}

	@RequestMapping(value = "/category/{id}/edit", produces = "text/html", method = RequestMethod.POST)
	public String categorySubFormSubmit(@PathVariable("id") long id, @ModelAttribute("form") CategoryForm form, Model model, Errors errors,
			RedirectAttributes redAttrs) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newCategoryName", "validation.required");
		if (errors.hasErrors()) {
			return "admin/edit";
		}
		try {
			categoryService.addSubCategory(id, form.getNewCategoryName());
		} catch (ValidationException e) {
			errors.reject("newCategoryName", "category.notValidCategory");
		}
		return "redirect:/admin/category/" + id + "/edit";

	}

	@RequestMapping(value = "/category/{id}/subcategory/{subId}/edit", produces = "text/html")
	public String categorySubEditForm(@PathVariable("id") long id, @PathVariable("subId") long subId, Model model,
			RedirectAttributes redAttrs) {
		CategoryForm form = new CategoryForm();

		Category cat = categoryService.findById(subId);
		if (cat == null) {
			// TODO: log or smtg
			return "redirect:/admin/categories";
		}
		form.setCategory(cat);
		model.addAttribute("form", form);
		return "admin/subcategory";

	}

	@RequestMapping(value = "/category/{id}/subcategory/{subId}/edit", produces = "text/html", method = RequestMethod.POST)
	public String categorySubEditFormSubmit(@PathVariable("id") long id, @PathVariable("subId") long subId,
			@ModelAttribute("form") CategoryForm form, Model model, Errors errors, RedirectAttributes redAttrs) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newCategoryName", "validation.required");
		if (errors.hasErrors()) {
			return "admin/edit";
		}
		try {
			categoryService.addSubCategory(id, form.getNewCategoryName());
		} catch (ValidationException e) {
			errors.reject("newCategoryName", "category.notValidCategory");
		}
		return "admin/category";

	}

	@RequestMapping(value = "/newProduct", method = RequestMethod.GET, produces = "text/html")
	public String productForm(@RequestParam(value = "id", required = false) Long id, Model model, RedirectAttributes redAttrs) {
		ProductForm form = new ProductForm();
		if (id != null) {
			Category cat = categoryService.findById(id);
			if (cat == null) {
				// TODO: log or smtg
				return "redirect:/admin/categories";
			}
			form.setSelectedCategory(cat);
		}
		model.addAttribute("isNew", true);
		model.addAttribute("form", form);
		return "admin/product";
	}

	@RequestMapping(value = "/product", method = RequestMethod.GET, produces = "text/html")
	public String productEditForm(@RequestParam(value = "id", required = true) long id, Model model, RedirectAttributes redAttrs) {
		ProductForm form = new ProductForm();
		Product product = productService.findById(id);
		if (product == null) {
			return "redirect:/admin/products";
		}
		form.setName(product.getName());
		form.setDescription(product.getDescription());
		form.setPrice(product.getPrice());
		// TODO:
		form.setOnStock(100);
		form.setCategory(product.getCategory());

		model.addAttribute("isNew", false);
		form.setId(product.getId());
		model.addAttribute("form", form);
		return "admin/product";
	}
}
