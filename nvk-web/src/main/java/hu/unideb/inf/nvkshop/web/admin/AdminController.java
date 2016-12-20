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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import hu.unideb.inf.nvkshop.web.AbstractNvkController;
import hu.unideb.inf.nvkshop.web.user.UserForm;
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
@SessionAttributes(types = { UserForm.class, CategoryForm.class, ProductForm.class })

public class AdminController extends AbstractNvkController {

	/** Max file size : 20 Mb */
	private final static int MAX_FILE_SIZE = 20971520;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductService productService;

	@RequestMapping(value = "/categories", method = RequestMethod.GET, produces = "text/html")
	public String categoryForm(@ModelAttribute("form") UserForm form, Model model, RedirectAttributes redAttrs) {
		CategoryForm categoryForm = new CategoryForm();
		categoryForm.setRootCategories(categoryService.findRootCategories());
		categoryForm.setIsAdmin(true);
		model.addAttribute("categoryForm", categoryForm);
		addDatasForUser(categoryForm, null);
		return "admin/categories";
	}

	@RequestMapping(value = "/categories", produces = "text/html", method = RequestMethod.POST)
	public String categoryFormAddNewCategory(@ModelAttribute("form") UserForm form,
			@ModelAttribute("categoryForm") CategoryForm categoryForm, Model model, Errors errors, RedirectAttributes redAttrs) {

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newCategoryName", "validation.required");

		try {
			categoryService.addCategory(categoryForm.getNewCategoryName(), null);
		} catch (ValidationException e) {
			errors.reject("newCategoryName", "category.notValidCategory");
		}
		return "redirect:/admin/categories";
	}

	@RequestMapping(value = "/category/{id}/edit", produces = "text/html")
	public String categorySubForm(@ModelAttribute("form") UserForm form, @PathVariable("id") long id, Model model,
			RedirectAttributes redAttrs) {
		Category cat = categoryService.findById(id);
		if (cat == null) {
			// TODO: log or smtg
			return "redirect:/admin/categories";
		}
		CategoryForm categoryForm = new CategoryForm();
		categoryForm.setCategory(cat);
		model.addAttribute("categoryForm", categoryForm);
		return "admin/category";

	}

	@RequestMapping(value = "/category/{id}/edit", produces = "text/html", method = RequestMethod.POST)
	public String categorySubFormSubmit(@ModelAttribute("form") UserForm form, @PathVariable("id") long id,
			@ModelAttribute("categoryForm") CategoryForm categoryForm, Model model, Errors errors, RedirectAttributes redAttrs) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newCategoryName", "validation.required");
		if (errors.hasErrors()) {
			return "admin/edit";
		}
		try {
			categoryService.addSubCategory(id, categoryForm.getNewCategoryName());
		} catch (ValidationException e) {
			errors.reject("newCategoryName", "category.notValidCategory");
		}
		return "redirect:/admin/category/" + id + "/edit";

	}

	@RequestMapping(value = "/category/{id}/subcategory/{subId}/edit", produces = "text/html")
	public String categorySubEditForm(@ModelAttribute("form") UserForm form, @ModelAttribute("categoryForm") CategoryForm categoryForm,
			@PathVariable("id") long id, @PathVariable("subId") long subId, Model model, RedirectAttributes redAttrs) {
		// CategoryForm categoryForm = new CategoryForm();

		Category cat = categoryService.findById(subId);
		if (cat == null) {
			// TODO: log or smtg
			return "redirect:/admin/categories";
		}
		categoryForm.setCategory(cat);
		model.addAttribute("form", form);
		return "admin/subcategory";

	}

	@RequestMapping(value = "/category/{id}/subcategory/{subId}/edit", produces = "text/html", method = RequestMethod.POST)
	public String categorySubEditFormSubmit(@PathVariable("id") long id, @PathVariable("subId") long subId,
			@ModelAttribute("form") CategoryForm categoryForm, @ModelAttribute("form") UserForm form, Model model, Errors errors,
			RedirectAttributes redAttrs) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newCategoryName", "validation.required");
		if (errors.hasErrors()) {
			return "admin/edit";
		}
		try {
			categoryService.addSubCategory(id, categoryForm.getNewCategoryName());
		} catch (ValidationException e) {
			errors.reject("newCategoryName", "category.notValidCategory");
		}
		return "admin/category";

	}

	@RequestMapping(value = "/newProduct", method = RequestMethod.GET, produces = "text/html")
	public String productForm(@RequestParam(value = "id", required = false) Long id, Model model, @ModelAttribute("form") UserForm form,
			RedirectAttributes redAttrs) {
		ProductForm productForm = new ProductForm();
		addDatasForUser(productForm, null);
		if (id != null) {
			Category cat = categoryService.findById(id);
			if (cat == null) {
				// TODO: log or smtg
				return "redirect:/admin/categories";
			}
			productForm.setSelectedCategory(cat);
			productForm.setSelectedCategoryId(cat.getId());

		}
		List<Category> cats = categoryService.findLeafCategories();
		for (Category cat : cats) {
			System.out.println(cat.toString());
		}

		productForm.setSubCategories(categoryService.findLeafCategories());
		for (Category cat : productForm.getSubCategories()) {
			System.out.println(cat.toString());
		}
		productForm.setIsNew(true);
		model.addAttribute("productForm", productForm);
		return "admin/product";
	}

	@RequestMapping(value = "/product", method = RequestMethod.GET, produces = "text/html")
	public String productEditForm(@ModelAttribute("form") UserForm form, @RequestParam(value = "id", required = true) long id, Model model,
			RedirectAttributes redAttrs) {
		ProductForm productForm = new ProductForm();
		addDatasForUser(productForm, null);
		Product product = productService.findById(id);
		addDatasForUser(form, null);
		if (product == null) {
			return "redirect:/admin/products";
		}
		productForm.setName(product.getName());
		productForm.setDescription(product.getDescription());
		productForm.setPrice(product.getPrice());
		// TODO:
		productForm.setOnStock(100);
		productForm.setCategory(product.getCategory());

		productForm.setIsNew(true);
		productForm.setPictureFlag(product.getPictureAsByte().length > 0);
		productForm.setId(product.getId());
		model.addAttribute("productForm", productForm);
		return "admin/product";
	}

	@RequestMapping(value = "/product", method = RequestMethod.POST, produces = "text/html")
	public String productEditFormSubmit(@ModelAttribute("form") UserForm form, @ModelAttribute("productForm") ProductForm productForm,
			Model model, Errors errors, RedirectAttributes redAttrs) throws IOException {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "validation.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "validation.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "validation.required");

		// if (errors.hasErrors()) {
		// return "redirect:/admin/product";
		// }

		if (productForm.getId() == null) {
			if (productForm.getIsNew() == true) {
				try {
					productService.addProduct(productForm.getName(), productForm.getDescription(), productForm.getPrice(),
							productForm.getOnStock(), productForm.getSelectedCategoryId());
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
				productService.updateProduct(productForm.getId(), productForm.getName(), productForm.getDescription(),
						productForm.getPrice(), productForm.getOnStock(), productForm.getSelectedCategoryId());
				if (productForm.getPicture() != null && productForm.getPicture().getBytes().length > 0) {
					// if (form.getPicture().getBytes().length > MAX_FILE_SIZE) {
					// errors.rejectValue("picture", "validation.tooBigFile");
					// return "admin/product";
					// }
					productService.uploadPictureForProduct(form.getId().longValue(), productForm.getPicture().getBytes());

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
				return "admin/product?id=" + productForm.getId();
			} catch (DeletedEntityException e) {
				redAttrs.addFlashAttribute("errorMsg", "notFound.categoryOrProduct");
				return "redirect:/admin/products";
			}
			redAttrs.addFlashAttribute("successMsg", "product.saved");

		}

		return "redirect:/admin/products";

	}

	@RequestMapping(value = "/product/{id}/picture", method = RequestMethod.GET)
	public ResponseEntity<byte[]> imageOfProduct(@PathVariable long id) {

		Product product = productService.findById(id);
		byte[] bytes = product.getPictureAsByte();

		// Set headers
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);

		return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/products", method = RequestMethod.GET, produces = "text/html")
	public String productsList(@ModelAttribute("form") UserForm form, Model model, RedirectAttributes redAttrs) {

		List<Product> products = productService.findAll();
		model.addAttribute("products", products);

		return "admin/products";
	}

	@RequestMapping(value = "/deletepicture", method = RequestMethod.GET, produces = "text/html")
	public String deleteAddress(@ModelAttribute("form") UserForm form, @RequestParam("id") long id, Errors errors, Model model,
			RedirectAttributes redAttrs) {

		// Long id = authenticationUserId();
		productService.deletePictureFromProduct(id);

		return "redirect:/admin/product?id=" + id;
	}

}
