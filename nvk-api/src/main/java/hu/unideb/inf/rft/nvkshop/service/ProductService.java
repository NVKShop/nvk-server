package hu.unideb.inf.rft.nvkshop.service;

import java.util.List;

import org.springframework.data.domain.Page;

import hu.unideb.inf.rft.nvkshop.entities.product.Category;
import hu.unideb.inf.rft.nvkshop.entities.product.Product;
import hu.unideb.inf.rft.nvkshop.util.ProductSearch;
import hu.unideb.inf.rft.nvkshop.validation.exception.ValidationException;

public interface ProductService {

	Page<Product> search(ProductSearch search);
	
	List<Product> findByCategory(Category category);

	void addProduct(Product p) throws ValidationException;
	
}