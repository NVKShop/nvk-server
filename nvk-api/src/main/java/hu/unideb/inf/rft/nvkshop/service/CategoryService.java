package hu.unideb.inf.rft.nvkshop.service;

import java.util.List;

import hu.unideb.inf.rft.nvkshop.entities.product.Category;
import hu.unideb.inf.rft.nvkshop.validation.exception.ValidationException;

public interface CategoryService {

	List<Category> findRootCategories();
	
	List<Category> findByParent(Category category);

	Category addCategory(Category category) throws ValidationException;
	
}
