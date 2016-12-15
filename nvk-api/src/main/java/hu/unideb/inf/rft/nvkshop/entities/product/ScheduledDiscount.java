package hu.unideb.inf.rft.nvkshop.entities.product;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import hu.unideb.inf.rft.nvkshop.entities.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Table(name = "scheduled_discounts")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduledDiscount extends BaseEntity {

	@OneToMany(mappedBy="scheduledDiscount")
	private List<Discount> discounts;

	@Column(name = "starts")
	@Temporal(TemporalType.TIMESTAMP)
	private Date starts;

	@Column(name = "expires")
	@Temporal(TemporalType.TIMESTAMP)
	private Date expires;

	public Date getExpires() {
		return expires;
	}

	public void setExpires(Date expires) {
		this.expires = expires;
	}

	public Date getStarts() {
		return starts;
	}

	public void setStarts(Date starts) {
		this.starts = starts;
	}

	public List<Discount> getDiscounts() {
		return discounts;
	}

	public void setDiscounts(List<Discount> discounts) {
		this.discounts = discounts;
	}

}
