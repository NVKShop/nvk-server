package hu.unideb.inf.nvkshop.web.admin;

import java.util.List;

import hu.unideb.inf.rft.nvkshop.entities.product.Category;

public class CategoryForm {

	private String newCategoryName;
	private List<Category> rootCategories;
	private Category category;

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

	public List<Category> getRootCategories() {
		return rootCategories;
	}

	public void setRootCategories(List<Category> rootCategories) {
		this.rootCategories = rootCategories;
	}

}
