package hu.unideb.inf.rft.nvkshop.entities.product;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import hu.unideb.inf.rft.nvkshop.entities.base.BaseEntity;

@Entity
@Table(name = "discounts")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "discount_type")
public abstract class Discount extends BaseEntity {

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "discount_product_sw", joinColumns = {
			@JoinColumn(name = "discount_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "product_id", nullable = false, updatable = false) })
	protected List<Product> products;

	@ManyToOne
	@JoinColumn(name = "scheduled_discound_id")
	protected ScheduledDiscount scheduledDiscount;

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public ScheduledDiscount getScheduledDiscount() {
		return scheduledDiscount;
	}

	public void setScheduledDiscount(ScheduledDiscount scheduledDiscount) {
		this.scheduledDiscount = scheduledDiscount;
	}

	public abstract Double modifyPrice(Double price);
	
}
