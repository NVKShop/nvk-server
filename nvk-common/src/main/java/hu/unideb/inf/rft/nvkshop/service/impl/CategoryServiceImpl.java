package hu.unideb.inf.rft.nvkshop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.unideb.inf.rft.nvkshop.entities.product.Category;
import hu.unideb.inf.rft.nvkshop.repositories.CategoryDao;
import hu.unideb.inf.rft.nvkshop.service.CategoryService;
import hu.unideb.inf.rft.nvkshop.validation.category.CategoryValidator;
import hu.unideb.inf.rft.nvkshop.validation.exception.ValidationException;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryValidator validator;
	
	@Autowired
	private CategoryDao categoryDao;

	@Override
	public List<Category> findRootCategories() {
		return categoryDao.findByParentIsNull();
	}

	@Override
	public List<Category> findByParent(Category category) {
		return categoryDao.findByParent(category);
	}

	@Override
	public Category addCategory(Category category) throws ValidationException {
		validator.validate(category);
	
		return categoryDao.save(category);
	}

}
