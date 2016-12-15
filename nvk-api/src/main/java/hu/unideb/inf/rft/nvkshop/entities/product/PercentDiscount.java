package hu.unideb.inf.rft.nvkshop.entities.product;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "percent_discount")
@DiscriminatorValue("PERCENT_DISCOUNT")
public class PercentDiscount extends Discount {

	@Column(name = "percent")
	private Double percent;

	public Double getPercent() {
		return percent;
	}

	public void setPercent(Double percent) {
		this.percent = percent;
	}

	public PercentDiscount() {
		super();
	}

	@Override
	public Double modifyPrice(Double price) {
		return price * (100D - percent);
	}

}
