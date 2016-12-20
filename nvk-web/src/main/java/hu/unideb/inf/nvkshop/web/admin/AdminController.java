package hu.unideb.inf.nvkshop.web.admin;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import hu.unideb.inf.rft.nvkshop.service.DeletedEntityException;
import hu.unideb.inf.rft.nvkshop.service.ProductService;
import hu.unideb.inf.rft.nvkshop.validation.exception.ValidationException;
import hu.unideb.inf.rft.nvkshop.validation.exception.ValidationViolation;
import hu.unideb.inf.rft.nvkshop.validation.product.ProductValidationViolation;

/**
 * @author VForjan
 *
 */
@Controller("adminController")
@RequestMapping("/admin")
public class AdminController extends AbstractNvkController {

	/** Max file size : 20 Mb */
	private final static int MAX_FILE_SIZE = 20971520;

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
			form.setSelectedCategoryId(cat.getId());

		}
		List<Category> cats = categoryService.findLeafCategories();
		for (Category cat : cats) {
			System.out.println(cat.toString());
		}

		form.setSubCategories(categoryService.findLeafCategories());
		for (Category cat : form.getSubCategories()) {
			System.out.println(cat.toString());
		}
		form.setIsNew(true);
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

		form.setIsNew(false);
		form.setPictureFlag(product.getPictureAsByte().length > 0);
		form.setId(product.getId());
		model.addAttribute("form", form);
		return "admin/product";
	}

	@RequestMapping(value = "/product", method = RequestMethod.POST, produces = "text/html")
	public String productEditFormSubmit(@ModelAttribute("form") ProductForm form, Model model, Errors errors, RedirectAttributes redAttrs)
			throws IOException {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "validation.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "validation.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "validation.required");

		if (errors.hasErrors()) {
			return "admin/product";
		}

		if (form.getId() == null) {
			if (form.getIsNew() == true) {
				try {
					productService.addProduct(form.getName(), form.getDescription(), form.getPrice(), form.getOnStock(),
							form.getSelectedCategoryId());
				} catch (ValidationException e) {
					for (ValidationViolation violation : e.getViolations()) {
						if (violation instanceof ProductValidationViolation) {
							switch ((ProductValidationViolation) violation) {
							case CATEGORY_CANNOT_BE_NULL:
								errors.rejectValue("category", "productValidation.categoryNotEmpty");
							case DESCRIPTION_CANNOT_BE_NULL:
								errors.rejectValue("description", "productValidation.descriptionNotEmpty");

							case NAME_CANNOT_BE_EMPTY:
								errors.rejectValue("name", "productValidation.nameNotEmpty");

							case PRICE_MUST_BE_VALID:
								errors.rejectValue("price", "productValidation.invalidPrice");

							}
						}
					}

				} catch (DeletedEntityException ex) {
					redAttrs.addFlashAttribute("errorMsg", "category.notFound");
					return "redirect:/admin/products";

				}
				redAttrs.addFlashAttribute("successMsg", "product.created");
			} else {
				redAttrs.addFlashAttribute("errorMsg", "product.cantCreated");
				return "redirect:/admin/products";
			}
		} else {
			try {
				productService.updateProduct(form.getId(), form.getName(), form.getDescription(), form.getPrice(), form.getOnStock(),
						form.getSelectedCategoryId());
				if (form.getPicture() != null && form.getPicture().getBytes().length > 0) {
					// if (form.getPicture().getBytes().length > MAX_FILE_SIZE) {
					// errors.rejectValue("picture", "validation.tooBigFile");
					// return "admin/product";
					// }
					productService.uploadPictureForProduct(form.getId().longValue(), form.getPicture().getBytes());

				}
			} catch (ValidationException e) {
				for (ValidationViolation violation : e.getViolations()) {
					if (violation instanceof ProductValidationViolation) {
						switch ((ProductValidationViolation) violation) {
						case CATEGORY_CANNOT_BE_NULL:
							errors.rejectValue("category", "productValidation.categoryNotEmpty");
						case DESCRIPTION_CANNOT_BE_NULL:
							errors.rejectValue("description", "productValidation.descriptionNotEmpty");

						case NAME_CANNOT_BE_EMPTY:
							errors.rejectValue("name", "productValidation.nameNotEmpty");

						case PRICE_MUST_BE_VALID:
							errors.rejectValue("price", "productValidation.invalidPrice");

						}
					}
				}
				return "admin/product?id=" + form.getId();
			} catch (DeletedEntityException e) {
				redAttrs.addFlashAttribute("errorMsg", "notFound.categoryOrProduct");
				return "redirect:/admin/products";
			}
			redAttrs.addFlashAttribute("successMsg", "product.saved");

		}

		return "redirect:/admin/products";

	}

	@RequestMapping(value = "/admin/product/{id}/picture", method = RequestMethod.GET)
	public ResponseEntity<byte[]> imageOfProduct(@PathVariable long id) {

		Product product = productService.findById(id);
		byte[] bytes = product.getPictureAsByte();

		// Set headers
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);

		return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/products", method = RequestMethod.GET, produces = "text/html")
	public String productsList(Model model, RedirectAttributes redAttrs) {

		List<Product> products = productService.findAll();
		model.addAttribute("products", products);

		return "admin/products";
	}
}
