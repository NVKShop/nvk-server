package hu.unideb.inf.rft.nvkshop.entities.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import hu.unideb.inf.rft.nvkshop.entities.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Table(name = "active_discounts")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActiveDiscount extends BaseEntity {

	@OneToOne(optional = false)
	private Product product;

	@ManyToOne
	@JoinColumn(name="scheduled_discount_id")
	private ScheduledDiscount scheduledDiscount;

	@Column(name = "new_price")
	private Double newPrice;

	@Column(name = "old_price")
	private Double oldPrice;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Double getNewPrice() {
		return newPrice;
	}

	public void setNewPrice(Double newPrice) {
		this.newPrice = newPrice;
	}

	public Double getOldPrice() {
		return oldPrice;
	}

	public void setOldPrice(Double oldPrice) {
		this.oldPrice = oldPrice;
	}

	public ScheduledDiscount getScheduledDiscount() {
		return scheduledDiscount;
	}

	public void setScheduledDiscount(ScheduledDiscount scheduledDiscount) {
		this.scheduledDiscount = scheduledDiscount;
	}

}
