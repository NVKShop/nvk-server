package hu.unideb.inf.nvkshop.web.main;

import hu.unideb.inf.rft.nvkshop.entities.product.Category;

public class ProductForm {
	private Category category;
	private String name;
	private String description;
	private Double price;
	private Integer onStock;
	private Long id;

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getOnStock() {
		return onStock;
	}

	public void setOnStock(Integer onStock) {
		this.onStock = onStock;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
