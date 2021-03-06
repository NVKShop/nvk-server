package hu.unideb.inf.nvkshop.web;

import java.util.List;

import hu.unideb.inf.nvkshop.web.main.MainForm;
import hu.unideb.inf.rft.nvkshop.entities.product.Category;
import hu.unideb.inf.rft.nvkshop.entities.product.Product;
import hu.unideb.inf.rft.nvkshop.entities.security.Language;

/**
 * Abstract user form for outer (user) navbar of views.
 * 
 * @author FV
 *
 */
public class AbstractUserForm extends MainForm {

	private Long userId;
	private String userName;
	private Integer totalValueOfItems;
	private List<Product> items;
	private Language language;
	private List<Category> rootCategory;

	public List<Category> getRootCategory() {
		return rootCategory;
	}

	public void setRootCategory(List<Category> rootCategory) {
		this.rootCategory = rootCategory;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getTotalValueOfItems() {
		return totalValueOfItems;
	}

	public void setTotalValueOfItems(Integer totalValueOfItems) {
		this.totalValueOfItems = totalValueOfItems;
	}

	public List<Product> getItems() {
		return items;
	}

	public void setItems(List<Product> items) {
		this.items = items;
	}

}
