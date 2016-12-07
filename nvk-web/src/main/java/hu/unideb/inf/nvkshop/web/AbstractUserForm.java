package hu.unideb.inf.nvkshop.web;

import java.util.List;

import hu.unideb.inf.rft.nvkshop.entities.product.Product;

/**
 * Abstract user form for outer (user) navbar of views.
 * 
 * @author FV
 *
 */
public class AbstractUserForm {

	private Long userId;
	private String userName;
	private Integer totalValueOfItems;
	private List<Product> items;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getTotalValueOfItems() {
		return totalValueOfItems;
	}

	public void setTotalValueOfItems(Integer totalValueOfItems) {
		this.totalValueOfItems = totalValueOfItems;
	}

	public List<Product> getItems() {
		return items;
	}

	public void setItems(List<Product> items) {
		this.items = items;
	}

}
