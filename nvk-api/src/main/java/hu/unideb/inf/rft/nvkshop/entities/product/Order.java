package hu.unideb.inf.rft.nvkshop.entities.product;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import hu.unideb.inf.rft.nvkshop.entities.base.BaseEntity;
import hu.unideb.inf.rft.nvkshop.entities.security.User;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "item_order_id")
	private List<ItemOrder> items;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
	private User user;

	@Column(name = "pay_type")
	@Enumerated(EnumType.STRING)
	private PayType payType;

	@OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "order")
	private Address deliveryAddress;

	public List<ItemOrder> getItems() {
		return items;
	}

	public void setItems(List<ItemOrder> items) {
		this.items = items;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Order(List<ItemOrder> items, User user) {
		super();
		this.items = items;
		this.user = user;
	}

	public Order() {
		super();
	}

}
