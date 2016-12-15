package hu.unideb.inf.rft.nvkshop.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import hu.unideb.inf.rft.nvkshop.entities.product.Category;
import hu.unideb.inf.rft.nvkshop.entities.product.Product;
import hu.unideb.inf.rft.nvkshop.repositories.ActiveDiscountDao;
import hu.unideb.inf.rft.nvkshop.repositories.ProductDao;
import hu.unideb.inf.rft.nvkshop.service.ProductService;
import hu.unideb.inf.rft.nvkshop.util.PriceSearchTag;
import hu.unideb.inf.rft.nvkshop.util.ProductSearch;
import hu.unideb.inf.rft.nvkshop.validation.exception.ValidationException;
import hu.unideb.inf.rft.nvkshop.validation.product.ProductValidator;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;

	@Autowired
	private ProductValidator validator;

	@Autowired
	private ActiveDiscountDao activeDiscountDao;

	@Override
	public Page<Product> search(ProductSearch search) {

		PriceSearchTag priceSearchTag = new PriceSearchTag(search.getLowerPrice(), search.getUpperPrice());
		Sort sort = new Sort(search.getSortDirection(), Arrays.asList(search.getSortBy()));
		PageRequest pageRequest = new PageRequest(search.getFrom(), search.getPageSize(), sort);
		return productDao.search(search.getSearchTerm(), search.getInCategories(), priceSearchTag, pageRequest);
	}

	@Override
	public List<Product> findByCategory(Category category) {
		return productDao.findByCategory(category);
	}

	@Override
	public Product addProduct(Product product) throws ValidationException {
		validator.validate(product);
		return productDao.save(product);
	}

	@Override
	public List<Product> getDiscountedProducts() {
		return activeDiscountDao.findDiscountedProducts();
	}

}
