package hu.unideb.inf.rft.nvkshop.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.unideb.inf.rft.nvkshop.entities.product.Category;
import hu.unideb.inf.rft.nvkshop.repositories.CategoryDao;
import hu.unideb.inf.rft.nvkshop.service.CategoryService;
import hu.unideb.inf.rft.nvkshop.service.DeletedEntityException;
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
	public List<Category> findLeafCategories() {
		return categoryDao.findBySubCategoriesIsNull();
	}

	@Override
	public List<Category> findByParent(Category category) {
		return categoryDao.findByParent(category);
	}

	@Override
	public Category addCategory(String categoryName, Category parent) throws ValidationException {

		Category cat = new Category();
		cat.setName(categoryName);
		cat.setDateOfCreation(new Date());
		if (parent != null) {
			cat.setParent(parent);
		}
		validator.validate(cat);

		return categoryDao.save(cat);
	}

	@Override
	public List<Category> findAll() {
		return categoryDao.findAll();
	}

	@Override
	public Category findById(Long id) {
		return categoryDao.findOne(id);
	}

	@Override
	public void addSubCategory(long id, String newCategoryName) throws ValidationException {

		Category category = categoryDao.findOne(id);
		if (category == null) {
			throw new DeletedEntityException();
		}

		Category newCategory = addCategory(newCategoryName, category);

		category.getSubCategories().add(newCategory);

	}
}
