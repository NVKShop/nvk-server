package hu.unideb.inf.nvkshop.rest.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderPlacement {

	@JsonProperty(required = false)
	private Long addressId;

	private CartItem[] items;

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public CartItem[] getItems() {
		return items;
	}

	public void setItems(CartItem[] items) {
		this.items = items;
	}

}
