package hu.unideb.inf.rft.nvkshop.entities.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import hu.unideb.inf.rft.nvkshop.entities.base.BaseEntity;

@Table(name = "products")
@Entity
@JsonIgnoreProperties({"category","dateOfCreation","dateOfModification","version"})
public class Product extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@Lob
	@Column(length = 10000000, name = "picture")
	private byte[] pictureAsByte;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "price", nullable = false)
	private Double price;

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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public byte[] getPictureAsByte() {
		return pictureAsByte;
	}

	public void setPictureAsByte(byte[] pictureAsByte) {
		this.pictureAsByte = pictureAsByte;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
