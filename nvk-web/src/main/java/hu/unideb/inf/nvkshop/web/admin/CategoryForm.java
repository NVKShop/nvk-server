package hu.unideb.inf.nvkshop.web.admin;

import java.util.List;

import hu.unideb.inf.nvkshop.web.AbstractUserForm;
import hu.unideb.inf.rft.nvkshop.entities.product.Category;
import hu.unideb.inf.rft.nvkshop.entities.product.Item;

public class CategoryForm extends AbstractUserForm {

	private String newCategoryName;
	private List<Category> rootCategories;
	private Category category;
	private Boolean isAdmin;

	private List<Item> cart;

	public List<Item> getCart() {
		return cart;
	}

	public void setCart(List<Item> cart) {
		this.cart = cart;
	}

	@Override
	public Boolean getIsAdmin() {
		return true;
	}

	@Override
	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getNewCategoryName() {
		return newCategoryName;
	}

	public void setNewCategoryName(String newCategoryName) {
		this.newCategoryName = newCategoryName;
	}

	@Override
	public List<Category> getRootCategories() {
		return rootCategories;
	}

	@Override
	public void setRootCategories(List<Category> rootCategories) {
		this.rootCategories = rootCategories;
	}

}
