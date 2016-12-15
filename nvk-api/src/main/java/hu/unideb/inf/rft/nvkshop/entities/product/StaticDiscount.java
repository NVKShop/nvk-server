package hu.unideb.inf.rft.nvkshop.entities.product;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "static_discounts")
@DiscriminatorValue("STATIC_DISCOUNT")
public class StaticDiscount extends Discount {

	@Column(name = "reduced_by")
	private Double reducedBy;

	public Double getReducedBy() {
		return reducedBy;
	}

	public void setReducedBy(Double reducedBy) {
		this.reducedBy = reducedBy;
	}

	@Override
	public Double modifyPrice(Double price) {
		return price-reducedBy;
	}

}
