package hu.unideb.inf.nvkshop.web;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

import hu.unideb.inf.rft.nvkshop.entities.product.Category;
import hu.unideb.inf.rft.nvkshop.entities.product.Product;
import hu.unideb.inf.rft.nvkshop.entities.security.Language;
import hu.unideb.inf.rft.nvkshop.entities.security.Role;
import hu.unideb.inf.rft.nvkshop.entities.security.User;
import hu.unideb.inf.rft.nvkshop.security.CustomUserDetails;
import hu.unideb.inf.rft.nvkshop.security.CustomUserDetailsService;
import hu.unideb.inf.rft.nvkshop.service.AlreadyLoggedInException;
import hu.unideb.inf.rft.nvkshop.service.CategoryService;
import hu.unideb.inf.rft.nvkshop.service.ProductService;

public class AbstractNvkController {

	private static final Role ADMIN_ROLE = new Role("ROLE_ADMIN");

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductService productService;

	@Autowired
	private CustomUserDetailsService costumUserDetailsService;

	protected <T extends AbstractUserForm> void addTestDatasForUser(T form, Long categoryId) {
		form.setUserName("Gipsz Jakab");
		form.setUserId(1L);
		Product prod1 = new Product();
		prod1.setName("Product1");
		prod1.setPrice(new Double(15.0));
		Product prod2 = new Product();
		prod2.setName("Product2");
		prod2.setPrice(new Double(600.0));
		form.setItems(Arrays.asList(prod1, prod2));
		form.setTotalValueOfItems(615);

		if (categoryId != null) {
			Category cat = categoryService.findById(categoryId.longValue());
			form.setProducts(productService.findByCategory(cat));
		} else {
			// TODO: Trimthis
			form.setProducts(productService.findAll());

		}

		form.setRootCategory(categoryService.findRootCategories());
	}

	/**
	 * Get a date by a zoned date time.
	 * 
	 * @param time the time
	 * @return the tima instant
	 */
	protected Date dateOf(ZonedDateTime time) {
		Assert.notNull(time);
		return Date.from(time.toInstant());
	}

	/**
	 * Return the actual local date tame based by system default
	 * 
	 * @return the actual time
	 */
	protected ZonedDateTime now() {
		return LocalDateTime.now().atZone(ZoneId.systemDefault());
	}

	protected long authenticationUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails principal = null;
		if (authentication == null) {
			// TODO : Log or smtgh
		} else {
			principal = (CustomUserDetails) authentication.getPrincipal();

		}

		return principal.getUser().getId();
	}

	protected <T extends AbstractUserForm> void addDatasForUser(T form, Long categoryId) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails principal = null;
		if (authentication == null) {
			// TODO : Log or smtgh
		} else {
			principal = (CustomUserDetails) authentication.getPrincipal();
			User user = principal.getUser();
			form.setIsAdmin(false);
			if (principal.getUser().getRoles().size() > 1) {
				for (Role r : principal.getUser().getRoles()) {
					if (r.getRoleName().equals("ROLE_ADMIN"))
						form.setIsAdmin(true);
				}
			}
			System.out.println(form.getIsAdmin());

			form.setUserName(user.getUserName());
			form.setUserId(user.getId());
			form.setLanguage(user.getLanguage() == null ? Language.EN : user.getLanguage());

			// FIXME: this is just for test cases!
			Product prod1 = new Product();
			prod1.setName("Product1");
			prod1.setPrice(new Double(15.0));
			Product prod2 = new Product();
			prod2.setName("Product2");
			prod2.setPrice(new Double(600.0));
			List<Product> testProducts = new LinkedList<Product>();
			testProducts.add(prod1);
			testProducts.add(prod2);
			form.setTotalValueOfItems(615);

			form.setItems(testProducts);

			if (categoryId != null) {
				Category cat = categoryService.findById(categoryId.longValue());
				form.setProducts(productService.findByCategory(cat));
			} else {
				// TODO: Trimthis
				form.setProducts(productService.findAll());

			}
			form.setRootCategory(categoryService.findRootCategories());

		}
	}

	protected void addItemsByCategory() {

		List<Category> rootCategoryes = new LinkedList<Category>();
		rootCategoryes.get(1).getSubCategories();

	}

	protected <T extends AbstractUserForm> void addDatasForUserInLogin(T form) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication.getCredentials() == null) {
			throw new AlreadyLoggedInException();
		} else {
			return;
		}
	}

}
