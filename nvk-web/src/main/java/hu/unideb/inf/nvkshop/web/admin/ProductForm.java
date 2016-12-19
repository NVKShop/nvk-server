package hu.unideb.inf.nvkshop.web.admin;

import java.util.List;

import hu.unideb.inf.rft.nvkshop.entities.product.Category;
import lombok.Data;

@Data
public class ProductForm {

	private Category category;
	private String name;
	private String description;
	private List<Category> subCategories;
	private Category selectedCategory;
	private Double price;
	private Integer onStock;
	private long id;

}
