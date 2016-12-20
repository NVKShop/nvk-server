package hu.unideb.inf.nvkshop.web.main;

import java.util.List;

import hu.unideb.inf.rft.nvkshop.entities.product.Category;
import hu.unideb.inf.rft.nvkshop.entities.product.Product;

public class MainForm {
	private List<Category> rootCategories;
	private List<Product> products;
	private Product product;
	private Integer qty;

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public List<Category> getRootCategories() {
		return rootCategories;
	}

	public void setRootCategories(List<Category> rootCategories) {
		this.rootCategories = rootCategories;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
