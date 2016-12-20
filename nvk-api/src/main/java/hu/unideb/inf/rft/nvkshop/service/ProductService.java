package hu.unideb.inf.rft.nvkshop.service;

import java.util.List;

import org.springframework.data.domain.Page;

import hu.unideb.inf.rft.nvkshop.entities.product.Category;
import hu.unideb.inf.rft.nvkshop.entities.product.Order;
import hu.unideb.inf.rft.nvkshop.entities.product.Product;
import hu.unideb.inf.rft.nvkshop.util.ProductSearch;
import hu.unideb.inf.rft.nvkshop.validation.exception.ValidationException;

public interface ProductService {

	Page<Product> search(ProductSearch search);

	List<Product> findByCategory(Category category);

	List<Product> getDiscountedProducts();

	void placeOrder(Order o);

	Double getTotalOfOrder(Order order);

	Product findById(Long id);

	Product addProduct(String name, String description, Double price, Integer onStrock, Long selectedCategoryId) throws ValidationException;

	Product updateProduct(long id, String name, String description, Double price, Integer onStrock, Long selectedCategoryId)
			throws ValidationException;

	List<Product> findAll();

	void uploadPictureForProduct(long id, byte[] pictureInBytes);

	void deletePictureFromProduct(long id);
}
