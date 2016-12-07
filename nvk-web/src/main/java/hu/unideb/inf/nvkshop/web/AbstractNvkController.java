package hu.unideb.inf.nvkshop.web;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Date;

import org.springframework.util.Assert;

import hu.unideb.inf.rft.nvkshop.entities.product.Product;

public class AbstractNvkController {

	// protected long authenticatedUserId() {
	// Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	//
	// }

	protected <T extends AbstractUserForm> void addTestDatasForUser(T form) {
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

	}

	protected Date dateOf(ZonedDateTime time) {
		Assert.notNull(time);
		return Date.from(time.toInstant());
	}

	protected ZonedDateTime now() {
		return LocalDateTime.now().atZone(ZoneId.systemDefault());
	}
}
