package hu.unideb.inf.rft.nvkshop.entities.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import hu.unideb.inf.rft.nvkshop.entities.base.BaseEntity;

@Entity
@Table(name = "item_order_sw")	
public class ItemOrder extends BaseEntity {
	
//	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@Transient
	private Item item;

	@Column(name="quantity")
	private Integer quantity;

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public ItemOrder() {
		super();
	}

	public ItemOrder(Item item, Integer quantity) {
		super();
		this.item = item;
		this.quantity = quantity;
	}

}
