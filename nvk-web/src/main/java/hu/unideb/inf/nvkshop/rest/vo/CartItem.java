package hu.unideb.inf.nvkshop.rest.vo;

public class CartItem {

	private Long productId;

	private Long quantity;

	public CartItem(Long productId, Long quantity) {
		super();
		this.productId = productId;
		this.quantity = quantity;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public CartItem() {
		super();
	}

}
