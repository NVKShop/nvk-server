package hu.unideb.inf.rft.nvkshop.entities.product;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import hu.unideb.inf.rft.nvkshop.entities.base.BaseEntity;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {

	@Column(name = "name")
	private String name;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "parent_id")
	private List<Category> subCategories;

	@ManyToOne
	@JoinColumn(name = "parent_id")
	private Category parent;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id")
	private List<Product> products;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Category> getSubCategories() {
		return subCategories;
	}

	public void setSubCategories(List<Category> subCategories) {
		this.subCategories = subCategories;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

}
