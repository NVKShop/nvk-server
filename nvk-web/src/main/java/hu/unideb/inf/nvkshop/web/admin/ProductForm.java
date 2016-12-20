package hu.unideb.inf.nvkshop.web.admin;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import hu.unideb.inf.rft.nvkshop.entities.product.Category;

public class ProductForm {

	private Category category;
	private String name;
	private String description;
	private List<Category> subCategories;
	private Category selectedCategory;
	private Long selectedCategoryId;
	private Double price;
	private Integer onStock;
	private Long id;
	private Boolean isNew;
	private MultipartFile picture;
	private boolean pictureFlag;

	public boolean isPictureFlag() {
		return pictureFlag;
	}

	public void setPictureFlag(boolean pictureFlag) {
		this.pictureFlag = pictureFlag;
	}

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

	public List<Category> getSubCategories() {
		return subCategories;
	}

	public void setSubCategories(List<Category> subCategories) {
		this.subCategories = subCategories;
	}

	public Category getSelectedCategory() {
		return selectedCategory;
	}

	public void setSelectedCategory(Category selectedCategory) {
		this.selectedCategory = selectedCategory;
	}

	public Long getSelectedCategoryId() {
		return selectedCategoryId;
	}

	public void setSelectedCategoryId(Long selectedCategoryId) {
		this.selectedCategoryId = selectedCategoryId;
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

	public Boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}

	public MultipartFile getPicture() {
		return picture;
	}

	public void setPicture(MultipartFile picture) {
		this.picture = picture;
	}

}
