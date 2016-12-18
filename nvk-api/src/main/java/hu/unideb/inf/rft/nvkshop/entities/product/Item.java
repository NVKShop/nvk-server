package hu.unideb.inf.rft.nvkshop.entities.product;

public class Item {
	private Product product;

	private Long quantity;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Item(Product product, Long quantity) {
		super();
		this.product = product;
		this.quantity = quantity;
	}

	public Item() {
		super();
	}

}
