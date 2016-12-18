package hu.unideb.inf.rft.nvkshop.entities.product.archive;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import hu.unideb.inf.rft.nvkshop.entities.base.BaseEntity;

@Entity
@Table(name="archive_items")
public class ArchiveItem extends BaseEntity {

	@ManyToOne
	@JoinColumn(name="order_id")
	private ArchiveOrder order;
	
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@PrimaryKeyJoinColumn
	private ArchiveProduct product;

	@Column(name="quantity")
	private Long quantity;


	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public ArchiveItem(ArchiveProduct product, Long quantity) {
		super();
		this.product = product;
		this.quantity = quantity;
	}

	public ArchiveItem() {
		super();
	}

	public ArchiveOrder getOrder() {
		return order;
	}

	public void setOrder(ArchiveOrder order) {
		this.order = order;
	}

	public ArchiveProduct getProduct() {
		return product;
	}

	public void setProduct(ArchiveProduct product) {
		this.product = product;
	}

}
