package hu.unideb.inf.rft.nvkshop.entities.product;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import hu.unideb.inf.rft.nvkshop.entities.base.BaseEntity;

public class ItemOrder extends BaseEntity {

	@ManyToMany(fetch=FetchType.LAZY,cascade={CascadeType.MERGE,CascadeType.PERSIST})
	private Item item;

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
